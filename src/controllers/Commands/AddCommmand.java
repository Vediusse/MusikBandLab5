package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class AddCommmand extends AbstractCommand {
    public AddCommmand() {
        super("add", "добавляет новый элемент в коллекци", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            // Проверка на null перед вызовом add()
            if (request.oldCollection() != null) {
                return new Response<>(request.oldCollection().add(), request.oldCollection());
            } else {
                return new Response<>(false, "Old collection is null", null);
            }
        }
        return new Response<>(false, "Required " + args + " position argument", request.oldCollection());
    }
}
