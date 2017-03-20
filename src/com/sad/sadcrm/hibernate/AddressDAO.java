package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.model.Address;

import static com.sad.sadcrm.HttpJson.postHTML;

public class AddressDAO {
    public static void insert(Address address) {
        postHTML("http://localhost/SadCRM/address/create", address.asParameters());
    }

    public static void update(Address address) {
        postHTML("http://localhost/SadCRM/address/update", address.asParameters());
    }
}
