package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchAlreadyLoadedException extends PatchException {
    public PatchAlreadyLoadedException(String patchName) {
        super(patchName, "The patch is already loaded!");
    }
}
