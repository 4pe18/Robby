package fr.the4pe18.robby.old.deploy.exceptions;

/**
 * @author 4PE18
 * @deprecated
 */
public class OldPatchAlreadyExistException extends OldDeployException {

    public OldPatchAlreadyExistException(String patchName) {
        super("The patch/deploy " + patchName + " already exist!");
    }
}
