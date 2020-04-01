package fr.the4pe18.Robby;

import fr.the4pe18.Robby.audit.AuditsManager;
import fr.the4pe18.Robby.commands.*;
import fr.the4pe18.Robby.events.ChatListener;
import fr.the4pe18.Robby.events.ReactionListener;
import fr.the4pe18.Robby.events.SpecialApril;
import fr.the4pe18.Robby.events.VocalListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;

import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Robby {
    private CommandManager commandManager;
    private AuditsManager auditsManager;
    private JDA jdaInstance;
    private Connection connection;

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void start(String[] args) throws LoginException, SQLException, ClassNotFoundException {
        if (args.length < 2) {
            System.err.println("Précissez le token du bot et le mot de passe mysql !");
            return;
        }

        JDABuilder builder = JDABuilder.createDefault(args[0]);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.listening("..."));

        this.auditsManager = new AuditsManager();
        this.commandManager = new CommandManager(this);
        SpecialApril specialApril = new SpecialApril(this);

        getCommandManager().addCommand(new DebugCommand());
        getCommandManager().addCommand(new DeployCommand());
        getCommandManager().addCommand(new DevoirsCommand());
        getCommandManager().addCommand(new AuditCommand(getAuditsManager()));
        getCommandManager().addCommand(new ClearCommand());
        getCommandManager().addCommand(new LearnCommand());
        getCommandManager().addCommand(new AprilCommand(specialApril));
        builder.addEventListeners(new ChatListener(this));
        builder.addEventListeners(new ReactionListener(this));
        builder.addEventListeners(new VocalListener(this));
        builder.addEventListeners(specialApril);
        builder.setAutoReconnect(true);
        jdaInstance = builder.build();
        openConnection(args[1]);
        System.out.println("Robby by 4PE18 is running.");

    }

    public void openConnection(String pass) throws SQLException, ClassNotFoundException {
        System.out.println("connecting to mysql database...");
        if (connection != null && !connection.isClosed()) return;
        synchronized (this) {
            if (connection != null && !connection.isClosed()) return;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robby", "root", pass);
            System.out.println("connected!");
        }
    }

    public static void main(String[] args) throws LoginException, SQLException, ClassNotFoundException {
        (new Robby()).start(args);
    }

    public AuditsManager getAuditsManager() {
        return auditsManager;
    }

    public JDA getJdaInstance() {
        return jdaInstance;
    }

    public Connection getConnection() {
        return connection;
    }
}
