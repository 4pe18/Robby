package fr.the4pe18.Robby.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.Arrays;

public class DevoirsCommand extends AbstractCommand {

    public DevoirsCommand() {
        super("devoirs_");
    }

    @Override
    public void run(Guild guild, MessageChannel channel, Member sender, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        /**if (!sender.getRoles().contains(guild.getRoleById(687751019341283403L))) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Vous n'avez pas la permission d'executer cette commande!");
        } else {
            eb.setTitle(":diamond_shape_with_a_dot_inside: Debug :diamond_shape_with_a_dot_inside:", null);
            eb.setColor(Color.green);
            eb.setDescription("Debug commande: " + Arrays.toString(args));
        }**/
        if (args.length == 0) {
            eb.setTitle(":green_book: Devoirs: ", null);
            eb.setColor(Color.green);
            eb.setDescription("__**FRANÇAIS:**__\n" +
                    "_**Pour le Lundi 23 Mars**_, commentaire sur \"Le Crapaud\" de T. Corbières commencé en ASA lundi dernier. __**Pour les 8 concernés, ainsi que toute personne souhaitant le faire.**__\n" +
                    "_**Pour le Mercredi 18 Mars**_, faire les deux questions (cf. fiche) sur le texte de \"Le discours de la pie\". :pen_fountain:\n" +
                    "__**ANGLAIS:**__\n" +
                    "_**À faire Lundi 16 Mars**_, répondre aux questions 5 & 6 page 55 du livre. Répondre aux questions 1 & 2 page 54 à l'aide de la vidéo ( https://www.youtube.com/watch?v=99AP4HJxQjE ). Répondre aux questions 3 & 4 page 54 à l'aide de la vidéo ( https://www.youtube.com/watch?v=L0aMYm-9TBU ).\n" +
                    "__**GÉOGRAPHIE:**__\n" +
                    "_**Pour le Jeudi 19 Mars**_, faire les quatre questions dans le livre page 179 ainsi que la synthèse (sous format Word) et le poster sur école directe avec votre NOM - Prénom dans le nom du document ! :nerd:. \n" +
                    "Finir et poster le travail pour le magazine.\n" +
                    "__**ES (SVT):**__\n" +
                    "_**Pour le Vendredi 20 Mars**_, Évaluation (QCM) sur la photosynthèse, être présent de 15h à 16h, les travaux rendus après 16h30 ne seront pas acceptés (je crois). :deciduous_tree:");
        } else {
            if (!sender.getRoles().contains(guild.getRoleById(687751019341283403L))) {
                eb.setTitle(":x: Erreur !", null);
                eb.setColor(Color.red);
                eb.setDescription("Vous n'avez pas la permission d'executer cette commande!");
            } else {
                eb.setTitle(":green_book: Menu aide: ", null);
                eb.setColor(Color.yellow);
                eb.setDescription("TODO");
            }
        }
        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/2db97754561ef317def618759fff18c1.png");
        channel.sendMessage(eb.build()).queue();
    }
}
