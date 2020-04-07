package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchIgnoredException extends PatchDeploymentException {
    public PatchIgnoredException(Exception e) {
        super("", e);
    }
}
