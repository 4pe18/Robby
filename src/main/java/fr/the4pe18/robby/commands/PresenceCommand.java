package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.RobbyEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PresenceCommand extends AbstractCommand {

    public PresenceCommand() {
        super("presence");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        message.delete().complete();
        RobbyEmbed embed = new RobbyEmbed("__**PRESENCE**__", "Erreur !").setColor(Color.RED);
        EmbedBuilder eb = new EmbedBuilder();
        if (!checkPerm(sender, 689033973367570446L, 695250710756982854L, 687751019341283403L)) {
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("PERMISSION", "Vous n'avez pas la permission d'écécuter cette commande.", false);
            channel.sendMessage(embed.build()).queue();
        } else {
            //noinspection ConstantConditions //According to doc, this will be null when the CacheFlag.VOICE_STATE is disabled manually - this isn't the case
            if (!sender.getVoiceState().inVoiceChannel()) {
                embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
                embed.addField("SALON", "Vous devez être dans un salon vocal pour éxécuter cette commande.", false);
                channel.sendMessage(embed.build()).queue();
            } else {
                assert sender.getVoiceState().getChannel() == null;
                List<Role> roles = new ArrayList<>();
                List<Member> members = new ArrayList<>();
                List<Member> online = new ArrayList<>();
                List<Member> offline = new ArrayList<>();
                for (PermissionOverride po : sender.getVoiceState().getChannel().getPermissionOverrides()) {
                    if (po.isRoleOverride()) {
                        if (po.getRole().getPosition() < guild.getRoleById(689418720967131138L).getPosition()) roles.add(po.getRole());
                    }
                }
                guild.retrieveMembers().thenRun(() -> {
                    for (Member m : guild.getMemberCache().asList()) {
                        for (Role r : roles) {
                            if (m.getIdLong() != 688103953417764876L && m.getRoles().contains(r) && !members.contains(m)) members.add(m);
                        }
                    }
                    for (Member m : members) {
                        if (m.getVoiceState().inVoiceChannel() && m.getVoiceState().getChannel().getIdLong() == sender.getVoiceState().getChannel().getIdLong()) online.add(m);
                        else offline.add(m);
                    }
                });
                embed = new RobbyEmbed("__**PRESENCE**__", "Connectés au salon vocal:");
                embed.addField("Salon Textuel", channel.getName(), true);
                embed.addField("Salon Vocal", sender.getVoiceState().getChannel().getName(), true);
                embed.addBlankField(false);
                embed.addField("<:yes:705017799554236536>" + " Présents (" + online.size() + "/" + members.size() + ")", online.stream().map(Member::getEffectiveName).collect(Collectors.joining(" - ")), false);
                embed.addField("<:no:705017781556346950>" + " Absents (" + offline.size() + "/" + members.size() + ")", offline.stream().map(Member::getEffectiveName).collect(Collectors.joining(" - ")), false);
                channel.sendMessage(embed.build()).queue();

            }
        }
        }
}
