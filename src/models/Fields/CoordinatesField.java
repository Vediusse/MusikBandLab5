package models.Fields;

import exceptions.IllegalArgumentForCordinateXException;
import exceptions.IllegalArgumentForCordinateYException;
import models.Coordinates;

public class CoordinatesField<T1 extends Comparable<T1>, T2 extends Number> extends Field {
    private final boolean nullableX;
    private final boolean nullableY;
    private final T2 maxY;
    private final T1 maxX;
    private final T2 minY;
    private final T1 minX;


    public Coordinates<T1, T2> value;  // Use the generic version of Coordinates

    public CoordinatesField(boolean nullableX, boolean nullableY, T1 minX, T2 minY, T1 maxX, T2 maxY) {
        this.nullableX = nullableX;
        this.nullableY = nullableY;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Coordinates<T1, T2> getValue(T1 x, T2 y) {
        this.examinateNullable(x, nullableX);
        this.examinateNullable(y, nullableX);
        this.minAndMaxXOrY(x, y, minX, minY, maxX, maxY);
        return new Coordinates<>(x, y);
    }

    private void minAndMaxXOrY(T1 x, T2 y, T1 minX, T2 minY, T1 maxX, T2 maxY) {
        if (x.compareTo(minX) < 0) {
            throw new IllegalArgumentForCordinateXException("Value for coordinate X is less than the minimum value");
        }

        if (x.compareTo(maxX) > 0) {
            throw new IllegalArgumentForCordinateXException("Value for coordinate X is greater than the maximum value");
        }

        if (y.doubleValue() < minY.doubleValue()) {
            throw new IllegalArgumentForCordinateYException("Value for coordinate Y is less than the minimum value");
        }

        if (y.doubleValue() > maxY.doubleValue()) {
            throw new IllegalArgumentForCordinateYException("Value for coordinate Y is greater than the maximum value");
        }
    }


}