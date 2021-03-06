package com.sad.sadcrm;

import com.sad.sadcrm.model.User;

import static java.lang.String.format;

public class Parameters {
    private static String username;
    private static String password;
    private String paramString;

    private Parameters(String login, String password) {
        paramString = format("credentials[login]=%s&credentials[password]=%s", login, password);
    }

    public Parameters add(String key, String value) {
        paramString += '&' + key + '=' + value;
        return this;
    }

    public Parameters add(String key, int value) {
        paramString += '&' + key + '=' + value;
        return this;
    }

    public Parameters add(String key, boolean value) {
        paramString += '&' + key + '=' + (value ? "true" : "false");
        return this;
    }

    public Parameters byUser(User user) {
        return add("user_id", user.getId());
    }

    public Parameters hasMail(boolean value) {
        return add("has_mail", value);
    }

    String getParamString() {
        return paramString;
    }

    public static void useCredentials(String login, String password) {
        Parameters.username = login;
        Parameters.password = password;
    }

    public static Parameters getCredentials() {
        return new Parameters(username, password);
    }
}
