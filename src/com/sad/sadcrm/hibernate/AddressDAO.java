package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.HttpJson;
import com.sad.sadcrm.HttpJsonException;
import com.sad.sadcrm.Parameters;
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

    public static Address fetchById(int id) {
        return fetchAddressById(Parameters.getCredentials().add("id", id));
    }

    private static Address fetchAddressById(Parameters parameters) {
        try {
            JSONObject object = HttpJson.get("/address/byId", parameters);
            return Address.createFromJson(object.getJSONObject("address"));
        } catch (HttpJsonException e) {
            throw new RuntimeException("Could not fetch address", e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
