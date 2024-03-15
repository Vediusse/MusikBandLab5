package models.Fields;

import exceptions.IllegalArgumentForCordinateXException;

public abstract class Field {


    void examinateNullable(Object obj, boolean nullable) {
        if ((obj == null) && (!nullable)) {
            throw new IllegalArgumentForCordinateXException("Null value");
        }
    }
}
