package fr.the4pe18.Robby.deploy;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public interface DeployStep<T> {
    T step(Guild guild, MessageChannel channel, Member sender, String[] args) throws Exception;
}
