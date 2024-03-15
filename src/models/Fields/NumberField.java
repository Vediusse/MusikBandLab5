package models.Fields;

public class NumberField<T1 extends Number> {

    private final boolean nullable;
    private final boolean positive;

    public NumberField(boolean nullable, boolean positive) {
        this.nullable = nullable;
        this.positive = positive;
    }

    public T1 getValue(T1 value) {
        examinateNullable(value, nullable);
        examinatePositive(value, positive);
        return value;
    }

    private void examinatePositive(T1 value, boolean positive) {
        if (positive && (value == null || value.doubleValue() <= 0)) {
            throw new IllegalArgumentException("Value must be a positive number.");
        }
    }

    private void examinateNullable(T1 value, boolean nullable) {
        if (!nullable && value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
    }
}