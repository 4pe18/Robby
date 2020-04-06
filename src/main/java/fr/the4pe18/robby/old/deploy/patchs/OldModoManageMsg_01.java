package fr.the4pe18.robby.old.deploy.patchs;

import fr.the4pe18.robby.old.deploy.OldRobbyDeploy;
import fr.the4pe18.robby.old.deploy.OldDeployEmbed;
import fr.the4pe18.robby.old.deploy.exceptions.OldDeployException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.managers.ChannelManager;

import java.util.Objects;

/**
 * @autor 4PE18
 * @deprecated
 */
public final class OldModoManageMsg_01 extends OldRobbyDeploy {

    public OldModoManageMsg_01() {
        super("ModoManageMsg_01");
    }

    @Override
    protected void patch() throws OldDeployException {
        final GuildChannel guildChannel = step(true,"", (guild, channel, sender, args) -> guild.getGuildChannelById(channel.getIdLong()));
        final ChannelManager manager = step(true,"", (guild, channel, sender, args) -> guildChannel.getManager());
        step(false, "", (guild, channel, sender, args) -> {
                    for (PermissionOverride permissionOverride : guildChannel.getPermissionOverrides()) {
                        if (Objects.requireNonNull(permissionOverride.getRole()).getIdLong() == 689764132760584240L || Objects.requireNonNull(permissionOverride.getRole()).getIdLong() == 689033973367570446L) {
                            channel.sendMessage(OldDeployEmbed.STEP_STARTING.build("Suppression du PermissionOverride....", "PermissionOverride: ", permissionOverride.toString())).complete();
                            manager.removePermissionOverride(Objects.requireNonNull(permissionOverride.getRole())).complete();
                            channel.sendMessage(OldDeployEmbed.STEP_SUCCESS.build("Supprimé!")).complete();
                        }
                    }
                    return null;
                });
        step(true,"", (guild, channel, sender, args) -> {
            channel.sendMessage(OldDeployEmbed.STEP_STARTING.build("Création du PermissionOverride...")).complete();
            guildChannel.putPermissionOverride(Objects.requireNonNull(guild.getRoleById(689764132760584240L))).setAllow(Permission.MESSAGE_MANAGE).complete();
            channel.sendMessage(OldDeployEmbed.STEP_SUCCESS.build("Créé!")).complete();
            return null;
        });
    }
}
