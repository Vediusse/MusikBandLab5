package models.Fields;

public class TextField extends Field {

    private final boolean empty;
    private final boolean nullable;

    public TextField(boolean nullable, boolean empty) {
        this.nullable = nullable;
        this.empty = empty;
    }

    public String getValue(String value) {
        this.examinateNullable(value, nullable);
        this.examinateEmpty(value, empty);
        return value;
    }

    private void examinateEmpty(String value, boolean empty) {
        if (!empty && value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty.");
        }
    }
}