package models;

import models.Forms.MusicBandForm;
import models.utils.CollectionToXml;
import models.utils.XmlParser;

import java.time.ZonedDateTime;
import java.util.*;

public class Collection {

    private final Date initializationDate;
    private final PriorityQueue<MusicBand> musicBandPriorityQueue;

    public Collection() {
        this.musicBandPriorityQueue = new PriorityQueue<>();
        this.initializationDate = new Date();
    }

    public void initializeCollection() {
        String xmlFilePath = System.getenv("XML_FILE_PATH");
        Map<Integer, MusicBand> collectionMap = XmlParser.parseXmlFile(xmlFilePath);
        musicBandPriorityQueue.addAll(collectionMap.values());
    }

    public PriorityQueue<MusicBand> getMusicBandPriorityQueue() {
        return musicBandPriorityQueue;
    }

    public Date getInitializationDate() {
        return this.initializationDate;
    }

    public MusicBand getByIdOrNull(int id) {
        for (MusicBand musicBand : musicBandPriorityQueue) {
            if (musicBand.getId() == id) {
                return musicBand;
            }
        }
        return null;
    }

    public String add() {
        MusicBandForm form = new MusicBandForm();
        MusicBand musicBand = form.createNew();
        if (musicBand != null) {
            this.musicBandPriorityQueue.add(musicBand);
            return "     Новая банда была успешно создана";
        }
        return "     Interupt adding";
    }

    public String countGreaterThanNumberOfParticipants(int numberOfParticipants) {
        try {
            PriorityQueue<MusicBand> musicBandById = this.getMusicBandPriorityQueue();
            int count = 0;
            for (MusicBand musicBand : musicBandById) {
                if (musicBand.getNumberOfParticipants() > numberOfParticipants) {
                    count++;
                }
            }
            return "Количество элементов, значение поля numberOfParticipants которых больше " + numberOfParticipants + ": " + count;

        } catch (NumberFormatException e) {
            return "     Второй аргумент не является числом";
        }
    }

    public String printUniqueEstablishmentDate() {
        List<MusicBand> musicBands = new ArrayList<>(this.getMusicBandPriorityQueue());
        Set<ZonedDateTime> uniqueEstablishmentDates = new HashSet<>();
        for (MusicBand band : musicBands) {
            uniqueEstablishmentDates.add(band.getEstablishmentDate());
        }
        StringBuilder result = new StringBuilder();
        result.append("     Уникальные значения поля establishmentDate:\n");
        for (java.time.ZonedDateTime establishmentDate : uniqueEstablishmentDates) {
            result.append("     ").append(establishmentDate).append("\n");
        }
        return result.toString();
    }

    public String removeById(int idNumber) {
        try {
            MusicBand musicBandbyId = this.getByIdOrNull(idNumber);
            if (musicBandbyId == null) {
                return "     Not Found";
            }
            String removeBandName = musicBandbyId.getName();
            boolean removed = this.getMusicBandPriorityQueue().removeIf(band -> band.getId() == idNumber);
            if (removed) {
                int newId = 1;
                for (MusicBand band : this.getMusicBandPriorityQueue()) {
                    band.setId(newId++);
                }
                return "     Старая банда " + removeBandName + " была успешно изменена";
            } else {
                return "     Старая банда " + removeBandName + " не была найдена";
            }
        } catch (NumberFormatException e) {
            return "     Second Argument is not a number";
        }
    }

    public String removeGreater(int idNumber) {
        try {
            MusicBand musicBandById = this.getByIdOrNull(idNumber);
            if (musicBandById != null) {
                List<MusicBand> bandsToRemove = new ArrayList<>();
                for (MusicBand band : this.getMusicBandPriorityQueue()) {
                    if (band.compareTo(musicBandById) > 0) {
                        bandsToRemove.add(band);
                    }
                }
                for (MusicBand bandToRemove : bandsToRemove) {
                    this.getMusicBandPriorityQueue().remove(bandToRemove);
                }
                return "     Элементы были удалены";
            } else {
                return "     Music band with ID " + idNumber + " not found.";
            }
        } catch (NumberFormatException e) {
            return "     Second Argument is not a number";
        }
    }

    public String removeHead() {
        MusicBand removedBand = this.getMusicBandPriorityQueue().poll();
        if (removedBand != null) {
            int newId = 1;
            for (MusicBand band : this.getMusicBandPriorityQueue()) {
                band.setId(newId++);
            }
            return "     Removed head of the collection: " + removedBand;
        } else {
            return "     The collection is empty, nothing to remove.";
        }
    }

    public String removeLower(int idNumber) {
        try {
            MusicBand musicBandById = this.getByIdOrNull(idNumber);
            if (musicBandById != null) {
                List<MusicBand> bandsToRemove = new ArrayList<>();
                for (MusicBand band : this.getMusicBandPriorityQueue()) {
                    if (band.compareTo(musicBandById) < 0) {
                        bandsToRemove.add(band);
                    }
                }
                for (MusicBand bandToRemove : bandsToRemove) {
                    this.getMusicBandPriorityQueue().remove(bandToRemove);
                }
                return "     Элементы были удалены";
            } else {
                return "     Music band with ID " + idNumber + " not found.";
            }
        } catch (NumberFormatException e) {
            return "     isNan";
        }
    }

    public String save() {
        String xmlFilePath = System.getenv("XML_FILE_PATH");
        if (!xmlFilePath.isEmpty()) {
            return CollectionToXml.writePriorityQueueToXml(this.getMusicBandPriorityQueue(), xmlFilePath);
        } else {
            return "     Переменная окружения XML_FILE_PATH не установлена или пуста";
        }
    }

    public String show() {
        StringBuilder result = new StringBuilder();
        for (MusicBand musicBand : musicBandPriorityQueue) {
            result.append(musicBand).append("\n\n\n\n\n");
        }
        return result.toString();
    }
}
