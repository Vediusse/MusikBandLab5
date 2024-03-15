package models;

import exceptions.IllegalArgumentForGenreException;
import exceptions.IllegalArgumentForNameException;
import exceptions.IllegalArgumentForNumberOfParticipantsException;
import exceptions.IllegalArgumentForSinglesException;
import models.Fields.CoordinatesField;
import models.Fields.EnumField;
import models.Fields.ZonedDateTimeField;
import models.Fields.NumberField;
import models.Fields.TextField;
import org.w3c.dom.ls.LSOutput;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MusicBand implements Comparable<MusicBand> {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    private static int lastId = 0;
    private final Long minX = Long.MIN_VALUE;
    private final Double minY = -263.0;
    private final Long maxX = Long.MAX_VALUE;
    private final Double maxY = Double.MAX_VALUE;
    private final boolean isPositivenumberOfParticipants = true;
    private final boolean nullAblePositivenumberOfParticipants = true;
    private final boolean nullAbleGenre = true;
    private final boolean nullAbleDate = true;
    private final boolean nowadaysAble = true;
    public String name; //Поле не может быть null, Строка не может быть пустой
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private long singlesCount; //Значение поля должно быть больше 0
    private java.time.ZonedDateTime establishmentDate; //Поле не может быть null
    private MusicGenre genre; //Поле не может быть null
    private Person frontMan; //Поле может быть null
    private boolean nullableName = false;
    private boolean emptyName = false;
    private boolean nullableX = false;
    private boolean nullableY = true;


    public MusicBand() {
        this.id = generateUniqueID();
        this.creationDate = LocalDateTime.now();
    }

    public MusicBand(String name, Long x, Double y, Integer numberOfParticipants, long singlesCount, MusicGenre genre, ZonedDateTime establishmentDate) {
        this();
        CoordinatesField<Long, Double> cordinatesField = new CoordinatesField<>(nullableX, nullableY, minX, minY, maxX, maxY);
        NumberField<Integer> numberOfParticipantsField = new NumberField<>(nullAblePositivenumberOfParticipants, isPositivenumberOfParticipants);
        NumberField<Long> singlesCountField = new NumberField<>(nullAblePositivenumberOfParticipants, isPositivenumberOfParticipants);
        EnumField<MusicGenre> genreField = new EnumField<>(nullAbleGenre);
        TextField nameField = new TextField(nullableName, emptyName);
        ZonedDateTimeField establishmentDateField = new ZonedDateTimeField(nullAbleDate, nowadaysAble);

        this.coordinates = cordinatesField.getValue(x, y);
        this.name = nameField.getValue(name);
        this.numberOfParticipants = numberOfParticipantsField.getValue(numberOfParticipants);
        this.singlesCount = singlesCountField.getValue(singlesCount);
        this.genre = genreField.getValue(genre);
        this.establishmentDate = establishmentDateField.getValue(establishmentDate);
    }

    public MusicBand(String name, Long x, Double y, Integer numberOfParticipants, long singlesCount, MusicGenre genre, ZonedDateTime establishmentDate, Person frontMan) {
        this(name, x, y, numberOfParticipants, singlesCount, genre, establishmentDate);
        this.frontMan = frontMan;
    }


    private int generateUniqueID() {
        return ++lastId;
    }


    @Override
    public int compareTo(MusicBand other) {
        return Integer.compare(this.getId(), other.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID should be greater than 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentForNameException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates<Long, Double> coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Creation date cannot be null");
        }
        this.creationDate = creationDate;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        if (numberOfParticipants != null && numberOfParticipants <= 0) {
            throw new IllegalArgumentForNumberOfParticipantsException("Number of participants should be greater than 0");
        }
        this.numberOfParticipants = numberOfParticipants;
    }

    public long getSinglesCount() {
        return singlesCount;
    }

    public void setSinglesCount(long singlesCount) {
        if (singlesCount <= 0) {
            throw new IllegalArgumentForSinglesException("Singles count should be greater than 0");
        }
        this.singlesCount = singlesCount;
    }

    public ZonedDateTime getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(ZonedDateTime establishmentDate) {
        if (establishmentDate == null) {
            throw new IllegalArgumentException("Establishment date cannot be null");
        }
        this.establishmentDate = establishmentDate;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        if (genre == null) {
            throw new IllegalArgumentForGenreException("Genre cannot be null");
        }
        this.genre = genre;
    }

    public Person getFrontMan() {
        return frontMan;
    }

    public void setFrontMan(Person frontMan) {
        this.frontMan = frontMan;
    }

    @Override
    public String toString() {
        return String.format("""
                        MusicBand{
                        \tid=%d,
                        \tname='%s',
                        \tcoordinates={%s\t},
                        \tcreationDate=%s,
                        \tnumberOfParticipants=%d,
                        \tsinglesCount=%d,
                        \testablishmentDate=%s,
                        \tgenre=%s,
                        \tfrontMan={%s}
                        }""",
                id, name, formatCoordinates(), formatDate(creationDate), numberOfParticipants, singlesCount,
                formatDate(establishmentDate), genre, formatFrontMan());
    }

    private String formatCoordinates() {
        return coordinates != null ? "\t" + coordinates.toString() : "\tnull,";
    }

    private String formatDate(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } else {
            return "\tnull,";
        }
    }

    private String formatDate(ZonedDateTime zonedDateTime) {
        if (zonedDateTime != null) {
            return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"));
        } else {
            return "\tnull,";
        }
    }


    private String formatFrontMan() {
        return frontMan != null ? "\t" + frontMan : "null";
    }
}