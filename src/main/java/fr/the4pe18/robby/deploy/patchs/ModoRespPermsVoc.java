package fr.the4pe18.robby.deploy.patchs;

import fr.the4pe18.robby.deploy.RobbyPatch;
import fr.the4pe18.robby.exceptions.PatchIgnoredException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.ChannelManager;

public class ModoRespPermsVoc extends RobbyPatch {
    public ModoRespPermsVoc() {
        super("ModoRespPermsVoc");
    }

    @Override
    protected void patch() {
        final VoiceChannel voiceChannel = step(true, "Get VoiceChannel", c -> {
            if (c.getInstigator().getVoiceState().inVoiceChannel()) return c.getInstigator().getVoiceState().getChannel();
            else throw new Exception("Vous devez être dans un salon vocal pour lancer ce patch!");
        });
        final GuildChannel guildChannel = step(true, "Get GuildChannel", c -> c.getGuild().getGuildChannelById(voiceChannel.getId()));
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
            guildChannel.putPermissionOverride(roleR).setDeny(Permission.ALL_PERMISSIONS).setAllow(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_MOVE_OTHERS, Permission.VOICE_USE_VAD, Permission.VOICE_STREAM).complete();
            return null;
        });
        step(false, "+ M PermissionOverride", context -> {
            guildChannel.putPermissionOverride(roleM).setDeny(Permission.CREATE_INSTANT_INVITE, Permission.MANAGE_CHANNEL, Permission.MANAGE_PERMISSIONS, Permission.MANAGE_WEBHOOKS).setAllow(Permission.VOICE_MUTE_OTHERS).complete();
            return null;
        });
    }
}
