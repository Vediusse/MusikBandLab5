package models;

import models.Fields.EnumField;
import models.Fields.NumberField;
import models.Fields.TextField;
import models.Fields.ZonedDateTimeField;

import java.awt.*;

public class Person {
    private final boolean nullableFrontManName = false;
    private final boolean nullableFrontManHeight = false;
    private final boolean isPositiveFrontManHeight = true;

    private final boolean nullableEyeColor = false;
    private final boolean nullableHairColor = false;
    private final boolean nullableNationality = false;
    private final boolean emptyName = false;
    private String frontManName;
    private Integer frontManHeight;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;

    public Person() {
    }

    public Person(String frontManName, Integer frontManHeight, Color eyeColor, Color hairColor, Country nationality) {
        TextField nameField = new TextField(nullableFrontManName, emptyName);
        NumberField<Integer> heightField = new NumberField<>(nullableFrontManHeight, isPositiveFrontManHeight);
        EnumField<Color> eyeColorField = new EnumField<>(nullableEyeColor);
        EnumField<Color> hairColorField = new EnumField<>(nullableHairColor);
        EnumField<Country> nationalityField = new EnumField<>(nullableNationality);
        this.frontManName = nameField.getValue(frontManName);
        this.frontManHeight = heightField.getValue(frontManHeight);
        this.eyeColor = eyeColorField.getValue(eyeColor);
        this.hairColor = hairColorField.getValue(hairColor);
        this.nationality = nationalityField.getValue(nationality);
    }

    public String getName() {
        return frontManName;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.frontManName = name;
    }

    public Integer getHeight() {
        return frontManHeight;
    }

    public void setHeight(Integer height) {
        if (height != null && height <= 0) {
            throw new IllegalArgumentException("Height should be greater than 0");
        }
        this.frontManHeight = height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        if (nationality == null) {
            throw new IllegalArgumentException("Nationality cannot be null");
        }
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return String.format("""

                        \t\tname='%s',
                        \t\theight=%d,
                        \t\teyeColor=%s,
                        \t\thairColor=%s,
                        \t\tnationality=%s
                        \t""",
                frontManName, frontManHeight, eyeColor, hairColor, nationality);
    }
}
