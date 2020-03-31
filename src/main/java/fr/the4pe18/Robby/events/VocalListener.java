package fr.the4pe18.Robby.events;

import fr.the4pe18.Robby.Robby;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class VocalListener extends ListenerAdapter {
    private Robby instance;

    public VocalListener(Robby instance) {
        System.out.println("Instance!!");
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        instance.getAuditsManager().userJoin(event.getChannelJoined(), event.getMember());
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        instance.getAuditsManager().userLeave(event.getChannelLeft(), event.getMember());
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        instance.getAuditsManager().userJoin(event.getChannelJoined(), event.getMember());
        instance.getAuditsManager().userLeave(event.getChannelLeft(), event.getMember());
    }
}
