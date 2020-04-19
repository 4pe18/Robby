package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.events.SpecialApril;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

/**
 * @author 4PE18
 */
public class AprilCommand extends AbstractCommand {
    private final SpecialApril specialApril;

    public AprilCommand(SpecialApril specialApril) {
        super("april");
        this.specialApril = specialApril;
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        if (args.length == 0) {
            eb.setTitle(":fish: Joyeux premier avril !", null);
            eb.setColor(Color.blue);
            eb.setDescription("Vous avez " + specialApril.getScore(sender.getId()) + " points!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        }
        if (!checkPerm(sender, 687752918777987213L, 689764132760584240L, 687751019341283403L)) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Vous n'avez pas la permission d'executer cette commande!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        }
        if (args.length < 2 || !(args[0].equalsIgnoreCase("score") || args[0].equalsIgnoreCase("switch"))) {
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Erreur de syntaxe, utilisez !april <score <id utilisateur> | switch <id salon>>!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        }
        if (args[0].equalsIgnoreCase("score")) {
            eb.setTitle(":fish: Joyeux premier avril !", null);
            eb.setColor(Color.blue);
            eb.setDescription(args[1] + " a " + specialApril.getScore(args[1]) + " points!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
            return;
        }
        if (args[0].equalsIgnoreCase("switch")) {
            boolean mode = specialApril.switchChannel(args[1]);
            eb.setTitle(":fish: Joyeux premier avril !", null);
            eb.setColor(Color.blue);
            eb.setDescription("Le mode 1er avril dans le salon " + args[1] + " a été " + (mode ? "activé" : "désactivé") + "!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();
        }
    }
}
