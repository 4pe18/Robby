package fr.the4pe18.robby.deploy;

import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.exceptions.PatchAlreadyLoadedException;
import fr.the4pe18.robby.exceptions.PatchAlreadyRegisteredException;
import fr.the4pe18.robby.exceptions.PatchNotLoadedException;
import fr.the4pe18.robby.exceptions.PatchNotRegisteredException;
import fr.the4pe18.robby.deploy.patchs.DemoPatch;
import fr.the4pe18.robby.deploy.patchs.DemoPatch2;
import fr.the4pe18.robby.deploy.patchs.ModoRespPerms;
import fr.the4pe18.robby.deploy.patchs.ModoRespPermsVoc;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * author 4PE18
 */
public class DeploymentManager {
    private Robby instance;
    private Map<String, RobbyPatch> registeredPatches;

    public DeploymentManager(Robby instance) {
        this.instance = instance;
        this.registeredPatches = new HashMap<>();
    }

    public void registerDefaultPatches() throws PatchAlreadyRegisteredException, PatchNotRegisteredException, PatchAlreadyLoadedException {
        registerPatch(new DemoPatch(), true);
        registerPatch(new DemoPatch2(), true);
        registerPatch(new ModoRespPerms(), true);
        registerPatch(new ModoRespPermsVoc(), true);
    }

    public void registerPatch(RobbyPatch patch) throws PatchAlreadyRegisteredException {
        try {
            this.registerPatch(patch, false);
        } catch (PatchAlreadyLoadedException | PatchNotRegisteredException ignored) {}
    }

    public void registerPatch(RobbyPatch patch, boolean autoload) throws PatchAlreadyRegisteredException, PatchNotRegisteredException, PatchAlreadyLoadedException {
        if (this.getRegisteredPatches().containsKey(patch.getPatchName())) throw new PatchAlreadyRegisteredException(patch.getPatchName());
        this.getRegisteredPatches().put(patch.getPatchName(), patch);
        if (autoload) this.loadPatch(patch);
    }

    public void loadPatch(RobbyPatch patch) throws PatchNotRegisteredException, PatchAlreadyLoadedException {
        this.loadPatch(patch.getPatchName());
    }

    public void loadPatch(String patchName) throws PatchNotRegisteredException, PatchAlreadyLoadedException {
        if (!this.getRegisteredPatches().containsKey(patchName)) throw new PatchNotRegisteredException(patchName);
        this.getRegisteredPatches().get(patchName).load();
    }

    public void deploy(String patchName, Guild guild, MessageChannel channel, Member instigator, String[] args) throws PatchNotRegisteredException, PatchNotLoadedException {
        if (!this.getRegisteredPatches().containsKey(patchName)) throw new PatchNotRegisteredException(patchName);
        DeploymentThread thread = new DeploymentThread(this.getRegisteredPatches().get(patchName));
        thread.begin(new DeploymentContext(guild, channel, instigator, args));
    }

    public Map<String, RobbyPatch> getRegisteredPatches() {
        return this.registeredPatches;
    }
}
