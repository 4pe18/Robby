package fr.the4pe18.robby.deploy;

import fr.the4pe18.robby.deploy.old.RobbyDeploy;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * @author 4PE18
 */
public interface PatchStepWorker<T> {
    T work(Guild guild, MessageChannel channel, Member sender, String[] args);
}
