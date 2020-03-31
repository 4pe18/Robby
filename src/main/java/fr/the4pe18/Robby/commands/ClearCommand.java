package fr.the4pe18.Robby.commands;

import fr.the4pe18.Robby.audit.AuditsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.awt.*;

public class ClearCommand extends AbstractCommand {
    AuditsManager manager;

    public ClearCommand(AuditsManager manager) {
        super("clear");
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

        }
    }
}
