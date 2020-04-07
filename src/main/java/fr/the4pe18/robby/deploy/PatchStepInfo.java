package fr.the4pe18.robby.deploy;

/**
 * @author 4PE18
 */
public class PatchStepInfo {
    private Integer stepPosition;
    private Boolean critical;
    private String stepName;
    boolean locked;

    public PatchStepInfo(Integer stepPosition, Boolean critical, String stepName) {
        this.stepPosition = stepPosition;
        this.critical = critical;
        this.stepName = stepName;
        this.lock();
    }

    public PatchStepInfo() {
        this.stepPosition = null;
        this.critical = false;
        this.stepName = null;
        this.locked = false;
    }

    public Integer getStepPosition() {
        return stepPosition;
    }

    public Boolean isCritical() {
        return critical;
    }

    public String getStepName() {
        return stepName;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setStepPosition(Integer stepPosition) {
        if (!this.isLocked()) this.stepPosition = stepPosition;
    }

    public void setCritical(Boolean critical) {
        if (!this.isLocked()) this.critical = critical;
    }

    public void setStepName(String stepName) {
        if (!this.isLocked()) this.stepName = stepName;
    }

    public void lock() {
        this.locked = true;
    }
}
