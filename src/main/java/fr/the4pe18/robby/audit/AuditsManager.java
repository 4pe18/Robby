package fr.the4pe18.robby.audit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.the4pe18.robby.Robby;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 4PE18
 */
public class AuditsManager {
    private final Map<Long, VocalAudit> vocalAudits;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(VocalAudit.class, new VocalAuditAdapter()).create();
    private final Robby instance;

    public AuditsManager(Robby instance) {
        this.vocalAudits = new HashMap<>();
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    public void addVocalAudit(VoiceChannel channel) {
        if (!vocalAudits.containsKey(channel.getIdLong())) vocalAudits.put(channel.getIdLong(), new VocalAudit(channel));
    }

    public void removeVocalAudit(VoiceChannel channel) {
        vocalAudits.remove(channel.getIdLong());
    }

    public void userJoin(VoiceChannel channel, Member member) {
        if (vocalAudits.containsKey(channel.getIdLong())) vocalAudits.get(channel.getIdLong()).join(member);
    }

    public void userLeave(VoiceChannel channel, Member member) {
        if (vocalAudits.containsKey(channel.getIdLong())) vocalAudits.get(channel.getIdLong()).leave(member);
    }

    public void start(VoiceChannel channel) {
        if (vocalAudits.containsKey(channel.getIdLong())) vocalAudits.get(channel.getIdLong()).start();
    }

    public void stop(VoiceChannel channel) {
        if (vocalAudits.containsKey(channel.getIdLong())) vocalAudits.get(channel.getIdLong()).stop();
    }

    public String getJSON(VoiceChannel channel) {
        if (vocalAudits.containsKey(channel.getIdLong())) return gson.toJson(vocalAudits.get(channel.getIdLong()));
        return null;
    }
}
