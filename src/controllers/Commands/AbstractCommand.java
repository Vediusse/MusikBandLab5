package controllers.Commands;

import models.Collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractCommand implements InterfaceCommand {
    public static HashMap<String, InterfaceCommand> commandMap;
    public static Map<Integer, LinkedHashMap<String, String>> commandHistory;
    public final int args;
    private final String name;
    private final String description;
    private int uniqueId = 1;


    public AbstractCommand(String name, String description, int args) {
        this.name = name;
        this.description = description;
        this.args = args;
    }


    public String getName() {
        return name;
    }


    /**
     * It returns the description of the command.
     *
     * @return The description of the command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * It returns a string representation of the object.
     *
     * @return The name of the question and the description of the command.
     */
    @Override
    public String toString() {
        return name + " (" + description + ")";
    }


    public boolean toEnoughArguments(String commandName, int amountArguments) {
        String[] commandParts = commandName.split(" ");
        return commandParts.length == amountArguments;
    }


    public Map<Integer, LinkedHashMap<String, String>> getCommandHistory() {
        return this.commandHistory;
    }


    public void updateHistory(String command, String result) {
        LinkedHashMap<String, String> commandEntry = new LinkedHashMap<>();
        commandEntry.put(command, result);
        commandHistory.put(uniqueId, commandEntry);
        this.uniqueId++;
    }


    public record Response<T>(boolean success, String errorMessage, T result, Collection musicBands) {

        // Дополнительный конструктор для успешных ответов
        public Response(T result, Collection musicBands) {
            this(true, null, result, musicBands);
        }

        // Дополнительный конструктор для неуспешных ответов
        public Response(boolean success, String errorMessage, Collection musicBands) {
            this(success, errorMessage, null, musicBands);
        }
    }

}
