package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.RobbyEmbed;
import fr.the4pe18.robby.events.SpecialApril;
import fr.the4pe18.robby.events.SpecialBlindTest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

/**
 * @author 4PE18
 */
public class BlindTestCommand extends AbstractCommand {
    private final SpecialBlindTest specialBlindTest;

    public BlindTestCommand(SpecialBlindTest specialBlindTest) {
        super("blindtest");
        this.specialBlindTest = specialBlindTest;
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        RobbyEmbed embed = new RobbyEmbed(":musical_note: __**BLIND TEST**__", "Évenement Blind Test !");
        embed.setColor(Color.RED);
        if (args.length == 0) {
            embed.setColor(Color.CYAN);
            embed.addField("Pseudonyme:", sender.getNickname(), true);
            embed.addField("Score:", specialBlindTest.getScore(sender.getId()).toString(), true);
            embed.setThumbnail(sender.getUser().getAvatarUrl());
            channel.sendMessage(embed.build()).queue();
            return;
        }
        embed.setAuthor(sender.getNickname(), sender.getUser().getAvatarUrl());
        if (!checkPerm(sender, 687751019341283403L)) {
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("ERREUR PERMISSION", "Vous n'avez pas la permission d'écécuter cette commande!", false);
            channel.sendMessage(embed.build()).queue();
            return;
        }
        if (args.length < 2 || !(args[0].equalsIgnoreCase("score") || args[0].equalsIgnoreCase("give"))) {
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("ERREUR SYNTAXE", "!blindtest <score <@utilisateur> | <give <@utilisateur> <points>>!", true);;
            channel.sendMessage(embed.build()).queue();
            return;
        }
        try {
            Member member = message.getMentionedMembers().get(0);
            if (args[0].equalsIgnoreCase("score")) {
                embed.setColor(Color.CYAN);
                embed.addField("Pseudonyme:", member.getNickname(), true);
                embed.addField("Score:", specialBlindTest.getScore(member.getId()).toString(), true);
                embed.setThumbnail(member.getUser().getAvatarUrl());
                channel.sendMessage(embed.build()).queue();
                return;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 3) {
                    embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
                    embed.addField("ERREUR SYNTAXE", "!blindtest <score <@utilisateur> | <give <@utilisateur> <points>>!", true);;
                    channel.sendMessage(embed.build()).queue();
                    return;
                }
                try {
                    Integer points = Integer.parseInt(args[2]);
                    specialBlindTest.addScore(member.getId(), points);
                    embed.setColor(Color.GREEN);
                    embed.addField("Succès", points.toString() + " points ajoutés à " + member.getNickname(), false);
                    embed.addBlankField(false);
                    embed.addField("Nouveau Score:", specialBlindTest.getScore(member.getId()).toString(), true);
                    embed.setThumbnail(member.getUser().getAvatarUrl());
                    channel.sendMessage(embed.build()).queue();
                }catch (Exception e) {
                    embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
                    embed.addField("ERREUR", "Impossible de parser le nombre de points " + args[2], true);;
                    channel.sendMessage(embed.build()).queue();
                    return;
                }
            }
        } catch (Exception e) {
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("ERREUR", "Impossible de trouver le membre " + args[1], true);;
            channel.sendMessage(embed.build()).queue();
            return;
        }
    }
}
