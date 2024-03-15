package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class RemoveHeadCommmand extends AbstractCommand {
    public RemoveHeadCommmand() {
        super("remove_head", "выводит первый элемент коллекции и удаляет его", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), 1)) {
            return new Response<>(request.oldCollection().removeHead(), request.oldCollection());
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
