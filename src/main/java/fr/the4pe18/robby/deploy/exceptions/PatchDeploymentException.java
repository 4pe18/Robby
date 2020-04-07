package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchDeploymentException extends PatchException {
    public PatchDeploymentException(String patchName, Exception e) {
        super(patchName, "Exception durring run: "+ e.getMessage());
    }
}
