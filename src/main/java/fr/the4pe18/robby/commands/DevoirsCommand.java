package fr.the4pe18.robby.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * @author 4PE18
 */
public class DevoirsCommand extends AbstractCommand {

    public DevoirsCommand() {
        super("devoirs_");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {

    }
}
