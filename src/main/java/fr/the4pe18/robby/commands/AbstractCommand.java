package fr.the4pe18.robby.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * @author 4PE18
 */
public abstract class AbstractCommand {
    private final String CommandLabel;

    public AbstractCommand(String commandLabel) {
        this.CommandLabel = commandLabel;
    }

    public String getCommandLabel() {
        return CommandLabel;
    }

    public abstract void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args);

    public boolean checkPerm(Member member, Long... rolesid) {
        if (rolesid.length == 0) return false;
        boolean has = false;
        for (Long id : rolesid) has = member.getRoles().contains(member.getGuild().getRoleById(id)) || has;
        return has;
    }
}
