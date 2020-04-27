package fr.the4pe18.robby.poll;

import fr.the4pe18.robby.Robby;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.HashMap;

public class PollManager {
    private HashMap<Long, Poll> polls = new HashMap<>();
    private Robby instance;

    public PollManager(Robby instance) {
        this.instance = instance;
    }

    public void addPoll(Poll p) {
        polls.put(p.getMessage().getIdLong(), p);
        p.updateMessage();
    }

    public void proceedEvent(MessageReactionAddEvent event) {
        if (event.getMember() != null && event.getUser().getIdLong() == Robby.getJdaInstance().getSelfUser().getIdLong()) return;
        if (polls.containsKey(event.getMessageIdLong())) polls.get(event.getMessageIdLong()).proceed(event);
    }

    public void proceedEvent(MessageReactionRemoveEvent event) {
        if (event.getMember() != null && event.getMember().getIdLong() == Robby.getJdaInstance().getSelfUser().getIdLong()) return;
        if (polls.containsKey(event.getMessageIdLong())) polls.get(event.getMessageIdLong()).proceed(event);
    }
}
