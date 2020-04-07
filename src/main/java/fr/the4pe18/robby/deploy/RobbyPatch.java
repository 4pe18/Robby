package fr.the4pe18.robby.deploy;

import fr.the4pe18.robby.deploy.exceptions.PatchAlreadyLoadedException;
import fr.the4pe18.robby.deploy.exceptions.PatchNotLoadedException;
import fr.the4pe18.robby.deploy.exceptions.PatchThreadNotDeploymentThreadException;

import java.util.ArrayList;
import java.util.List;

/**
 * @auhor 4PE18
 */
public abstract class RobbyPatch {
    private final String patchName;

    private boolean loaded;
    private Integer stepSize;
    private List<PatchStepInfo> stepInfos;

    public RobbyPatch(String patchName) {
        this.patchName = patchName;
        this.loaded = false;
    }

    protected abstract void patch();

    void load() throws PatchAlreadyLoadedException {
        if (this.isLoaded()) throw new PatchAlreadyLoadedException(this.getPatchName());
        this.stepSize = 0;
        this.stepInfos = new ArrayList<>();
        patch();
        this.loaded = true;
    }

    void deploy() throws PatchNotLoadedException, PatchThreadNotDeploymentThreadException {
        if (!this.isLoaded()) throw new PatchNotLoadedException(this.getPatchName());
        if (!(Thread.currentThread() instanceof DeploymentThread)) throw new PatchThreadNotDeploymentThreadException(this.getPatchName());
        patch();
    }

    public <T> T step(Boolean critical, String name, PatchStepWorker<T> worker) {
        if (!this.isLoaded()) this.getStepInfos().add(new PatchStepInfo(stepSize++, critical, name));
        else {
            DeploymentThread deploymentThread = (DeploymentThread) Thread.currentThread();
            deploymentThread.updateCurrentStepStatus(PatchStepStatus.RUNNING);
            Exception exception = null;
            try {
                T obj = worker.work(deploymentThread.getContext());
                deploymentThread.updateCurrentStepStatus(PatchStepStatus.SUCCESS);
                deploymentThread.endStep(null);
                return obj;
            } catch (Exception e) {
                if (critical) deploymentThread.updateCurrentStepStatus(PatchStepStatus.FATAL_ERROR);
                else deploymentThread.updateCurrentStepStatus(PatchStepStatus.ERROR);
                deploymentThread.endStep(e);
            }
        }
        return null;
    }

    public String getPatchName() {
        return patchName;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public List<PatchStepInfo> getStepInfos() {
        return stepInfos;
    }
}
