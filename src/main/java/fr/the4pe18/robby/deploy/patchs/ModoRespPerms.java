package fr.the4pe18.robby.deploy.patchs;

import fr.the4pe18.robby.deploy.RobbyPatch;
import fr.the4pe18.robby.exceptions.PatchIgnoredException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.managers.ChannelManager;

public class ModoRespPerms extends RobbyPatch {
    public ModoRespPerms() {
        super("ModoRespPerms");
    }

    @Override
    protected void patch() {
        final GuildChannel guildChannel = step(true, "Get GuildChannel", c -> c.getGuild().getGuildChannelById(c.getChannel().getId()));
        final ChannelManager channelManager = step(true, "Get ChannelManager", c -> guildChannel.getManager());
        final Role roleR = step(true, "Get Role R", c -> c.getGuild().getRoleById(695250710756982854L));
        final Role roleM = step(true, "Get Role M", c -> c.getGuild().getRoleById(689033973367570446L));
        step(false, "- R PermissionOverride", context -> {
            try {
                guildChannel.getPermissionOverrides().stream().filter(po -> po.getRole() == roleR).forEach(po -> channelManager.clearOverridesRemoved().removePermissionOverride(po.getRole()).complete());
            } catch (NullPointerException e) {
                throw new PatchIgnoredException(e);
            }
            return null;
        });
        step(false, "- M PermissionOverride", context -> {
            try {
                guildChannel.getPermissionOverrides().stream().filter(po -> po.getRole() == roleM).forEach(po -> channelManager.clearOverridesRemoved().removePermissionOverride(po.getRole()).complete());
            }catch (NullPointerException e) {
                throw new PatchIgnoredException(e);
            }
            return null;
        });
        step(false, "+ R PermissionOverride", context -> {
            guildChannel.putPermissionOverride(roleR).setDeny(Permission.ALL_PERMISSIONS).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_MANAGE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY, Permission.MESSAGE_MENTION_EVERYONE, Permission.MESSAGE_ADD_REACTION).complete();
            return null;
        });
        step(false, "+ M PermissionOverride", context -> {
            guildChannel.putPermissionOverride(roleM).setDeny(Permission.CREATE_INSTANT_INVITE, Permission.MANAGE_CHANNEL, Permission.MANAGE_PERMISSIONS, Permission.MANAGE_WEBHOOKS).setAllow(Permission.MESSAGE_MANAGE, Permission.MESSAGE_MENTION_EVERYONE).complete();
            return null;
        });
    }
}
