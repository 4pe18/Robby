package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchFailedToLoadException extends DeployException {
    DeployException exception;

    public PatchFailedToLoadException(String patchName, DeployException exception) {
        super("The patch/deploy " + patchName + " failed to load!");
        this.exception = exception;
    }

    public DeployException getException() {
        return exception;
    }
}
