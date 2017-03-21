package com.sad.sadcrm;

import static java.lang.String.format;

public class Parameters {
    private String paramString;

    private Parameters() {
        paramString = "";
    }

    public Parameters(String login, String password) {
        paramString = format("credentials[login]=%s&credentials[password]=%s", login, password);
    }

    public Parameters add(String key, String value) {
        paramString += '&' + key + '=' + value;
        return this;
    }

    public String getParamString() {
        return paramString;
    }

    public static Parameters getParameters() {
        return new Parameters();
    }

    public static Parameters getCredentials() {
        return new Parameters("daniel", "daniel");
    }
}
