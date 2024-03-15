package controllers.Commands;

import models.Collection;
import models.MusicBand;
import views.ScriptMode;

import java.util.PriorityQueue;

public class ClearCommmand extends AbstractCommand {
    public ClearCommmand() {
        super("clear", "очищает коллекцию", 1);
    }


    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            Collection collection = request.oldCollection();
            if (collection != null) {
                PriorityQueue<MusicBand> musicBandPriorityQueue = collection.getMusicBandPriorityQueue();
                if (musicBandPriorityQueue != null) {
                    musicBandPriorityQueue.clear();
                    return new Response<>("     Все банды были удалены", collection);
                } else {
                    return new Response<>(false, "     Незя удалить пустоту", collection);
                }
            } else {
                return new Response<>(false, "     Незя удалить пустоту", collection);
            }
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
