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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (HttpJsonException e) {
            e.printStackTrace();
        }
    }

    public static void update(Address address) {
        postHTML("/address/update", address.asParameters());
    }
}
