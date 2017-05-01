package com.sad.sadcrm.model;

import static java.util.Arrays.stream;

public enum UserType {
    ADMIN("Administrator"), MANAGER("Manager"), USER("Pracownik");

    private final String title;

    UserType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static UserType fromTitle(String title) {
        return stream(values())
                .filter(string -> string.getTitle().equals(title))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
