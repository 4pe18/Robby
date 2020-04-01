package fr.the4pe18.Robby.events;

import fr.the4pe18.Robby.Robby;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class ReactionListener extends ListenerAdapter {
    private Robby instance;

    public ReactionListener(Robby instance) {
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        //System.out.println(event.getReaction().getReactionEmote().getAsCodepoints());
        try {
            if (Objects.requireNonNull(event.getGuild().getMember(Objects.requireNonNull(event.getUser()))).getRoles().contains(event.getGuild().getRoleById(689033973367570446L))) {
                if (event.getChannel().getIdLong() == 689125601998929935L) {
                    if (event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("✅")) {
                        Message m = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
                        String message = m.getContentRaw() + "\n";

                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle(":white_check_mark: Support", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Votre demande: \n" + message + " a été traitée par " + Objects.requireNonNull(event.getGuild().getMember(Objects.requireNonNull(event.getUser()))).getNickname() + ".\n*Merci de nous aider à améliorer le discord!*");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");

                        m.getAuthor().openPrivateChannel().complete().sendMessage(eb.build()).queue();
                        m.delete().queue();
                    }
                }
            }
        }catch (NullPointerException e) {}
    }

    /*
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
    }*/
}