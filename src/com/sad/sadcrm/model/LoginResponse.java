package com.sad.sadcrm.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginResponse {
    private String version;
    private User user;

    private LoginResponse(String version, User user) {
        this.version = version;
        this.user = user;
    }

    public static LoginResponse createFromJson(JSONObject response) throws JSONException {
        return new LoginResponse(
                response.getString("version"),
                User.createFromJson(response.getJSONObject("user"))
        );
    }

    public String getVersion() {
        return version;
    }

    public User getUser() {
        return user;
    }
}
