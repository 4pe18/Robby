package fr.the4pe18.robby.old.deploy.exceptions;

/**
 * @author 4PE18
 * @deprecated
 */
public class OldPatchNotLoadedException extends OldDeployException {

    public OldPatchNotLoadedException(String patchName) {
        super("The patch/deploy " + patchName + " isn't loaded yet!");
    }
}
