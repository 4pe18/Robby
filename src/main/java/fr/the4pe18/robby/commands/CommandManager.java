package fr.the4pe18.robby.commands;

import fr.the4pe18.robby.Robby;

import java.util.HashMap;

/**
 * @author 4PE18
 */
public class CommandManager {
    private final HashMap<String, AbstractCommand> commands;
    private final Robby instance;

    public CommandManager(Robby instance) {
        this.commands = new HashMap<>();
        this.instance = instance;
    }

    public Robby getInstance() {
        return instance;
    }

    private HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }

    public AbstractCommand getCommand(String label) {
        if (!getCommands().containsKey(label)) return null;
        return getCommands().get(label);
    }

    public void addCommand(AbstractCommand command) {
        if (getCommand(command.getCommandLabel()) != null) return;
        getCommands().put(command.getCommandLabel(), command);
    }
}
