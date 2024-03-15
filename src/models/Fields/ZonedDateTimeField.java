package models.Fields;

import java.time.ZonedDateTime;

public class ZonedDateTimeField {

    private final boolean nullableDate;
    private final boolean nowadaysAble;

    public ZonedDateTimeField(boolean nullableDate, boolean nowadaysAble) {
        this.nullableDate = nullableDate;
        this.nowadaysAble = nowadaysAble;
    }

    public ZonedDateTime getValue(ZonedDateTime value) {
        examinateNullable(value, nullableDate);
        examinateNowadays(value, nowadaysAble);
        return value;
    }

    private void examinateNullable(ZonedDateTime value, boolean nullable) {
        if (!nullable && value == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
    }

    private void examinateNowadays(ZonedDateTime value, boolean nowadaysAble) {
        if (nowadaysAble && (value == null || value.isAfter(ZonedDateTime.now()))) {
            throw new IllegalArgumentException("Date cannot be later than the current date.");
        }
    }
}