package controllers.Commands;

import models.Collection;
import models.Forms.MusicBandForm;
import models.MusicBand;
import views.ScriptMode;

public class UpdateIdCommmand extends AbstractCommand {
    public UpdateIdCommmand() {
        super("update", " обновляет значение элемента коллекции, id которого равен заданному", 2);
    }

    @Override
    public AbstractCommand.Response<String> execute(ScriptMode.Request request) {
        if (this.toEnoughArguments(request.command(), args)) {
            try {
                int idNumber = Integer.parseInt(request.command().split(" ")[1]);
                MusicBand musicBandById = request.oldCollection().getByIdOrNull(idNumber);
                if (musicBandById == null) {
                    return new Response<>(false, "     Not Found", request.oldCollection());
                }
                MusicBandForm form = new MusicBandForm();
                MusicBand updatedMusicBand = form.update(musicBandById);
                String removeBandName = musicBandById.getName();
                boolean removed = request.oldCollection().getMusicBandPriorityQueue().removeIf(band -> band.getId() == idNumber);
                if (removed) {
                    int newId = 1;
                    request.oldCollection().getMusicBandPriorityQueue().add(updatedMusicBand);
                    for (MusicBand band : request.oldCollection().getMusicBandPriorityQueue()) {
                        band.setId(newId++);
                    }
                    return new Response<>("     Старая банда " + removeBandName + " была успешно изменена", request.oldCollection());
                } else {
                    return new Response<>(false, "     Старая банда " + removeBandName + " не была найдена", request.oldCollection());
                }
            } catch (NumberFormatException e) {
                return new Response<>(false, "     Second Argument is not a number", request.oldCollection());
            }
        }
        return new Response<>(false, "Required " + request.length() + " position argument", request.oldCollection());
    }
}
