package fr.the4pe18.robby.old.deploy;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * Représente le coeur d'une étape d'un patch/déploiement Robby
 * @see OldRobbyDeploy
 *
 * @param <T> le type d'objet traité par l'étape
 *
 * @author 4PE18
 * @deprecated
 */
public interface OldStepCore<T> {

    /**
     * Méthode de l'interface qui est éxécutée lors du déploiement
     *
     * @param guild la guilde où s'éexécute le déploiement
     * @param channel le salon où s'éxécute le déploiement
     * @param sender le member qui a instancié le déploiement
     * @param args les arguments fournis lors du déploiement
     * @return un objet T traité par l'étape
     */
    T work(Guild guild, MessageChannel channel, Member sender, String[] args);
}
