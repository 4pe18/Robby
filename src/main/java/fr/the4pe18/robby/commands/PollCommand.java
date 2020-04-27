package fr.the4pe18.robby.commands;

import com.github.breadmoirai.Emoji;
import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.RobbyEmbed;
import fr.the4pe18.robby.RobbyUtils;
import fr.the4pe18.robby.poll.Poll;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 4PE18
 */
public class PollCommand extends AbstractCommand {
    private Robby instance;

    public PollCommand(Robby instance) {
        super("poll");
        this.instance = instance;
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        message.delete().complete();
        if (args.length > 0) {
            instance.getPollManager().addPoll(new Poll(channel, String.join(" ", args).contains("\"") ? RobbyUtils.getQuotationArgs(args)[0] : String.join(" ", args), sender));
        } else {
            RobbyEmbed embed = new RobbyEmbed("__**SONDAGE**__", "Erreur !").setColor(Color.RED);
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("SYNTAXE", "!poll <une question>\n\n\n\n **EN COURS DE DEVELOPPEMENT:\n** !poll <\"question entre guillemets\"> [\"réponse 1\"] [\"réponse 2\"]... ", false);
            channel.sendMessage(embed.build()).queue();
        }
    }
}
