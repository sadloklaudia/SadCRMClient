package com.sad.sadcrm.model;

public class Address implements java.io.Serializable {
    private int id;
    private String street;
    private String number;
    private String city;
    private String postCode;

    public Address() {
    }

    public Address(int id, String street, String number, String city, String postCode) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postCode = postCode;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
