package fr.the4pe18.robby.deploy.old;

import fr.the4pe18.robby.deploy.exceptions.DeployException;
import fr.the4pe18.robby.deploy.exceptions.PatchAlreadyLoadedException;
import fr.the4pe18.robby.deploy.exceptions.PatchFailedToLoadException;
import fr.the4pe18.robby.deploy.exceptions.PatchNotLoadedException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;

/**
 * Représente un patch deployable par Robby
 *
 * @author 4PE18
 */
public abstract class RobbyDeploy {
    private final String patchName;

    private Guild guild;
    private MessageChannel channel;
    private Member sender;
    private String[] args;
    private Boolean loaded;

    private Integer stepSize;
    private HashMap<Integer, String> steps;
    private HashMap<Integer, StepStatus> stepStatuses;
    private Integer runningStep;
    private Message lastMessageStatus;

    /**
     * Instancie un patch avec le nom donné
     *
     * @param patchName le nom du patch
     */
    public RobbyDeploy(String patchName) {
        this.patchName = patchName;
        this.loaded = false;
        this.stepSize = 0;
        this.runningStep = 0;
        this.stepStatuses = null;
        this.lastMessageStatus = null;
    }

    /**
     * Méthode abstraite consitutée d'instructions step, qui contient
     * le "coeur" du patch
     *
     * @throws DeployException
     */
    protected abstract void patch() throws DeployException;

    /**
     * Permet d'appeler une étape du patch
     *
     * @param critical si l'étape oui ou non est critique. Pour une étape critique, une erreur entrainera l'arrêt du déploiement.
     * @param name le nom de l'étape
     * @param stepCore un objet DeployStep<T> qui met en oeuvre l'étape
     * @param <T> la classe traitée par l'étape en question
     * @return un objet <T> retournée par l'étape
     * @throws DeployException
     */
    public <T> T step(boolean critical, String name, StepCore<T> stepCore) throws DeployException {
        if (!this.isLoaded()) this.getSteps().put(this.stepSize++, name);
        /**
        else {
            try {
                return stepCore.work(getGuild(), getChannel(), getSender(), getArgs());
            } catch (Exception e) {
                channel.sendMessage(DeployEmbed.STEP_ERROR.build(RobbyUtils.getExceptionTrace(e))).complete();
                if (critical) throw new DeployException();
            }
        }**/
        return null;
    }

    public void updateStatus() {

    }

    public void load() throws PatchAlreadyLoadedException, PatchFailedToLoadException {
        if (this.isLoaded()) throw new PatchAlreadyLoadedException(this.getPatchName());
        try {
            patch();
        } catch (DeployException e) {
            throw new PatchFailedToLoadException(this.getPatchName(), e);
        }
        this.loaded = true;
    }


    public void deploy(Guild guild, MessageChannel channel, Member sender, String[] args) throws PatchNotLoadedException {
        if (!this.isLoaded()) throw new PatchNotLoadedException(this.getPatchName());

        this.guild = guild;
        this.channel = channel;
        this.sender = sender;
        this.args = args;
        //channel.sendMessage(DeployEmbed.DEPLOY_STARTING.build(patchName)).complete();

        this.stepStatuses = new HashMap<>();
        getSteps().keySet().forEach(i -> getStepStatuses().put(i, StepStatus.PENDING));

        try {
            patch();
        } catch (DeployException e) {
            //channel.sendMessage(DeployEmbed.DEPLOY_FAILED.build(patchName, RobbyUtils.getExceptionTrace(e))).complete();
            return;
        }
        //channel.sendMessage(DeployEmbed.DEPLOY_SUCCESS.build(patchName)).complete();
    }

    public String getPatchName() {
        return patchName;
    }

    public Guild getGuild() {
        return guild;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Member getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }

    public Boolean isLoaded() {
        return loaded;
    }

    public Integer getStepSize() throws PatchNotLoadedException {
        if (this.isLoaded()) return stepSize;
        throw new PatchNotLoadedException(this.getPatchName());
    }

    public HashMap<Integer, String> getSteps() {
        return steps;
    }

    public HashMap<Integer, StepStatus> getStepStatuses() {
        return stepStatuses;
    }

    public Integer getRunningStep() {
        return runningStep;
    }

    public Message getLastMessageStatus() {
        return lastMessageStatus;
    }
}
