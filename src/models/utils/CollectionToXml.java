package models.utils;

import models.MusicBand;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class CollectionToXml {

    public static String writePriorityQueueToXml(PriorityQueue<MusicBand> musicBandPriorityQueue, String filePath) {
        File outputFile = new File(filePath);

        if (!outputFile.exists()) {
            return "     Файл не найден: " + filePath;
        } else if (!outputFile.canRead()) {
            return "     Невозможно прочитать файл: " + filePath;
        } else {
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
                StringBuilder xmlContent = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                xmlContent.append("<MusicBands>\n");

                List<MusicBand> musicBandList = new ArrayList<>(musicBandPriorityQueue);
                for (MusicBand musicBand : musicBandList) {
                    xmlContent.append("\t<MusicBand>\n");
                    xmlContent.append("\t\t<id>").append(musicBand.getId()).append("</id>\n");
                    xmlContent.append("\t\t<name>").append(escapeXml(musicBand.getName())).append("</name>\n");
                    xmlContent.append("\t\t<coordinates>\n");
                    xmlContent.append("\t\t\t<x>").append(musicBand.getCoordinates().getX()).append("</x>\n");
                    xmlContent.append("\t\t\t<y>").append(musicBand.getCoordinates().getY()).append("</y>\n");
                    xmlContent.append("\t\t</coordinates>\n");
                    xmlContent.append("\t\t<creationDate>").append(musicBand.getCreationDate()).append("</creationDate>\n");
                    xmlContent.append("\t\t<numberOfParticipants>").append(musicBand.getNumberOfParticipants()).append("</numberOfParticipants>\n");
                    xmlContent.append("\t\t<singlesCount>").append(musicBand.getSinglesCount()).append("</singlesCount>\n");
                    xmlContent.append("\t\t<establishmentDate>").append(musicBand.getEstablishmentDate()).append("</establishmentDate>\n");
                    xmlContent.append("\t\t<genre>").append(musicBand.getGenre()).append("</genre>\n");
                    xmlContent.append("\t\t<frontMan>\n");
                    if (musicBand.getFrontMan() != null) {
                        xmlContent.append("\t\t\t<name_front>").append(escapeXml(musicBand.getFrontMan().getName())).append("</name_front>\n");
                        xmlContent.append("\t\t\t<height>").append(musicBand.getFrontMan().getHeight()).append("</height>\n");
                        xmlContent.append("\t\t\t<eyeColor>").append(musicBand.getFrontMan().getEyeColor()).append("</eyeColor>\n");
                        xmlContent.append("\t\t\t<hairColor>").append(musicBand.getFrontMan().getHairColor()).append("</hairColor>\n");
                        xmlContent.append("\t\t\t<nationality>").append(musicBand.getFrontMan().getNationality()).append("</nationality>\n");
                    }
                    xmlContent.append("\t\t</frontMan>\n");
                    xmlContent.append("\t</MusicBand>\n");
                }

                xmlContent.append("</MusicBands>");

                outputStream.write(xmlContent.toString().getBytes());
                return "    Удачное сохранение!";
            } catch (Exception e) {
                return "     Невозможно записать в файл " + filePath;
            }
        }
    }

    private static String getUserHome() {
        return System.getProperty("user.home");
    }

    private static String escapeXml(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
