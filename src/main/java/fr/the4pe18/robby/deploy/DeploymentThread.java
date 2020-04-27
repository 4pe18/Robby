package fr.the4pe18.robby.deploy;

import fr.the4pe18.robby.RobbyEmbed;
import fr.the4pe18.robby.RobbyUtils;
import fr.the4pe18.robby.exceptions.PatchNotLoadedException;
import fr.the4pe18.robby.exceptions.PatchThreadNotDeploymentThreadException;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 4PE18
 */
public class DeploymentThread extends Thread {
    private RobbyPatch patch;
    private Integer currentStep;
    private Map<Integer, PatchStepStatus> stepStatuses;
    private Map<Integer, String> updateTimes;
    private String startTime;
    private DeploymentContext context;
    private Message message;
    private DeploymentStatus status;


    public DeploymentThread(RobbyPatch robbyPatch) {
        this.patch = robbyPatch;
        this.currentStep = 0;
        this.stepStatuses = new HashMap<>();
    }

    synchronized void begin(DeploymentContext context) throws PatchNotLoadedException {
        if (!this.getPatch().isLoaded()) throw new PatchNotLoadedException(this.getPatch().getPatchName());
        this.context = context;
        this.status = DeploymentStatus.PREPARING;
        RobbyEmbed embed = new RobbyEmbed("__**DÉPLOIEMENT PATCH**___", "Chargement...").setColor(Color.yellow);
        embed.setThumbnail("https://i.ibb.co/ZYGM6dw/617c1718e07ad475bacecb7e5e401dd3.png").setAuthor(this.getContext().getInstigator().getNickname(), this.getContext().getInstigator().getUser().getAvatarUrl());
        this.message = this.getContext().getChannel().sendMessage(embed.build()).complete();
        this.start();
    }

    @Override
    public void run() {
        super.run();
        this.currentStep = 0;
        this.stepStatuses = new HashMap<>();
        this.updateTimes = new HashMap<>();
        this.startTime = RobbyUtils.getInstant();
        this.getPatch().getStepInfos().forEach(patchStepInfo -> this.getStepStatuses().put(patchStepInfo.getStepPosition(), PatchStepStatus.PENDING));
        this.getPatch().getStepInfos().forEach(patchStepInfo -> this.getUpdateTimes().put(patchStepInfo.getStepPosition(), this.getStartTime()));
        this.status = DeploymentStatus.RUNNING;
        updateMessage();
        try {
            this.getPatch().deploy();
        } catch (PatchNotLoadedException | PatchThreadNotDeploymentThreadException ignored) {}
    }

    public void updateCurrentStepStatus(PatchStepStatus status) {
        if (status != null) this.updateStep(this.getCurrentStep(), status);
        updateMessage();
    }

    private void updateStep(Integer step, PatchStepStatus status) {
        this.getStepStatuses().replace(step, status);
        this.getUpdateTimes().replace(step, RobbyUtils.getInstant());
    }

    public void updateMessage() {
        RobbyEmbed embed = new RobbyEmbed("__**DÉPLOIEMENT PATCH**__", null).setColor(Color.CYAN);
        embed.setThumbnail("https://i.ibb.co/ZYGM6dw/617c1718e07ad475bacecb7e5e401dd3.png").setAuthor(this.getContext().getInstigator().getNickname(), this.getContext().getInstigator().getUser().getAvatarUrl());
        embed.addField("Instigateur", this.getContext().getInstigator().getNickname(), true);
        embed.addField("Salon", this.getContext().getChannel().getName(), true);
        embed.addField("Patch", this.getPatch().getPatchName(), true);
        embed.addField("Début le/à", this.getStartTime(), true);
        embed.addField("Statut", this.getStatus().getEmoji() + " " + this.getStatus().getStatusText(), true);
        embed.addBlankField(false);
        StringBuilder etapes = new StringBuilder();
        this.getStepStatuses().forEach((s, patchStepStatus) -> {
            PatchStepInfo stepInfo = getPatch().getStepInfos().get(s);
            etapes.append(patchStepStatus.getEmoji());
            etapes.append(" `").append(stepInfo.getStepPosition()).append("` : ");
            etapes.append(stepInfo.isCritical() ? "**" : "").append(stepInfo.getStepName()).append(stepInfo.isCritical() ? "**" : "");
            etapes.append(" *_[").append(patchStepStatus.getStatusText()).append("]_*\n");
        });
        embed.addField("Étapes", etapes.toString(), true);
        StringBuilder updates = new StringBuilder();
        this.getUpdateTimes().forEach((integer, s) -> updates.append(" `").append(s).append("`\n"));
        embed.addField("Dernière opération le/à", updates.toString(), true);
        embed.addBlankField(false);
        embed.addField("Thread id", String.valueOf(Thread.currentThread().getId()), true);
        embed.addField("Dernière update", RobbyUtils.getInstant(), true);
        this.getMessage().editMessage(embed.build()).complete();
    }

    public void endStep(Exception exception) {
        if (exception != null && !this.getPatch().getPatchName().equals("ModoRespPerms")) {
            RobbyEmbed embed = new RobbyEmbed("__**DÉPLOIEMENT PATCH**__", "Erreur lors de l'étape").setColor(this.getStepStatuses().get(this.getCurrentStep()) == PatchStepStatus.FATAL_ERROR ? Color.RED : Color.ORANGE);
            embed.setThumbnail(this.getStepStatuses().get(this.getCurrentStep()) == PatchStepStatus.FATAL_ERROR ? "https://i.ibb.co/hfcCRwm/8becd37ab9d13cdfe37c08c496a9def3.png" : "https://i.ibb.co/vwbSWth/289673858e06dfa2e0e3a7ee610c3a30.png");
            embed.setAuthor(this.getContext().getInstigator().getNickname(), this.getContext().getInstigator().getUser().getAvatarUrl());
            embed.addField("Message", exception.getMessage(), true);
            embed.addField("Class", exception.getClass().getName(), true);
            embed.addField("Erreur", RobbyUtils.getExceptionTrace(exception), false);
            this.getContext().getChannel().sendMessage(embed.build()).complete();
            if (this.getStepStatuses().get(this.getCurrentStep()) == PatchStepStatus.FATAL_ERROR) exception.printStackTrace();
        }
        if (this.getStepStatuses().get(this.getCurrentStep()) == PatchStepStatus.FATAL_ERROR) {
            this.status = DeploymentStatus.INTERRUPTED;
            this.getStepStatuses().forEach((i, p) -> {
                if (i > getCurrentStep()) this.updateStep(i, PatchStepStatus.CANCELLED);
            });
            this.updateMessage();
        }
        this.currentStep++;
        if (currentStep >= this.getPatch().getStepInfos().size() && this.getStatus() == DeploymentStatus.RUNNING) {
            this.status = DeploymentStatus.SUCCEEDED;
            this.updateMessage();
        }
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

    public Map<Integer, String> getUpdateTimes() {
        return updateTimes;
    }

    public DeploymentContext getContext() {
        return context;
    }

    public String getStartTime() {
        return startTime;
    }

    public Message getMessage() {
        return message;
    }

    public DeploymentStatus getStatus() {
        return status;
    }
}
