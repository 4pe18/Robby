package fr.the4pe18.robby.exceptions;

/**
 * @author 4PE18
 */
public class PatchAlreadyRegisteredException extends PatchException {
    public PatchAlreadyRegisteredException(String patchName) {
        super(patchName, "The patch is already registered!");
    }
}
