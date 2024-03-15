package models;

import exceptions.IllegalArgumentForCordinateXException;
import exceptions.IllegalArgumentForCordinateYException;

public class Coordinates<T1, T2 extends Number> {
    private T1 x; // Field cannot be null
    private T2 y; // Field must be greater than -263, Cannot be null

    public Coordinates(T1 x, T2 y) {
        this.setX(x);
        this.setY(y);
    }

    public Coordinates() {
    }

    public T1 getX() {
        return x;
    }

    public void setX(T1 x) {
        if (x == null) {
            throw new IllegalArgumentForCordinateXException("X coordinate cannot be null");
        }
        this.x = x;
    }

    public T2 getY() {
        return y;
    }

    public void setY(T2 y) {
        if (y == null) {
            throw new IllegalArgumentForCordinateYException("Y coordinate cannot be null");
        }
        if (y.doubleValue() <= -263) {
            throw new IllegalArgumentForCordinateYException("Coordinate Y should be greater than -263");
        }
        this.y = y;
    }


    @Override
    public String toString() {
        return String.format("""
                                                
                        \t\tx=%s,
                        \t\ty=%s
                        """,
                x, y);
    }
}