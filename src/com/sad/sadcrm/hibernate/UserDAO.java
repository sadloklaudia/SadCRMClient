package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.HttpJson;
import com.sad.sadcrm.HttpJsonException;
import com.sad.sadcrm.Parameters;
import com.sad.sadcrm.UserLoginException;
import com.sad.sadcrm.hibernate.exception.UserInsertException;
import com.sad.sadcrm.hibernate.exception.UserUpdateException;
import com.sad.sadcrm.model.LoginResponse;
import com.sad.sadcrm.model.User;
import com.sad.sadcrm.model.UserType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.sad.sadcrm.HttpJson.post;
import static com.sad.sadcrm.Parameters.getCredentials;
import static com.sad.sadcrm.model.LoginResponse.createFromJson;

public class UserDAO {
    public static LoginResponse login(String login, String password) {
        try {
            Parameters.useCredentials(login, password);
            JSONObject jsonLoginResponse = post("/user/login", getCredentials());
            return createFromJson(jsonLoginResponse);
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        } catch (HttpJsonException exception) {
            throw new UserLoginException(exception);
        }
    }

    public static void insertUser(User user) {
        try {
            post("/user/create", user.asParameters());
        } catch (HttpJsonException exception) {
            throw new UserInsertException(exception);
        }
    }

    public static void updateUser(User user) {
        try {
            post("/user/update", user.asParameters());
        } catch (HttpJsonException exception) {
            throw new UserUpdateException(exception);
        }
    }

    public static User getUserById(Integer id) {
        return fetchUserByParameters(getCredentials()
                .add("id", Integer.toString(id)));
    }

    public static List<User> searchUsers() {
        return fetchUsersByParameters(getCredentials()
                .add("type", UserType.USER.toString()));
    }

    private static User fetchUserByParameters(Parameters parameters) {
        try {
            JSONObject object = HttpJson.get("/user/byId", parameters);
            return User.createFromJson(object.getJSONObject("user"));
        } catch (HttpJsonException exception) {
            throw new RuntimeException("Could not fetch user", exception);
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static List<User> fetchUsersByParameters(Parameters parameters) {
        try {
            JSONObject object = HttpJson.get("/user", parameters);
            List<User> users = new ArrayList<>();
            JSONArray jsonUsers = object.getJSONArray("users");
            for (int i = 0; i < jsonUsers.length(); i++) {
                users.add(User.createFromJson(jsonUsers.getJSONObject(i)));
            }
            return users;
        } catch (HttpJsonException exception) {
            throw new RuntimeException("Could not fetch users", exception);
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        }
    }
}
