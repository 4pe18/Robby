package fr.the4pe18.robby.deploy;

/**
 * @author 4PE18
 */
public enum PatchStepStatus {
    SUCCESS(":white_check_mark:", "SUCCESS"),
    ERROR(":warning:", "WARN ERROR"),
    FATAL_ERROR(":x:", "FATAL ERROR"),
    RUNNING(":arrows_counterclockwise:", "RUNNING"),
    PENDING(":clock3:", "PENDING"),
    CANCELLED(":grey_exclamation:", "CANCELLED");


    private String emoji;
    private String statusText;

    PatchStepStatus(String emoji, String statusText) {
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
