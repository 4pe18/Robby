package fr.the4pe18.robby.deploy;

/**
 * @auhor 4PE18
 */
public abstract class RobbyPatch {
    private final String patchName;

    public RobbyPatch(String patchName) {
        this.patchName = patchName;
    }

    protected abstract void patch();
}
