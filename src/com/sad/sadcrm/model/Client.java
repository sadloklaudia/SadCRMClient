package com.sad.sadcrm.model;

import com.sad.sadcrm.Parameters;
import com.sad.sadcrm.hibernate.AddressDAO;
import com.sad.sadcrm.hibernate.UserDAO;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

import static com.sad.sadcrm.Parameters.getCredentials;

public class Client implements java.io.Serializable {
    private int id;
    private Address address;
    private User user;
    private String name;
    private String surname;
    private String pesel;
    private String phone1;
    private String phone2;
    private String mail;
    private String description;
    private boolean vip;
    private String created;
    private String products;
    private String sellChance;
    private String modified;
    private String tel = "";
    private Timestamp telDate;

    public Client() {
    }

    public Client(int id, Address address, User user, String name, String surname, String pesel, boolean vip, String created) {
        this.id = id;
        this.address = address;
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.vip = vip;
        this.created = created;
    }

    private Client(int id, Address address, User user, String name, String surname, String pesel, String phone1, String phone2, String mail, String description, boolean vip, String created, String products, String sellChance) {
        this.id = id;
        this.address = address;
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.mail = mail;
        this.description = description;
        this.vip = vip;
        this.created = created;
        this.products = products;
        this.sellChance = sellChance;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Timestamp getTelDate() {
        return telDate;
    }

    public void setTelDate(Timestamp telDate) {
        this.telDate = telDate;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return this.pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getVip() {
        return this.vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getProducts() {
        return this.products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getSellChance() {
        return this.sellChance;
    }

    public void setSellChance(String sellChance) {
        this.sellChance = sellChance;
    }

    public Parameters asParameters() {
        return getCredentials()
                .add("id", id)
                .add("address_id", address.getId())
                .add("user_id", user.getId())
                .add("name", name)
                .add("surname", surname)
                .add("pesel", pesel)
                .add("phone1", phone1)
                .add("phone2", phone2)
                .add("mail", mail)
                .add("description", description)
                .add("vip", vip ? "1" : "0")
                .add("created", created)
                .add("products", products)
                .add("sellChance", sellChance)
                .add("modified", modified)
                .add("tel", tel)
                .add("telDate", telDate == null ? "" : telDate.toString());
    }

    public static Client createFromJson(JSONObject jsonObject) throws JSONException {
        return new Client(
                jsonObject.getInt("id"),
                AddressDAO.fetchById(jsonObject.getInt("address_id")),
                UserDAO.getUserById(jsonObject.getInt("user_id")),
                jsonObject.getString("name"),
                jsonObject.getString("surname"),
                jsonObject.getString("pesel"),
                jsonObject.getString("phone1"),
                jsonObject.getString("phone2"),
                jsonObject.getString("mail"),
                jsonObject.getString("description"),
                jsonObject.getString("vip").equals("true"),
                jsonObject.getString("created"),
                jsonObject.getString("products"),
                jsonObject.getString("sellChance")
        );
    }
}
