package fr.the4pe18.robby.audit;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.*;

/**
 * @author 4PE18
 */
public class VocalAudit {
    static class VocalChange {
        private final boolean join;
        private final Long time;
        private final Member member;

        public VocalChange(boolean join, Long time, Member member) {
            this.join = join;
            this.time = time;
            this.member = member;
        }

        public boolean isJoin() {
            return this.join;
        }

        public Long getTime() {
            return time;
        }

        public Member getMember() {
            return this.member;
        }
    }

    private boolean started;
    private final VoiceChannel channel;
    private final Map<Long, Integer> membersCount;
    private final List<Member> activeMembers;
    private final List<VocalChange> vocalChanges;
    private Long startTime;
    private Long stopTime;

    public VocalAudit(VoiceChannel channel) {
        this.started = false;
        this.channel = channel;
        this.membersCount = new HashMap<>();
        this.activeMembers = new ArrayList<>();
        this.vocalChanges = new ArrayList<>();
        this.stopTime = 0L;
        this.stopTime = 0L;
    }

    public void start() {
        if (!started) {
            this.started = true;
            this.startTime = Calendar.getInstance().getTimeInMillis();
            channel.getMembers().forEach(this::join);
        }
    }

    public void stop() {
        if (started) {
            this.stopTime = Calendar.getInstance().getTimeInMillis();
            membersCount.put(stopTime, activeMembers.size());
            this.started = false;
        }
    }

    public void join(Member member) {
        if (started && !activeMembers.contains(member)) {
            activeMembers.add(member);
            Long time = Calendar.getInstance().getTimeInMillis();
            vocalChanges.add(new VocalChange(true, time, member));
            membersCount.put(time, activeMembers.size());
        }
    }

    public void leave(Member member) {
        if (started && activeMembers.contains(member)) {
            activeMembers.remove(member);
            Long time = Calendar.getInstance().getTimeInMillis();
            vocalChanges.add(new VocalChange(false, time, member));
            membersCount.put(time, activeMembers.size());
        }
    }

    public VoiceChannel getChannel() {
        return channel;
    }

    public Map<Long, Integer> getMembersCount() {
        return membersCount;
    }

    public List<VocalChange> getVocalChanges() {
        return vocalChanges;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }
}
