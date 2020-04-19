package fr.the4pe18.robby.events;

import fr.the4pe18.robby.Robby;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author 4PE18
 */
public class SpecialBlindTest extends ListenerAdapter {
    private final Robby instance;

    public SpecialBlindTest(Robby instance) {
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    public Integer getScore(String member) {
        try {
            Statement statement = getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `points` FROM `blindtest` WHERE `id`=" + member);
            int result = 0;
            while (resultSet.next()) result = resultSet.getInt("points");
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addScore(String member, Integer score) {
        try {
            Statement statement = getInstance().getConnection().createStatement();
            statement.executeUpdate("INSERT INTO `blindtest`(`id`, `points`) VALUES (" + member + "," + score+ ") ON DUPLICATE KEY UPDATE `points`=`points`+" + score);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
