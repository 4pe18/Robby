package fr.the4pe18.robby.deploy.old;

import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.deploy.exceptions.PatchAlreadyExistException;
import fr.the4pe18.robby.deploy.old.RobbyDeploy;

import java.util.HashMap;

/**
 * @author 4PE18
 */
public class PatchManager {

    private HashMap<String, RobbyDeploy> patches = new HashMap<>();
    private Robby instance;

    public PatchManager(Robby instance) {
        this.instance = instance;
    }

    public void addPatch(RobbyDeploy patch) throws PatchAlreadyExistException {
        if (patches.containsKey(patch.getPatchName())) throw new PatchAlreadyExistException(patch.getPatchName());
        patches.put(patch.getPatchName(), patch);
    }

}
