package models.utils;

import models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class XmlParser {

    public static Map<Integer, MusicBand> parseXmlFile(String filePath) {
        Map<Integer, MusicBand> resultMap = new HashMap<>();

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            Element rootElement = doc.getDocumentElement();
            NodeList musicBandsList = rootElement.getElementsByTagName("MusicBand");
            for (int i = 0; i < musicBandsList.getLength(); i++) {
                Node musicBandNode = musicBandsList.item(i);
                if (musicBandNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element musicBandElement = (Element) musicBandNode;
                    MusicBand musicBand = parseMusicBandElement(musicBandElement);
                    if (musicBand != null) { // Проверяем, что объект не null
                        resultMap.put(musicBand.getId(), musicBand);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    private static String getUserHome() {
        return System.getProperty("user.home");
    }

    private static MusicBand parseMusicBandElement(Element musicBandElement) {
        try {
            MusicBand musicBand = new MusicBand();
            musicBand.setId(Integer.parseInt(Objects.requireNonNull(getElementValue(musicBandElement, "id"))));
            musicBand.setName(getElementValue(musicBandElement, "name"));
            musicBand.setCoordinates(parseCoordinatesElement(getElement(musicBandElement, "coordinates")));

            String creationDateString = getElementValue(musicBandElement, "creationDate");
            musicBand.setCreationDate(parseLocalDateTime(creationDateString));

            String numberOfParticipantsString = getElementValue(musicBandElement, "numberOfParticipants");
            musicBand.setNumberOfParticipants(numberOfParticipantsString != null && !numberOfParticipantsString.isEmpty()
                    ? Integer.parseInt(numberOfParticipantsString)
                    : 0);

            String singlesCountString = getElementValue(musicBandElement, "singlesCount");
            musicBand.setSinglesCount(singlesCountString != null && !singlesCountString.isEmpty()
                    ? Long.parseLong(singlesCountString)
                    : 0);

            String establishmentDateString = getElementValue(musicBandElement, "establishmentDate");
            musicBand.setEstablishmentDate(parseZonedDateTime(establishmentDateString));

            String genreString = getElementValue(musicBandElement, "genre");
            musicBand.setGenre(genreString != null && !genreString.isEmpty()
                    ? MusicGenre.valueOf(genreString)
                    : null);

            musicBand.setFrontMan(parsePersonElement(getElement(musicBandElement, "frontMan")));

            return musicBand;
        } catch (Exception e) {
            return null;
        }
    }


    private static LocalDateTime parseLocalDateTime(String dateString) {
        return dateString != null && !dateString.isEmpty()
                ? LocalDateTime.parse(dateString)
                : LocalDateTime.now();
    }


    private static ZonedDateTime parseZonedDateTime(String dateString) {
        try {
            return ZonedDateTime.parse(dateString);
        } catch (DateTimeParseException e) {
            // Handle the parsing exception, for example, log it or return ZonedDateTime.now()
            return ZonedDateTime.now();
        }
    }


    private static Coordinates parseCoordinatesElement(Element coordinatesElement) {
        Coordinates coordinates = new Coordinates();

        coordinates.setX(Long.parseLong(Objects.requireNonNull(getElementValue(coordinatesElement, "x"))));
        coordinates.setY(Double.parseDouble(Objects.requireNonNull(getElementValue(coordinatesElement, "y"))));

        return coordinates;
    }

    private static Person parsePersonElement(Element personElement) {
        if (personElement != null) {
            Person person = new Person();
            String name = getElementValue(personElement, "name_front");
            if (name != null && !name.isEmpty()) {
                person.setName(name);

                String heightString = getElementValue(personElement, "height");
                person.setHeight(heightString != null && !heightString.isEmpty()
                        ? Integer.parseInt(heightString)
                        : 0);

                String eyeColorString = getElementValue(personElement, "eyeColor");
                person.setEyeColor(eyeColorString != null && !eyeColorString.isEmpty()
                        ? Color.valueOf(eyeColorString)
                        : null);

                String hairColorString = getElementValue(personElement, "hairColor");
                person.setHairColor(hairColorString != null && !hairColorString.isEmpty()
                        ? Color.valueOf(hairColorString)
                        : null);

                String nationalityString = getElementValue(personElement, "nationality");
                person.setNationality(nationalityString != null && !nationalityString.isEmpty()
                        ? Country.valueOf(nationalityString)
                        : null);

                return person;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static Element getElement(Element parentElement, String tagName) {
        if (parentElement != null) {
            NodeList nodeList = parentElement.getElementsByTagName(tagName);
            if (nodeList.getLength() > 0) {
                return (Element) nodeList.item(0);
            }
        }
        return null;
    }

    private static String getElementValue(Element parentElement, String tagName) {
        Element element = getElement(parentElement, tagName);
        if (element != null) {

            return element.getTextContent();
        } else {
            return null;
        }
    }
}
