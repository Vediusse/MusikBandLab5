package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class FilterStartsWithName extends AbstractCommand {
    public FilterStartsWithName() {
        super("filter", "вывести справку по доступным командам", 2);
    }

    /**
     * @param command String with name of command and params
     * @return all elements with name, that starts with String in params  .Implementation in CommandHandlers
     */
    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}