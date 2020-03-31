package fr.the4pe18.Robby;

import fr.the4pe18.Robby.audit.AuditsManager;
import fr.the4pe18.Robby.commands.*;
import fr.the4pe18.Robby.events.ChatListener;
import fr.the4pe18.Robby.events.ReactionListener;
import fr.the4pe18.Robby.events.VocalListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;

import javax.security.auth.login.LoginException;

public class Robby {
    private CommandManager commandManager;
    private AuditsManager auditsManager;

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void start(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder("Njg4MzkxOTA1NDI3NDU2MDI3.XmzsUg.T8AXlnunFIAo5oTHXLFSbE3AAlQ");
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.listening("..."));
        this.commandManager = new CommandManager(this);
        this.auditsManager = new AuditsManager();
        getCommandManager().addCommand(new WarningCommand());
        getCommandManager().addCommand(new DebugCommand());
        getCommandManager().addCommand(new DeployCommand());
        getCommandManager().addCommand(new DevoirsCommand());
        getCommandManager().addCommand(new AuditCommand(getAuditsManager()));
        builder.addEventListeners(new ChatListener(this));
        builder.addEventListeners(new ReactionListener(this));
        builder.addEventListeners(new VocalListener(this));
        builder.setAutoReconnect(true);
        JDA jda = builder.build();
        System.out.println("Robby by 4PE18 is running.");
    }

    public static void main(String[] args) throws LoginException {
        (new Robby()).start(args);
    }

    public AuditsManager getAuditsManager() {
        return auditsManager;
    }
}
