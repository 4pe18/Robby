package fr.the4pe18.robby.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand extends AbstractCommand {


    public ClearCommand() {
        super("clear");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        if (!checkPerm(sender, 687752918777987213L, 689764132760584240L, 687751019341283403L)) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Vous n'avez pas la permission d'executer cette commande!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        }
        if (args.length == 0) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Erreur de syntaxe, utilisez !clear <nombre de message | *> [[<! | !+ | +>]<@role | @pseudo>]... !");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        }
        int count;
        if (args[0].equalsIgnoreCase("*")) count = -1;
        else if (args[0].contains("-")) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Le nombre de message ne peux pas être négatif !");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        } else try {
            count = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Merci de préciser un nombre valide !");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        }
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
        channel.purgeMessages(history);
        channel.sendMessage("Deleting: " + history.size() + " message(s)...").complete().delete().completeAfter(7, TimeUnit.SECONDS);
    }

    private boolean canDelete(Member author, Member deleter) {
        if (author == null) return true;
        if (checkPerm(author, 693810529856258099L)) return checkPerm(deleter, 693810529856258099L);
        if (checkPerm(author, 687751019341283403L)) return checkPerm(deleter, 693810529856258099L, 687751019341283403L);
        return true;
    }
}
