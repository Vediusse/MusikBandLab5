package controllers.Commands;

import models.Collection;
import views.ScriptMode;

public class PrintUniqueEstablishmentDate extends AbstractCommand {
    public PrintUniqueEstablishmentDate() {
        super("print_unique_establishment_date", "выводит уникальные значения поля establishmentDate всех элементов в коллекции", 1);
    }

    /**
     * @param command String with name of command and params
     * @return unique value establishmentDate all elements in collection   .Implementation in CommandHandlers
     */
    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            return new Response<>(request.oldCollection().printUniqueEstablishmentDate(), request.oldCollection());
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}