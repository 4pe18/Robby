package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.RobbyEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MeCommand extends AbstractCommand {

    public MeCommand() {
        super("me");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        message.delete().complete();
        RobbyEmbed embed = new RobbyEmbed("Lien vers les salons:", "*Ce message s'auto-supprimera dans 10 secondes*");
        embed.setThumbnail(guild.getIconUrl());
        embed.setAuthor(sender.getEffectiveName(), sender.getUser().getAvatarUrl());
        List<Role> roles = sender.getRoles().stream().filter(r -> r.getPosition() < guild.getRoleById(689418720967131138L).getPosition()).collect(Collectors.toList());
        sender.getRoles().stream().filter(r -> r.getPosition() > guild.getRoleById(689418720967131138L).getPosition()).findAny().ifPresent(role -> embed.addField("**ADMINISTRATION**", "<#688077573208801341> <#690936423229947974> <#694220404847542455>", false));
        List<GuildChannel> channels = guild.getTextChannels().stream().filter(t -> roles.stream().anyMatch(r -> r.hasPermission(t, Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY))).collect(Collectors.toList());
        Map<Category, List<GuildChannel>> map = new HashMap<>();
        guild.getCategories().forEach(category -> map.put(category, category.getChannels().stream().filter(channels::contains).collect(Collectors.toList())));
        map.forEach((category, channels1) -> {
            if (channels1.size() != 0) embed.addField("**" + category.getName() + "**", channels1.stream().map(ISnowflake::getId).map(c -> "<#" + c + ">").collect(Collectors.joining(" ")), false);
        });
        channel.sendMessage(embed.build()).queue(new Consumer<Message>() {
            @Override
            public void accept(Message message) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    message.delete().queue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
