package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.RobbyEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.IOException;

/**
 * @author 4PE18
 */
public class QuickPlayCommand extends AbstractCommand {

    public QuickPlayCommand() {
        super("quickplay");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        if (!sender.getRoles().contains(guild.getRoleById(687751019341283403L))) {

            RobbyEmbed embed = new RobbyEmbed("__**DÉPLOIEMENT**__", "Erreur !").setColor(Color.RED);
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("PERMISSION", "Vous n'avez pas la permission d'écécuter cette commande.", false);
            channel.sendMessage(embed.build()).queue();

        } else {
            //if (args.le)
        }
    }
}
