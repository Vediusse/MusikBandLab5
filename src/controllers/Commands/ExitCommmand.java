package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class ExitCommmand extends AbstractCommand {
    public ExitCommmand() {
        super("exit", "завершает программу (без сохранения в файл)", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), 1)) {
            System.exit(0);
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
