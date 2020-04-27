package fr.the4pe18.robby.exceptions;

/**
 * @author 4PE18
 */
public class PatchIgnoredException extends PatchDeploymentException {
    public PatchIgnoredException(Exception e) {
        super("", e);
    }
}
