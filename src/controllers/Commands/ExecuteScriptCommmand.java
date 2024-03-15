package controllers.Commands;

import controllers.utils.ScriptParser;
import models.Collection;
import views.ScriptMode;

import java.util.ArrayList;
import java.util.List;

public class ExecuteScriptCommmand extends AbstractCommand {
    public ExecuteScriptCommmand() {
        super("execute_script", "считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в скрипт режиме.", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            // Проверка на null перед вызовом add()
            String scriptFilePath = System.getenv("SCRIPT_FILE");

            if (scriptFilePath == null) {
                return null;
            }
            List<String> listCommands = ScriptParser.parseScript(scriptFilePath);

            if (listCommands.isEmpty()) {
                return null;
            }
            List<Collection> tempResponse = new ArrayList<>();
            tempResponse.add(request.oldCollection());

            for (String command : listCommands) {
                ScriptMode.Request commandRequest = new ScriptMode.Request(command, tempResponse.get(tempResponse.size() - 1));
                InterfaceCommand cmd = commandMap.get(commandRequest.command().split(" ")[0].strip());

                tempResponse.add(cmd.execute(commandRequest).musicBands());

            }

            return new Response<>("Execute done", request.oldCollection());
        }
        return new Response<>(false, "Required " + args + " position argument", request.oldCollection());
    }
}
