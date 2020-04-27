package fr.the4pe18.robby.events;

import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.RobbyEmbed;
import fr.the4pe18.robby.deploy.PatchStepStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author 4PE18
 */
public class ReactionListener extends ListenerAdapter {
    private final Robby instance;

    public ReactionListener(Robby instance) {
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        getInstance().getPollManager().proceedEvent(event);
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        getInstance().getPollManager().proceedEvent(event);
        try {
            if (event.getMember().getRoles().contains(event.getGuild().getRoleById(689033973367570446L))) {
                if (event.getChannel().getIdLong() == 691957351288275055L || event.getChannel().getIdLong() == 689125601998929935L) {
                    if (event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("✅")) {

                        Message message = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
                        //String message = m.getContentRaw() + "\n";
                        //System.out.println(message);

                        RobbyEmbed embed = new RobbyEmbed("__**SUPPORT**__", "Demande traitée").setColor(Color.GREEN);
                        embed.setAuthor(event.getGuild().getName(), event.getGuild().getIconUrl());
                        embed.addField("Demande", message.getContentStripped(), false);
                        embed.addField("Traitée par", event.getMember().getNickname(), true);
                        embed.addField("Salon", event.getChannel().getName(), true);
                        embed.setThumbnail("https://i.ibb.co/Ry7zpK4/212e30e47232be03033a87dc58edaa95.png");
                        message.getAuthor().openPrivateChannel().queue(c -> c.sendMessage(embed.build()).queue(ign -> message.delete().queue()));

                    }
                }
            }
        }catch (NullPointerException ignored) {}
    }

    /*
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
    }*/
}