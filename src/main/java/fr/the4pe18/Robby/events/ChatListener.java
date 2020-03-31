package fr.the4pe18.Robby.events;

import fr.the4pe18.Robby.LangUtils;
import fr.the4pe18.Robby.Robby;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ChatListener extends ListenerAdapter {
    private Robby instance;
    Random r = new Random();

    public ChatListener(Robby instance) {
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
            if (event.getAuthor().getIdLong() == 159985870458322944L) {
                String msg = event.getMessage().getContentRaw();
                msg = msg.replace(":XMARK6:", ":x:").replace(":CHECK6:", ":white_check_mark:");
                if (event.getMessage().getEmbeds().size() == 0) {
                    if (!msg.isEmpty()) event.getChannel().sendMessage(msg).queue();
                } else {
                    ArrayList<MessageEmbed> fromEmbeds = new ArrayList<>(event.getMessage().getEmbeds());
                    ArrayList<MessageEmbed> embeds = new ArrayList<>(event.getMessage().getEmbeds());
                    for (MessageEmbed embed : fromEmbeds) {
                        EmbedBuilder eb = new EmbedBuilder(embed);
                        eb.setColor(Color.orange);
                        embeds.add(eb.build());
                    }
                    MessageAction a;
                    if (!msg.isEmpty()) a = event.getChannel().sendMessage(msg);
                    else {
                        a = event.getChannel().sendMessage(embeds.get(0));
                        embeds.remove(0);
                    }
                    for (MessageEmbed embed : embeds) a = a.embed(embed);
                    a.queue();
                }
                event.getMessage().delete().queue();
            }
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();

        String[] args;
        args = content.split(" ");
        if (args[0].startsWith("!")) {
            String label = args[0].replace("!", "");
            args = LangUtils.removeFromArray(args, 0);
            if (getInstance().getCommandManager().getCommand(label) == null) return;
            getInstance().getCommandManager().getCommand(label).run(event.getGuild(), event.getChannel(), event.getGuild().getMember(event.getAuthor()), args);
        }

    }
}
