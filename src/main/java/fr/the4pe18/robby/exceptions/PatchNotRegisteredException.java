package fr.the4pe18.robby.exceptions;

/**
 * @author 4PE18
 */
public class PatchNotRegisteredException extends PatchException {
    public PatchNotRegisteredException(String patchName) {
        super(patchName, "The patch is not registered!");
    }
}
