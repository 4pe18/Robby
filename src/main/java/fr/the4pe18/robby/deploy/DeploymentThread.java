package fr.the4pe18.robby.deploy;

import fr.the4pe18.robby.deploy.exceptions.PatchNotLoadedException;
import fr.the4pe18.robby.deploy.exceptions.PatchThreadNotDeploymentThreadException;
import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author 4PE18
 */
public class DeploymentThread extends Thread {
    private RobbyPatch patch;
    private Integer currentStep;
    private Map<Integer, PatchStepStatus> stepStatuses;
    private DeploymentContext context;
    private Message message;

    public DeploymentThread(RobbyPatch robbyPatch) {
        this.patch = robbyPatch;
        this.currentStep = 0;
        this.stepStatuses = new HashMap<>();
    }

    synchronized void begin(DeploymentContext context) throws PatchNotLoadedException {
        if (!this.getPatch().isLoaded()) throw new PatchNotLoadedException(this.getPatch().getPatchName());
        this.context = context;
        this.message = this.getContext().getChannel().sendMessage("Preparing patch...").complete();
        this.start();
    }

    @Override
    public void run() {
        super.run();
        this.currentStep = 0;
        this.stepStatuses = new HashMap<>();
        this.getPatch().getStepInfos().forEach(patchStepInfo -> this.getStepStatuses().put(patchStepInfo.getStepPosition(), PatchStepStatus.PENDING));
        try {
            this.getPatch().deploy();
        } catch (PatchNotLoadedException | PatchThreadNotDeploymentThreadException ignored) {}
    }

    public void updateCurrentStepStatus(PatchStepStatus status) {
        this.getStepStatuses().replace(this.getCurrentStep(), status);
        StringBuilder builder = new StringBuilder("Déploiement: (en dev)\n");
        this.getStepStatuses().forEach((s, patchStepStatus) -> {
            PatchStepInfo stepInfo = getPatch().getStepInfos().get(s);
            builder.append(patchStepStatus.getEmoji());
            builder.append(" `").append(stepInfo.getStepPosition()).append("` : ");
            builder.append(stepInfo.isCritical() ? "**" : "").append(stepInfo.getStepName()).append(stepInfo.isCritical() ? "**" : "");
            builder.append(" *_[").append(patchStepStatus.getStatusText()).append("]_*\n");
        });
        builder.append("#Thread: ").append(Thread.currentThread().getId());
        this.getMessage().editMessage(builder.toString()).complete();
    }

    public void endStep(Exception exception) {
        if (this.getStepStatuses().get(this.getCurrentStep()) == PatchStepStatus.FATAL_ERROR) exception.printStackTrace();
        this.currentStep++;
    }

    public RobbyPatch getPatch() {
        return patch;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public Map<Integer, PatchStepStatus> getStepStatuses() {
        return stepStatuses;
    }

    public DeploymentContext getContext() {
        return context;
    }

    public Message getMessage() {
        return message;
    }
}
