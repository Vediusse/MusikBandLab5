package controllers;

import controllers.Commands.AbstractCommand;
import controllers.Commands.InterfaceCommand;
import controllers.utils.ScriptParser;
import views.ScriptMode;

import java.util.*;

import static controllers.utils.ScriptParser.parseScript;


/**
 * This class represents a command handler that manages commands, their execution, and maintains a history of executed commands and their results.
 */
public class CommandHandlers {
    private final HashMap<String, InterfaceCommand> commandMap;

    public CommandHandlers(InterfaceCommand... commands) {
        this.commandMap = new HashMap<>();
        for (InterfaceCommand command : commands) {
            commandMap.put(command.getName(), command);
        }
        AbstractCommand.commandMap = this.commandMap;
    }

    public AbstractCommand.Response<String> executeCommand(ScriptMode.Request request) {
        InterfaceCommand cmd = commandMap.get(request.command().split(" ")[0].strip());
        if (cmd == null) {
            return null;
        }
        return cmd.execute(request);
    }
}
