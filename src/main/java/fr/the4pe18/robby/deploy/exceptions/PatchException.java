package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchException extends Exception {
    PatchException(String patchName, String exception) {
        super("An exception occurred with the patch " + patchName + ":\n" + exception);
    }
}
