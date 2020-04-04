package fr.the4pe18.robby.commands;

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
public class LearnCommand extends AbstractCommand {

    public LearnCommand() {
        super("learn");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        EmbedBuilder eb= new EmbedBuilder();
        try {
            Document document = Jsoup.connect("https://fr.wikipedia.org/wiki/Sp%C3%A9cial:Page_au_hasard").followRedirects(true).get();
            String title = document.getElementById("firstHeading").text();
            String content = document.getElementsByClass("mw-parser-output").get(0).getElementsByTag("p").get(2).text();
            eb.setColor(Color.CYAN);
            eb.setTitle(title);
            eb.setDescription(content);
            eb.addField("Source:", document.location(), false);

        } catch (IOException e) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Impossible de contacter wikipedia :(");
        }
        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
        channel.sendMessage(eb.build()).queue();
    }
}
