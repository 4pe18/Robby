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
        if (event.getMessage().getContentRaw().equalsIgnoreCase("showthehollyfish")) event.getMessage().addReaction("superfish:694689673435676782").complete();
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();

        String[] args;
        args = content.split(" ");
        if (args[0].startsWith("!")) {
            String label = args[0].replace("!", "");
            args = LangUtils.removeFromArray(args, 0);
            if (getInstance().getCommandManager().getCommand(label) == null) return;
            getInstance().getCommandManager().getCommand(label).run(event.getMessage(), event.getGuild(), event.getChannel(), event.getMember(), args);
        }

    }
}
