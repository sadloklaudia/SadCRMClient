package com.sad.sadcrm.model;

public enum UserType {
    ADMIN("Administrator"), MANAGER("Manager"), USER("Pracownik");

    private final String title;

    UserType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
