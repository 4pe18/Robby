package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.RobbyEmbed;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

public class PresenceCommand extends AbstractCommand {

  public PresenceCommand() { super("presence"); }

  @Override
  public void run(Message message, Guild guild, MessageChannel channel,
                  Member sender, String[] args) {
    message.delete().complete();
    final RobbyEmbed[] embed = {
        new RobbyEmbed("__**PRESENCE**__", "Erreur !").setColor(Color.RED)};
    EmbedBuilder eb = new EmbedBuilder();
    if (!checkPerm(sender, 689033973367570446L, 695250710756982854L,
                   687752918777987213L, 687751019341283403L)) {
      embed[0].setThumbnail(
          "https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
      embed[0].addField(
          "PERMISSION",
          "Vous n'avez pas la permission d'écécuter cette commande.", false);
      channel.sendMessage(embed[0].build()).queue();
    } else {
      // noinspection ConstantConditions //According to doc, this will be null
      // when the CacheFlag.VOICE_STATE is disabled manually - this isn't the
      // case
      if (!sender.getVoiceState().inVoiceChannel()) {
        embed[0].setThumbnail(
            "https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
        embed[0].addField(
            "SALON",
            "Vous devez être dans un salon vocal pour éxécuter cette commande.",
            false);
        channel.sendMessage(embed[0].build()).queue();
      } else {
        assert sender.getVoiceState().getChannel() == null;
        List<Role> roles = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        List<Member> online = new ArrayList<>();
        List<Member> offline = new ArrayList<>();
        for (PermissionOverride po :
             sender.getVoiceState().getChannel().getPermissionOverrides()) {
          if (po.isRoleOverride()) {
            if (po.getRole().getPosition() <
                guild.getRoleById(689418720967131138L).getPosition())
              roles.add(po.getRole());
          }
        }
        guild.retrieveMembers().thenRun(() -> {
          for (Member m : guild.getMemberCache().asList()) {
            for (Role r : roles) {
              if (m.getIdLong() != 688103953417764876L &&
                  m.getRoles().contains(r) && !members.contains(m))
                members.add(m);
            }
          }
          for (Member m : members) {
            if (m.getVoiceState().inVoiceChannel() &&
                m.getVoiceState().getChannel().getIdLong() ==
                    sender.getVoiceState().getChannel().getIdLong())
              online.add(m);
            else
              offline.add(m);
          }

          embed[0] =
              new RobbyEmbed("__**PRESENCE**__", "Connectés au salon vocal:");
          embed[0].addField("Salon Textuel", channel.getName(), true);
          embed[0].addField("Salon Vocal",
                            sender.getVoiceState().getChannel().getName(),
                            true);
          embed[0].addBlankField(false);

          StringBuilder presents = new StringBuilder();
          boolean mainPresentsAdded = false;
          for (String n : online.stream()
                              .map(Member::getEffectiveName)
                              .collect(Collectors.toList())) {
            presents.append(n);
            if (presents.length() >= 500) {
              embed[0].addField(
                  (mainPresentsAdded ? "\u200b"
                                     : ("<:yes:705017799554236536>"
                                        + " Présents (" + online.size() + "/" +
                                        members.size() + ")")),
                  splitStringEvery(presents.toString(), 1020)[0], true);
              mainPresentsAdded = true;
              presents = new StringBuilder();
            } else
              presents.append(" - ");
          }
          if (presents.length() > 3) {
            embed[0].addField((mainPresentsAdded
                                   ? "\u200b"
                                   : ("<:yes:705017799554236536>"
                                      + " Présents (" + online.size() + "/" +
                                      members.size() + ")")),
                              presents.substring(0, presents.length() - 3),
                              mainPresentsAdded);
          } else if (!mainPresentsAdded) {
            embed[0].addField("<:yes:705017799554236536>"
                                  + " Présents (" + online.size() + "/" +
                                  members.size() + ")",
                              "\u200b", false);
          }
          StringBuilder absents = new StringBuilder();
          boolean mainAbsentsAdded = false;
          for (String n : offline.stream()
                              .map(Member::getEffectiveName)
                              .collect(Collectors.toList())) {
            absents.append(n);
            if (absents.length() >= 500) {
              embed[0].addField(
                  (mainAbsentsAdded ? "\u200b"
                                    : ("<:no:705017781556346950>"
                                       + " Absents (" + offline.size() + "/" +
                                       members.size() + ")")),
                  splitStringEvery(absents.toString(), 1020)[0], true);
              mainAbsentsAdded = true;
              absents = new StringBuilder();
            } else
              absents.append(" - ");
          }
          if (absents.length() > 3) {
            embed[0].addField(
                (mainAbsentsAdded ? "\u200b"
                                  : ("<:no:705017781556346950>"
                                     + " Absents (" + offline.size() + "/" +
                                     members.size() + ")")),
                absents.substring(0, absents.length() - 3), mainAbsentsAdded);
          } else if (!mainAbsentsAdded) {
            embed[0].addField("<:no:705017781556346950>"
                                  + " Absents (" + offline.size() + "/" +
                                  members.size() + ")",
                              "\u200b", false);
          }
          channel.sendMessage(embed[0].build()).queue();
        });
      }
    }
  }

  public String[] splitStringEvery(String s, int interval) {
    if (s.length() <= interval)
      return new String[] {s};
    int arrayLength = (int)Math.ceil(((s.length() / (double)interval)));
    String[] result = new String[arrayLength];

    int j = 0;
    int lastIndex = result.length - 1;
    for (int i = 0; i < lastIndex; i++) {
      result[i] = s.substring(j, j + interval);
      j += interval;
    } // Add the last bit
    result[lastIndex] = s.substring(j);

    return result;
  }
}
