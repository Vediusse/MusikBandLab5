package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class RemoveByIdCommmand extends AbstractCommand {
    public RemoveByIdCommmand() {
        super("remove_by_id", "удаляет элемент из коллекции по его id", 2);
    }


    /**
     * @param command String with name of command and params
     * @return the result of delete elements by id  .Implementation in CommandHandlers
     */
    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {

            return new Response<>(request.oldCollection().removeById(Integer.parseInt(request.command().split(" ")[1])), request.oldCollection());
        }

        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
