package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class RemoveLowerCommmand extends AbstractCommand {
    public RemoveLowerCommmand() {
        super("remove_lower", "удаляет из коллекции все элементы, меньшие, чем заданный", 2);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            return new Response<>(request.oldCollection().removeLower(Integer.parseInt(request.command().split(" ")[1])), request.oldCollection());
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
