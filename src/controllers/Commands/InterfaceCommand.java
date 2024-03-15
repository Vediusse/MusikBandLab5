package controllers.Commands;

import views.ScriptMode;

public interface InterfaceCommand {
    String getDescription();

    String getName();

    AbstractCommand.Response<String> execute(ScriptMode.Request request);
}
