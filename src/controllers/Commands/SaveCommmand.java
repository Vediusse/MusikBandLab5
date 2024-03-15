package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class SaveCommmand extends AbstractCommand {
    public SaveCommmand() {
        super("save", "сохраняет коллекцию в файл", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            request.oldCollection().save();
            return new Response<>(true, "Collection saved successfully", request.oldCollection());
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
