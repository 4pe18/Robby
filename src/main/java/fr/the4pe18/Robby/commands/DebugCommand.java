package fr.the4pe18.Robby.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.Arrays;

public class DebugCommand extends AbstractCommand {

    public DebugCommand() {
        super("debug");
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        if (!sender.getRoles().contains(guild.getRoleById(687751019341283403L))) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Vous n'avez pas la permission d'executer cette commande!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
        } else {
            eb.setTitle(":diamond_shape_with_a_dot_inside: Debug :diamond_shape_with_a_dot_inside:", null);
            eb.setColor(Color.green);
            eb.setDescription("Debug commande: " + Arrays.toString(args) + "\nNombre ce salons sur le discord: " + guild.getChannels().size() + "\nCompte des membres:");
            eb.addField("Nombre total de membre: ", Integer.toString(guild.getMemberCount()), false);
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            for (Role role : guild.getRoles()) {
                eb = new EmbedBuilder();
                eb.setColor(role.getColor());
                eb.setTitle(role.getName());
                eb.setDescription("Nombre:"  + guild.getMembersWithRoles(role).size());
                channel.sendMessage(eb.build());
            }
        }
    }
}
