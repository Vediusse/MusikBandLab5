package controllers.Commands;

import models.Collection;
import models.MusicBand;
import views.ScriptMode;

import java.util.*;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("help", "вывести справку по доступным командам", 1);
    }

    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        System.out.println();
        if (this.toEnoughArguments(request.command(), args)) {
            List<InterfaceCommand> sortedCommands = new ArrayList<>(commandMap.values());
            sortedCommands.sort(Comparator.comparing(InterfaceCommand::getName));
            StringJoiner result = new StringJoiner("\n");
            for (InterfaceCommand command : sortedCommands) {
                result.add("     " + command.getName() + " - " + command.getDescription());
            }
            return new Response<>(result.toString(), request.oldCollection());
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
