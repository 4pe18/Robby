package fr.the4pe18.robby.old.deploy;

import fr.the4pe18.robby.Robby;
import fr.the4pe18.robby.old.deploy.exceptions.OldPatchAlreadyExistException;

import java.util.HashMap;

/**
 * @author 4PE18
 * @deprecated
 */
public class OldPatchManager {

    private HashMap<String, OldRobbyDeploy> patches = new HashMap<>();
    private Robby instance;

    public OldPatchManager(Robby instance) {
        this.instance = instance;
    }

    public void addPatch(OldRobbyDeploy patch) throws OldPatchAlreadyExistException {
        if (patches.containsKey(patch.getPatchName())) throw new OldPatchAlreadyExistException(patch.getPatchName());
        patches.put(patch.getPatchName(), patch);
    }

}
