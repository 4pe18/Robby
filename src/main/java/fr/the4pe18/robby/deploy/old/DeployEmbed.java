package fr.the4pe18.robby.deploy.old;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * @author 4PE18
 */
public enum DeployEmbed {
    DEPLOY_STARTING,
    STEP_STARTING,
    STEP_SUCCESS,
    STEP_ERROR,
    DEPLOY_FAILED,
    DEPLOY_SUCCESS;

    public MessageEmbed build(String message, String... fields) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
        embedBuilder.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
        switch (this) {
            case DEPLOY_STARTING:
                embedBuilder.setColor(new Color(36, 227, 170));
                embedBuilder.setDescription("**DÉPLOIEMENT DU PATCH " + message + " DÉMARRÉ...**");
                break;
            case STEP_STARTING:
                embedBuilder.setColor(new Color(207, 179, 2));
                embedBuilder.setDescription(message);
                for (int i = 0; i <= fields.length / 2; i++) {
                    try {
                        embedBuilder.addField(fields[2*i], fields[2*i+1], false);
                    } catch (Exception ignored) {}
                }
                break;
            case STEP_SUCCESS:
                embedBuilder.setColor(new Color(67, 207, 2));
                embedBuilder.setDescription(message);
                for (int i = 0; i <= fields.length / 2; i++) {
                    try {
                        embedBuilder.addField(fields[2*i], fields[2*i+1], false);
                    } catch (Exception ignored) {}
                }
                break;
            case STEP_ERROR:
                embedBuilder.setColor(new Color(196, 99, 0));
                embedBuilder.setDescription(message);
                embedBuilder.setDescription("Une erreur est survenue, réessayer!");
                embedBuilder.addField("Erreur: ", message, false);
                break;
            case DEPLOY_FAILED:
                embedBuilder.setColor(new Color(171, 0, 0));
                embedBuilder.setDescription("**DÉPLOIEMENT DU PATCH " + message + " ÉCHOUÉ !**");
                embedBuilder.addField("Erreur: ", fields[0], false);
                break;
            case DEPLOY_SUCCESS:
                embedBuilder.setColor(new Color(0, 209, 3));
                embedBuilder.setDescription("**DÉPLOIEMENT DU PATCH " + message + " TERMINÉ !**");
                break;
        }
        return embedBuilder.build();
    }
}
