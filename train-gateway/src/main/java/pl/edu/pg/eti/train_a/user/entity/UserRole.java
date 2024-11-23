package pl.edu.pg.eti.train_a.user.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    MANAGER("manager"),
    USER("user");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
