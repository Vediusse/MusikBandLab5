package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class CountGreaterThanNumberOfParticipantsCommmand extends AbstractCommand {
    public CountGreaterThanNumberOfParticipantsCommmand() {
        super("count_greater_than_number_of_participants", "выводит количество элементов, значение поля numberOfParticipants которых больше заданного", 2);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            Collection collection = request.oldCollection();
            if (collection != null) {
                int numberOfParticipants = Integer.parseInt(request.command().split(" ")[1]);
                String result = collection.countGreaterThanNumberOfParticipants(numberOfParticipants);
                return new Response<>(result, collection);
            } else {
                return new Response<>(false, "Old collection is null", null);
            }
        }
        return new Response<>(false, "Required " + args + " position argument", request.oldCollection());
    }

}
