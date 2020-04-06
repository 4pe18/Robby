package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchAlreadyLoadedException extends DeployException {

    public PatchAlreadyLoadedException(String patchName) {
        super("The patch/deploy " + patchName + " is already loaded yet!");
    }
}
