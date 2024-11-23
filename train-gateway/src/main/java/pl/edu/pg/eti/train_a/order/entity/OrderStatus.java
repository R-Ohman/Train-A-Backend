package pl.edu.pg.eti.train_a.order.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    ACTIVE("active"),
    COMPLETED("completed"),
    REJECTED("rejected"),
    CANCELED("canceled");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
