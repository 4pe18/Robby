package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.RobbyEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ClearCommand extends AbstractCommand {


    public ClearCommand() {
        super("clear");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        RobbyEmbed embed = new RobbyEmbed("__**NETTOYAGE**__", "Erreur !").setColor(Color.RED);
        EmbedBuilder eb = new EmbedBuilder();
        if (!checkPerm(sender, 687752918777987213L, 689764132760584240L, 687751019341283403L)) {
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("PERMISSION", "Vous n'avez pas la permission d'écécuter cette commande.", false);
            channel.sendMessage(embed.build()).queue();
            return;
        }
        if (args.length == 0) {
            embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
            embed.addField("SYNTAXE", "!clear <nombre de message | *> [[<! | !+ | +>]<@role | @pseudo>]...", false);
            channel.sendMessage(embed.build()).queue();
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                int count;
                if (args[0].equalsIgnoreCase("*")) count = -2;
                else if (args[0].contains("-")) {
                    embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
                    embed.addField("ARGUMENT", "Le nombre de messages ne peut pas être négatif !", false);
                    channel.sendMessage(embed.build()).queue();
                    return;
                } else try {
                    count = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    embed.setThumbnail("https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png");
                    embed.addField("ARGUMENT", "Le nombre de messages n'est pas valide !", false);
                    channel.sendMessage(embed.build()).queue();
                    return;
                }
                RobbyEmbed embed = new RobbyEmbed("__**NETTOYAGE**__", "Calcul en cours...").setColor(Color.ORANGE);
                embed.setThumbnail("https://i.ibb.co/ZYGM6dw/617c1718e07ad475bacecb7e5e401dd3.png");
                Message temp = channel.sendMessage(embed.build()).complete();
                count++;
                List<Message> history = new ArrayList<>();
                Message lastMessage = message;
                if (count == -1) {
                    for(boolean reached = false; !reached;) {
                        List<Message> getting = MessageHistory.getHistoryBefore(channel, lastMessage.getId()).limit(100).complete().getRetrievedHistory();
                        if (getting.size() > 0) {
                            history.addAll(getting);
                            lastMessage = getting.get(getting.size() - 1);
                        }
                        else reached = true;
                    }
                } else if (count <= 100) history = MessageHistory.getHistoryBefore(channel, message.getId()).limit(count).complete().getRetrievedHistory();
                else {
                    for(boolean reached = false; !reached;) {
                        List<Message> getting = MessageHistory.getHistoryBefore(channel, lastMessage.getId()).limit(Math.min(100, count)).complete().getRetrievedHistory();
                        if (getting.size() > 0) {
                            history.addAll(getting);
                            lastMessage = getting.get(getting.size() - 1);
                            count -= 100;
                            if (count <= 0) reached = true;
                        }
                        else reached = true;
                    }
                }
                if (args.length == 1) {
                    //TODO
                }
                message.delete().complete();
                temp.delete().complete();
                embed = new RobbyEmbed("__**NETTOYAGE**__", "Nettoyage en cours...").setColor(Color.BLUE);
                embed.setAuthor(sender.getNickname(), sender.getUser().getAvatarUrl());
                embed.setThumbnail("https://i.ibb.co/ZYGM6dw/617c1718e07ad475bacecb7e5e401dd3.png");
                embed.addField("Instigateur", sender.getNickname(), true);
                embed.addField("Salon", channel.getName(), true);
                embed.addField("Taille", history.size() + " message" + (history.size() > 1 ? "s" : ""), true);
                Message m = channel.sendMessage(embed.build()).complete();
                List<CompletableFuture<Void>> l = channel.purgeMessages(history);
                l.get(l.size() -1).thenRun(() -> m.delete().queue());
            }
        }.start();
        }

    private boolean canDelete(Member author, Member deleter) {
        if (author == null) return true;
        if (checkPerm(author, 693810529856258099L)) return checkPerm(deleter, 693810529856258099L);
        if (checkPerm(author, 687751019341283403L)) return checkPerm(deleter, 693810529856258099L, 687751019341283403L);
        return true;
    }
}
