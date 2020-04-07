package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.deploy.exceptions.PatchNotLoadedException;
import fr.the4pe18.robby.deploy.exceptions.PatchNotRegisteredException;
import fr.the4pe18.robby.old.deploy.patchs.OldModoManageMsg_01;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.Objects;

/**
 * @author 4PE18
 */
public class DeployCommand extends AbstractCommand {
    private Robby instance;

    public DeployCommand(Robby instance) {
        super("deploy");
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    @Override
    public void run(Message message, Guild guild, MessageChannel channel, Member sender, String[] args) {
        EmbedBuilder eb;

        if (!sender.getRoles().contains(guild.getRoleById(687751019341283403L))) {

            eb = new EmbedBuilder();
            eb.setTitle(":x: Erreur !", null);
            eb.setColor(Color.red);
            eb.setDescription("Vous n'avez pas la permission d'executer cette commande!");
            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
            channel.sendMessage(eb.build()).queue();

        } else {
            if (args.length > 0) {
                try {
                    this.getInstance().getDeploymentManager().deploy(args[0], guild, channel, sender, args);
                } catch (PatchNotRegisteredException e) {
                    eb = new EmbedBuilder();
                    eb.setTitle(":diamond_shape_with_a_dot_inside: ERREUR... :diamond_shape_with_a_dot_inside:", null);
                    eb.setColor(Color.red);
                    eb.setDescription("Le patch n'est pas enregistré!");
                    eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                    channel.sendMessage(eb.build()).complete();
                } catch (PatchNotLoadedException e) {
                    eb = new EmbedBuilder();
                    eb.setTitle(":diamond_shape_with_a_dot_inside: ERREUR... :diamond_shape_with_a_dot_inside:", null);
                    eb.setColor(Color.red);
                    eb.setDescription("Le patch n'est pas chargé!");
                    eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                    channel.sendMessage(eb.build()).complete();
                }
            } else {
                eb = new EmbedBuilder();
                eb.setTitle(":x: Erreur !", null);
                eb.setColor(Color.red);
                eb.setDescription("Patch inconnu!");
                eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                channel.sendMessage(eb.build()).queue();
            }
            /**
            if (args.length >= 1 && args[0].equalsIgnoreCase("ModoManageMsg_01")) {
                //new OldModoManageMsg_01().deploy(guild, channel, sender, args);
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("ClassCreator")) {
                eb = new EmbedBuilder();
                eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                eb.setColor(Color.orange);
                eb.setDescription("Déploiement du patch ClassCreator démarré...");
                eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                channel.sendMessage(eb.build()).complete();

                if (args.length >= 3) {
                    String classe = "\uD83D\uDC65 " +args[1].replace("_", " ");
                    boolean roleNonExisting = true;
                    for (Role r : guild.getRoles()) {
                        if (r.getName().equalsIgnoreCase(classe)) {
                            roleNonExisting = false;

                            eb = new EmbedBuilder();
                            eb.setTitle(":x: Erreur !", null);
                            eb.setColor(Color.red);
                            eb.setDescription("Classe déjà existante!");
                            eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                            channel.sendMessage(eb.build()).complete();

                        }
                    }
                    if (roleNonExisting) {

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Création du rôle " + classe);
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Role classeRole = guild.createRole().setName(classe).setHoisted(true).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Créé!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Déplacement du rôle dans la hiérarchie...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        guild.modifyRolePositions().selectPosition(classeRole).moveUp(37).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Déplacé!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Création du salon textuel de classe...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        TextChannel textChannel = Objects.requireNonNull(guild.getCategoryById(689027868575268877L)).createTextChannel("\uD83D\uDC65-salon-"+ args[2].toLowerCase()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Créé!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Modification des permissions de everyone du salon textuel...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Objects.requireNonNull(guild.getGuildChannelById(textChannel.getId())).putPermissionOverride(Objects.requireNonNull(guild.getRoleById(687748971430805525L))).setDeny(Permission.ALL_PERMISSIONS).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Modifié!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Modification des permissions de la classe du salon textuel...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Objects.requireNonNull(guild.getGuildChannelById(textChannel.getId())).putPermissionOverride(classeRole).setDeny(Permission.ALL_PERMISSIONS).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_ADD_REACTION).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Modifié!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Modification des permissions des modérateurs chat du salon textuel...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Objects.requireNonNull(guild.getGuildChannelById(textChannel.getId())).putPermissionOverride(Objects.requireNonNull(guild.getRoleById(689764132760584240L))).setAllow(Permission.MESSAGE_MANAGE).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Modifié!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Création du salon vocal de classe...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        VoiceChannel voiceChannel = Objects.requireNonNull(guild.getCategoryById(690244022639657005L)).createVoiceChannel("\uD83D\uDC65 Salon "+ args[2].toUpperCase()).setUserlimit(50).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Créé!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Modification des permissions de everyone du salon vocal...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Objects.requireNonNull(guild.getGuildChannelById(voiceChannel.getId())).putPermissionOverride(Objects.requireNonNull(guild.getRoleById(687748971430805525L))).setDeny(Permission.ALL_PERMISSIONS).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Modifié!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Modification des permissions de la classe du salon vocal...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Objects.requireNonNull(guild.getGuildChannelById(voiceChannel.getId())).putPermissionOverride(classeRole).setDeny(Permission.ALL_PERMISSIONS).setAllow(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK, Permission.VOICE_STREAM, Permission.VOICE_USE_VAD).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Modifié!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Modification des permissions du SEEVOCS du salon vocal...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Objects.requireNonNull(guild.getGuildChannelById(voiceChannel.getId())).putPermissionOverride(Objects.requireNonNull(guild.getRoleById(689418720967131138L))).setAllow(Permission.VIEW_CHANNEL).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Modifié!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.orange);
                        eb.setDescription("Modification des permissions des modérateurs du salon vocal...");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        Objects.requireNonNull(guild.getGuildChannelById(voiceChannel.getId())).putPermissionOverride(Objects.requireNonNull(guild.getRoleById(689033973367570446L))).setAllow(Permission.VOICE_MUTE_OTHERS, Permission.VOICE_DEAF_OTHERS).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription("Modifié!");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();

                        eb = new EmbedBuilder();
                        eb.setTitle(":diamond_shape_with_a_dot_inside: Déploiement... :diamond_shape_with_a_dot_inside:", null);
                        eb.setColor(Color.green);
                        eb.setDescription(":white_check_mark: DÉPLOIEMENT TERMINÉ! :white_check_mark:");
                        eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                        channel.sendMessage(eb.build()).complete();
                    }
                } else {

                    eb = new EmbedBuilder();
                    eb.setTitle(":x: Erreur !", null);
                    eb.setColor(Color.red);
                    eb.setDescription("Merci de préciser la classe et l'identifiant salon !");
                    eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                    channel.sendMessage(eb.build()).queue();

                }
            } else {

                eb = new EmbedBuilder();
                eb.setTitle(":x: Erreur !", null);
                eb.setColor(Color.red);
                eb.setDescription("Patch inconnu!");
                eb.setFooter("Bip. Robby. Bip boup. Je suis un robot.", "https://cdn.discordapp.com/avatars/688391905427456027/e04068c2b59feadbe446fd813b688fa6.png?size=128");
                channel.sendMessage(eb.build()).queue();

            }**/
        }
    }
}
