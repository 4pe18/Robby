package fr.the4pe18.robby.old.deploy.exceptions;

/**
 * @author 4PE18
 * @deprecated
 */
public class OldPatchAlreadyLoadedException extends OldDeployException {

    public OldPatchAlreadyLoadedException(String patchName) {
        super("The patch/deploy " + patchName + " is already loaded yet!");
    }
}
