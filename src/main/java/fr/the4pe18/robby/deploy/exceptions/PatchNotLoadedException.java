package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchNotLoadedException extends DeployException {

    public PatchNotLoadedException(String patchName) {
        super("The patch/deploy " + patchName + " isn't loaded yet!");
    }
}
