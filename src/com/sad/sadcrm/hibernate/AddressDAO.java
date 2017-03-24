package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.HttpJsonException;
import com.sad.sadcrm.model.Address;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sad.sadcrm.HttpJson.post;
import static com.sad.sadcrm.HttpJson.postHTML;


public class AddressDAO {
    public static void insert(Address address) {
        try {
            JSONObject result = post("/address/create", address.asParameters());
            address.setId(result.getInt("id"));
        } catch (JSONException exception) {
            throw new RuntimeException("Missing \"id\" key", exception);
        } catch (HttpJsonException exception) {
            throw new RuntimeException("Couldn't insert Address", exception);
        }
    }

    public static void update(Address address) {
        postHTML("/address/update", address.asParameters());
    }
}
