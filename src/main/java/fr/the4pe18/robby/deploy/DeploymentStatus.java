package fr.the4pe18.robby.deploy;

public enum DeploymentStatus {
    PREPARING(":pause_button:", "Péparation..."),
    RUNNING(":arrow_forward:", "En cours..."),
    INTERRUPTED(":no_entry:", "Interrompu!"),
    SUCCEEDED(":checkered_flag:", "Réussi!");

    private String emoji;
    private String statusText;

    DeploymentStatus(String emoji, String statusText) {
        this.emoji = emoji;
        this.statusText = statusText;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getStatusText() {
        return statusText;
    }
}
