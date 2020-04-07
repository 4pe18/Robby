package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchNotLoadedException extends PatchException {
    public PatchNotLoadedException(String patchName) {
        super(patchName, "The patch isn't loaded yet!");
    }
}
