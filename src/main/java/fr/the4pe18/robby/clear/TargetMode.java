package fr.the4pe18.robby.clear;

/**
 * @author 4PE18
 */
public enum TargetMode {
    SELECT_ONLY(""),
    SELECT("+"),
    EXCLUDE_ONLY("!"),
    EXCLUDE("!+");

    final String prefix;

    TargetMode(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    

}
