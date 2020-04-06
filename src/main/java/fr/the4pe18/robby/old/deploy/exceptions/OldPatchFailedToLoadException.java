package fr.the4pe18.robby.old.deploy.exceptions;

/**
 * @author 4PE18
 * @deprecated
 */
public class OldPatchFailedToLoadException extends OldDeployException {
    OldDeployException exception;

    public OldPatchFailedToLoadException(String patchName, OldDeployException exception) {
        super("The patch/deploy " + patchName + " failed to load!");
        this.exception = exception;
    }

    public OldDeployException getException() {
        return exception;
    }
}
