package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class RemoveGreaterCommmand extends AbstractCommand {
    public RemoveGreaterCommmand() {
        super("remove_greater", "удаляет из коллекции все элементы, превышающие заданный", 2);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            return new Response<>(request.oldCollection().removeGreater(Integer.parseInt(request.command().split(" ")[1])), request.oldCollection());
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
