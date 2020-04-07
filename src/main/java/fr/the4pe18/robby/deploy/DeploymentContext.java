package fr.the4pe18.robby.deploy;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * @author 4PE18
 */
public class DeploymentContext {
    Guild guild;
    MessageChannel channel;
    Member instigator;
    String[] args;

    public DeploymentContext(Guild guild, MessageChannel channel, Member instigator, String[] args) {
        this.guild = guild;
        this.channel = channel;
        this.instigator = instigator;
        this.args = args;
    }

    public DeploymentContext() {
        this.guild = null;
        this.channel = null;
        this.instigator = null;
        this.args = null;
    }

    public Guild getGuild() {
        return guild;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Member getInstigator() {
        return instigator;
    }

    public String[] getArgs() {
        return args;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public void setInstigator(Member instigator) {
        this.instigator = instigator;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
