package fr.the4pe18.Robby.events;

import fr.the4pe18.Robby.LangUtils;
import fr.the4pe18.Robby.Robby;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SpecialApril extends ListenerAdapter {
    private Robby instance;
    Random random = new Random();
    private HashMap<String, Boolean> enabledChannels = new HashMap<>();
    private HashMap<String, List<String>> pointsGranted = new HashMap<>();

    private String fishName = "\uD83D\uDC1F";
    private String tropicalFishName = "\uD83D\uDC20";
    private String blowfishName = "\uD83D\uDC21";
    private String superfishName = "superfish";
    private String superfishId = "694689673435676782";

    public SpecialApril(Robby instance) {
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!enabledChannels.containsKey(event.getChannel().getId())) enabledChannels.put(event.getChannel().getId(), true);
        if (!enabledChannels.get(event.getChannel().getId())) return;
        int v = random.nextInt(100);
        if (v < 25) {
            event.getMessage().addReaction(fishName).complete();
            addScore(event.getMessage().getAuthor().getId(), 1);
        }
        v = random.nextInt(100);
        if (v < 10) {
            event.getMessage().addReaction(tropicalFishName).complete();
            addScore(event.getMessage().getAuthor().getId(), 5);
        }
        v = random.nextInt(100);
        if (v < 5) {
            event.getMessage().addReaction(blowfishName).complete();
            addScore(event.getMessage().getAuthor().getId(), 8);
        }
        v = random.nextInt(100);
        if (v < 1) {
            event.getMessage().addReaction(superfishName + ":" + superfishId).complete();
            addScore(event.getMessage().getAuthor().getId(), 15);
        }
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        assert event.getMember() != null;
        if (!enabledChannels.containsKey(event.getChannel().getId())) enabledChannels.put(event.getChannel().getId(), true);
        if (Objects.requireNonNull(event.getUser()).isBot()) return;
        if (!enabledChannels.get(event.getChannel().getId())) return;
        String reactionName = event.getReactionEmote().getName();
        if (reactionName.equalsIgnoreCase(fishName) || reactionName.equalsIgnoreCase(tropicalFishName) || reactionName.equalsIgnoreCase(blowfishName) || reactionName.equalsIgnoreCase(superfishName)) {
            Message message = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
            if (!event.getReaction().retrieveUsers().complete().contains(instance.getJdaInstance().getSelfUser())) {
                message.removeReaction(reactionName, Objects.requireNonNull(event.getUser())).complete();
            } else {
                String identifier = event.getMessageId() + "-" + event.getReaction().getReactionEmote().getName();
                if (!pointsGranted.containsKey(identifier)) pointsGranted.put(identifier, new ArrayList<>());
                if (!pointsGranted.get(identifier).contains(event.getMember().getId())) {
                    pointsGranted.get(identifier).add(event.getMember().getId());
                    addScore(message.getAuthor().getId(), 1);
                }
            }
        }
    }

    public Integer getScore(String member) {
        try {
            Statement statement = getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `points` FROM `april` WHERE `id`=" + member);
            int result = 0;
            while (resultSet.next()) result = resultSet.getInt("points");
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean switchChannel(String channel) {
        if (!enabledChannels.containsKey(channel)) enabledChannels.put(channel, true);
        enabledChannels.replace(channel, !enabledChannels.get(channel));
        return enabledChannels.get(channel);
    }

    public void addScore(String member, Integer score) {
        try {
            Statement statement = getInstance().getConnection().createStatement();
            statement.executeUpdate("INSERT INTO `april`(`id`, `points`) VALUES (" + member + "," + score+ ") ON DUPLICATE KEY UPDATE `points`=`points`+" + score);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
