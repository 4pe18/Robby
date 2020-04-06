package fr.the4pe18.robby.deploy.exceptions;

/**
 * @author 4PE18
 */
public class DeployException extends Exception {

    public DeployException() {
        super("RobbyDeployException");
    }

    public DeployException(String exception) {
        super(exception);
    }
}
