package com.sad.sadcrm.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements java.io.Serializable {
    private int id;
    private String name;
    private String surname;
    private String type;
    private String login;
    private String password;
    private String created;

    public User() {
    }

    public User(int id, String name, String surname, String type, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public User(int id, String name, String surname, String type, String login, String password, String created) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.login = login;
        this.password = password;
        this.created = created;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public static User createFromJson(JSONObject user) throws JSONException {
        return new User(
                user.getInt("id"),
                user.getString("name"),
                user.getString("surname"),
                user.getString("type"),
                user.getString("login"),
                "",
                user.getString("created")
        );
    }
}
