package fr.the4pe18.Robby.commands;

import fr.the4pe18.Robby.Robby;
import fr.the4pe18.Robby.commands.AbstractCommand;

import java.util.HashMap;
import java.util.List;

public class CommandManager {
    private HashMap<String, AbstractCommand> commands;
    private Robby instance;

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
