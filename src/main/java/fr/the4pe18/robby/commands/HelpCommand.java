package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.RobbyEmbed;
import fr.the4pe18.robby.RobbyUtils;
import fr.the4pe18.robby.poll.Poll;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

/**
 * @author 4PE18
 */
public class HelpCommand extends AbstractCommand {
    private Robby instance;

    public HelpCommand(Robby instance) {
        super("help");
        this.instance = instance;
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        RobbyEmbed embed = new RobbyEmbed("__**Bonjour, je suis Robby !**__", "https://robby.4pe18.fr", "Je suis un robot, créé pour le serveur discord \"e-school\". Je permets sa maintenance de manière rapide, efficace et automatisée. Je possède aussi certains outils permettant l'intégration de nouvelles fonctionnalités sur le serveur.").setColor(Color.BLUE);
        embed.setAuthor("Created by 4PE18", instance.getChannel4pe18().getUser().getAvatarUrl());
        embed.addField("Commandes", "!help\n!poll\n!clear\n!deploy\n!learn\n!debug\n!audit", true);
        embed.addField("disponibles:", "Aide\nSondage\nNettoyage\nPatch\nFait Aléatoire\nDebug\nAudit", true);
        embed.setImage("https://robby.4pe18.fr/img/robby.png");
        channel.sendMessage(embed.build()).queue();
        try {
            message.delete().complete();
        } catch (Exception ignored) {}
    }
}
