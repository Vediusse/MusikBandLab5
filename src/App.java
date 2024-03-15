import controllers.CommandHandlers;
import controllers.Commands.*;
import models.Collection;
import views.ScriptMode;

public class App {
    public static void main(String[] args) {
        Collection collectionView = new Collection();
        collectionView.initializeCollection();
        CommandHandlers commandView = new CommandHandlers(
                new HelpCommand(),
                new FilterStartsWithName(),
                new CountGreaterThanNumberOfParticipantsCommmand(),
                new InfoCommand(),
                new PrintUniqueEstablishmentDate(),
                new RemoveByIdCommmand(),
                new RemoveGreaterCommmand(),
                new RemoveHeadCommmand(),
                new RemoveLowerCommmand(),
                new UpdateIdCommmand(),
                new AddCommmand(),
                new ExecuteScriptCommmand(),
                new SaveCommmand(),
                new ClearCommmand(),
                new ExitCommmand(),
                new ShowCommand()
        );
        ScriptMode scriptMode = new ScriptMode(commandView, collectionView);
        scriptMode.view();

    }

}