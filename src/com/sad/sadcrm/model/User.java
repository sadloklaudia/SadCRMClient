package com.sad.sadcrm.model;

import com.sad.sadcrm.Parameters;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sad.sadcrm.Parameters.getCredentials;

public class User implements java.io.Serializable {
    private int id;
    private String name;
    private String surname;
    private UserType type;
    private String login;
    private String password;
    private String created;

    public User() {
    }

    public User(int id, String name, String surname, UserType type, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public User(int id, String name, String surname, UserType type, String login, String password, String created) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.login = login;
        this.password = password;
        this.created = created;
    }

    public User(int user_id) {
        this.id = user_id;
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

    public UserType getType() {
        return this.type;
    }

    public void setType(UserType type) {
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
                UserType.fromTitle(user.getString("type")),
                user.getString("login"),
                "",
                user.getString("created")
        );
    }

    public Parameters asParameters() {
        return getCredentials()
                .add("id", id)
                .add("name", name)
                .add("surname", surname)
                .add("type", type.toString())
                .add("login", login)
                .add("password", password)
                .add("created", created);
    }
}
