package fr.the4pe18.Robby.deploy;

import fr.the4pe18.Robby.RobbyUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public abstract class AbstractDeploy {
    private String patchName;

    private Guild guild;
    private MessageChannel channel;
    private Member sender;
    private String[] args;

    public AbstractDeploy(String patchName) {
        this.patchName = patchName;
    }

    public String getPatchName() {
        return patchName;
    }

    private Guild getGuild() {
        return guild;
    }

    private MessageChannel getChannel() {
        return channel;
    }

    private Member getSender() {
        return sender;
    }

    private String[] getArgs() {
        return args;
    }

    public <T> T step(boolean critical, DeployStep<T> step) throws DeployException {
        try {
            return step.step(getGuild(), getChannel(), getSender(), getArgs());
        } catch (Exception e) {
            channel.sendMessage(DeployEmbed.STEP_ERROR.build(RobbyUtils.getExceptionTrace(e))).complete();
            if (critical) throw new DeployException();
            else return null;
        }
    }

    protected abstract void patch() throws DeployException;

    public void deploy(Guild guild, MessageChannel channel, Member sender, String[] args) {
        this.guild = guild;
        this.channel = channel;
        this.sender = sender;
        this.args = args;
        channel.sendMessage(DeployEmbed.DEPLOY_STARTING.build(patchName)).complete();
        try {
            patch();
        } catch (DeployException e) {
            channel.sendMessage(DeployEmbed.DEPLOY_FAILED.build(patchName, RobbyUtils.getExceptionTrace(e))).complete();
            return;
        }
        channel.sendMessage(DeployEmbed.DEPLOY_SUCCESS.build(patchName)).complete();
    }
}
