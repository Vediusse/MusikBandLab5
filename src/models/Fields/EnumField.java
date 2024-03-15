package models.Fields;

public class EnumField<E extends Enum<E>> extends Field {

    private final boolean nullable;

    public EnumField(boolean nullable) {
        this.nullable = nullable;
    }

    public E getValue(E value) {
        examinateNullable(value, this.nullable);
        return value;
    }
}