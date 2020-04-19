package fr.the4pe18.robby.commands;

import net.dv8tion.jda.api.audit.*;
import net.dv8tion.jda.api.entities.*;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author 4PE18
 */
public class GourceCommand extends AbstractCommand {

    public GourceCommand() {
        super("gource");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        /**EmbedBuilder eb= new EmbedBuilder();
        try {
            Document document = Jsoup.connect("https://fr.wikipedia.org/wiki/Sp%C3%A9cial:Page_au_hasard").followRedirects(true).get();
            String title = document.getElementById("firstHeading").text();
            String content = document.getElementsByClass("mw-parser-output").get(0).getElementsByTag("p").get(2).text();
            eb.setColor(Color.CYAN);
            eb.setTitle(title);
            eb.setDescription(content);
            eb.addField("Source:", document.location(), false);

        } catch (IOException e) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Impossible de contacter wikipedia :(");
        }
        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
        channel.sendMessage(eb.build()).queue();**/

        if (message.getAuthor().getIdLong() != 266208886204137472L) return;

        System.out.println("Retrieving full audit log...");
        List<AuditLogEntry> audits = new ArrayList<>();
        guild.retrieveAuditLogs().cache(false).forEach(audits::add);
        System.out.println("retreived: " + audits.size());
        Collections.reverse(audits);
        System.out.println("flipped: " + audits.size());


        audits.forEach(auditLogEntry -> {
            try {
                boolean display = false;
                String author;
                StringBuilder line = new StringBuilder(Long.toString(auditLogEntry.getTimeCreated().toEpochSecond())).append("|");
                try {
                    author = Objects.requireNonNull(auditLogEntry.getGuild().getMember(Objects.requireNonNull(auditLogEntry.getUser()))).getNickname();
                } catch (NullPointerException e) {
                    author = Objects.requireNonNull(auditLogEntry.getUser()).getName();
                }
                line.append(author).append("|");

                if (auditLogEntry.getType() == ActionType.CHANNEL_CREATE) {
                    line.append("A|e-school/channels/").append((String) auditLogEntry.getChangesForKeys(AuditLogKey.CHANNEL_NAME).get(0).getNewValue()).append(".channel");
                    display = true;
                }
                if (auditLogEntry.getType() == ActionType.CHANNEL_UPDATE) {
                    AuditLogChange c = auditLogEntry.getChangesForKeys(AuditLogKey.CHANNEL_NAME).get(0);
                    String oldValue = c.getOldValue();
                    String newValue = c.getNewValue();
                    assert oldValue != null;
                    if (oldValue.equals(newValue)) line.append("M|e-school/channels/").append(oldValue).append(".channel");
                    else {
                        StringBuilder line2 = new StringBuilder(line.toString());
                        line.append("D|e-school/channels/").append(oldValue).append(".channel");
                        line2.append("A|e-school/channels/").append(newValue).append(".channel");
                        line.append("\n").append(line2.toString());
                    }
                    display = true;
                }
                if (auditLogEntry.getType() == ActionType.CHANNEL_DELETE) {
                    line.append(author).append("D|e-school/channels/").append((String) auditLogEntry.getChangesForKeys(AuditLogKey.CHANNEL_NAME).get(0).getOldValue()).append(".channel");;
                    display = true;
                }
                if (display) System.out.println(line);
            } catch ( Exception e) {
                //e.printStackTrace();
            }

        });

    }
}
