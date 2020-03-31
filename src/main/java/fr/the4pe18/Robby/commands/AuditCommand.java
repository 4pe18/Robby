package fr.the4pe18.Robby.commands;

import fr.the4pe18.Robby.audit.AuditsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.Arrays;

public class AuditCommand extends AbstractCommand {
    AuditsManager manager;

    public AuditCommand(AuditsManager manager) {
        super("audit");
        this.manager = manager;
    }

    @Override
    public void run(Guild guild, MessageChannel channel, Member sender, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        if (!sender.getRoles().contains(guild.getRoleById(687751019341283403L))) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Vous n'avez pas la permission d'executer cette commande!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
        } else {
            if (args.length < 2) {
                eb.setTitle(":x: Erreur !", null);
                eb.setColor(Color.red);
                eb.setDescription("/audit <start|stop> <channel id>!");
                eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                channel.sendMessage(eb.build()).queue();
            } else {
                if (args[0].equalsIgnoreCase("start")) {
                    VoiceChannel voiceChannel = (VoiceChannel) guild.getGuildChannelById(args[1]);
                    assert voiceChannel != null;
                    manager.addVocalAudit(voiceChannel);
                    manager.start(voiceChannel);
                    System.out.println("started!");
                }
                if (args[0].equalsIgnoreCase("stop")) {
                    VoiceChannel voiceChannel = (VoiceChannel) guild.getGuildChannelById(args[1]);
                    assert voiceChannel != null;
                    manager.stop(voiceChannel);
                    System.out.println("stopped!");
                    String json = manager.getJSON(voiceChannel);
                    System.out.println(json);
                    channel.sendMessage("```json\n" + json + "```").complete();
                    manager.removeVocalAudit(voiceChannel);
                }
            }
        }
    }
}
