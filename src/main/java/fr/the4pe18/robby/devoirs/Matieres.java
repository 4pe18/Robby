package fr.the4pe18.robby.devoirs;

/**
 * @author 4PE18
 */
public enum Matieres {
    FR("Français", ":pen_fountain:", "U+1f58bU+fe0f"),
    HG("Histoire/Géographie", ":map:", "U+1f5faU+fe0f"),
    EN("Anglais", ":flag_gb:", "U+1f1ecU+1f1e7"),
    ES_SVT("Enseignement Scientifique (SVT)", ":microbe:", "U+1f9a0"),
    ES_PC("Enseignement Scientifique (Physique-Chimie)", ":comet:", "U+2604U+fe0f"),
    ES_MATH("Enseignement Scientifique (Physique-Chimie)", ":comet:", "U+2604U+fe0f");

    private final String name;
    private final String emoteString;
    private final String codePoints;

    Matieres(String name, String emoteString, String Codepoints) {
        this.name = name;
        this.emoteString = emoteString;
        this.codePoints = Codepoints;
    }

    public String getName() {
        return name;
    }

    public String getEmoteString() {
        return emoteString;
    }

    public String getCodePoints() {
        return codePoints;
    }
}
