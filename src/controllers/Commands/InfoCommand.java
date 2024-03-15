package controllers.Commands;

import models.Collection;
import models.MusicBand;
import views.ScriptMode;

import java.text.SimpleDateFormat;
import java.util.PriorityQueue;

public class InfoCommand extends AbstractCommand {
    public InfoCommand() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            PriorityQueue<MusicBand> collectionQueue = request.oldCollection().getMusicBandPriorityQueue();

            if (collectionQueue.isEmpty()) {
                return new Response<>(false, "     Коллекция пуста.", request.oldCollection());
            }

            Class<?> queueClass = collectionQueue.getClass();
            String typeName = queueClass.getName();

            StringBuilder info = new StringBuilder();
            info.append("     Тип коллекции: ").append(typeName).append("\n");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String initDate = dateFormat.format(request.oldCollection().getInitializationDate());

            info.append("     Дата инициализации: ").append(initDate).append("\n");
            info.append("     Количество элементов: ").append(collectionQueue.size()).append("\n");

            for (MusicBand musicBand : collectionQueue) {
                info.append("     ").append("Элемент: ").append(musicBand).append("\n");
            }
            return new Response<>(info.toString(), request.oldCollection());
        }

        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
