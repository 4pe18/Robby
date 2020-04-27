package fr.the4pe18.robby.events;

import fr.the4pe18.robby.LangUtils;
import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.RobbyUtils;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

/**
 * @author 4PE18
 */
public class ChatListener extends ListenerAdapter {
    private final Robby instance;
    Random r = new Random();

    public ChatListener(Robby instance) {
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getAuthor() == getInstance().getJdaInstance().getSelfUser()) return;
        if (!event.isFromGuild()) {
            if (!event.isFromType(ChannelType.GROUP)) {
                String message = event.getMessage().getContentRaw();
                if (event.getAuthor().getIdLong() == 266208886204137472L) {
                    if (message.startsWith("!send ")) {
                        String receiver = message.split(" ")[1];
                        getInstance().getJdaInstance().openPrivateChannelById(receiver).complete().sendMessage(message.replace("!send " + receiver + " ", "")).complete();
                    }
                } else {
                    getInstance().getChannel4pe18().sendMessage("Message from: " + event.getAuthor().getAsTag() + " (" + event.getAuthor().getId() + ")\n" + message).complete();
                }
            }
        }
        Message message = event.getMessage();
        String content = message.getContentRaw();

        String[] args = content.split(" ");
        if (args[0].startsWith("!")) {
            String label = args[0].replace("!", "");
            args = RobbyUtils.removeFromArray(args, 0);
            if (getInstance().getCommandManager().getCommand(label) == null) return;
            getInstance().getCommandManager().getCommand(label).run(event.getMessage(), event.isFromGuild() ? event.getGuild() : null, event.getChannel(), event.getMember(), args);
        }

    }
}
