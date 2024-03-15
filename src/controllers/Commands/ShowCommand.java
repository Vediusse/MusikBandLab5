package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class ShowCommand extends AbstractCommand {
    public ShowCommand() {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            Collection collection = request.oldCollection();
            if (collection != null) {
                String collectionContent = collection.show();
                if (!collectionContent.isEmpty()) {
                    return new Response<>(collectionContent, collection);
                } else {
                    return new Response<>(false, "The collection is empty", collection);
                }
            } else {
                return new Response<>(false, "Old collection is null", null);
            }
        }
        return new Response<>(false, "Required " + args + " position argument", request.oldCollection());
    }
}
