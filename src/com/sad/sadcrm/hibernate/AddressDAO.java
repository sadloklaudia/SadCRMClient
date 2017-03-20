package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.HttpJson;
import com.sad.sadcrm.model.Address;

import static com.sad.sadcrm.Parameters.getParameters;

public class AddressDAO {
    public static void insertAddress(Address address) {
        HttpJson.postHTML("http://localhost/SadCRM/address/create",
                getParameters()
                        .add("street", address.getStreet())
                        .add("number", address.getNumber())
                        .add("city", address.getCity())
                        .add("postCode", address.getPostCode())
        );
    }

    public static void updateAddress(Address address) {
        HttpJson.postHTML("http://localhost/SadCRM/address/update",
                getParameters()
                        .add("id", address.getId() + "")
                        .add("street", address.getStreet())
                        .add("number", address.getNumber())
                        .add("city", address.getCity())
                        .add("postCode", address.getPostCode())
        );
    }
}
