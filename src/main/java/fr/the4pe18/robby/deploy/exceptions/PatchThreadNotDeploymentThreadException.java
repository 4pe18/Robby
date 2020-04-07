package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class PatchThreadNotDeploymentThreadException extends PatchException {
    public PatchThreadNotDeploymentThreadException(String patchName) {
        super(patchName, "The thread running the patch is not an instance of DeploymentThread!");
    }
}
