package fr.the4pe18.robby.old.deploy.exceptions;

/**
 * @author 4PE18
 * @deprecated
 */
public class OldDeployException extends Exception {

    public OldDeployException() {
        super("RobbyDeployException");
    }

    public OldDeployException(String exception) {
        super(exception);
    }
}
