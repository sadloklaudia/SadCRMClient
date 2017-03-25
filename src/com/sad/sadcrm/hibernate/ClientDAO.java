package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.HttpJson;
import com.sad.sadcrm.HttpJsonException;
import com.sad.sadcrm.Parameters;
import com.sad.sadcrm.hibernate.exception.ClientInsertException;
import com.sad.sadcrm.hibernate.exception.ClientUpdateException;
import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sad.sadcrm.HttpJson.post;
import static com.sad.sadcrm.Parameters.getCredentials;
import static java.util.Calendar.DAY_OF_MONTH;

public class ClientDAO {
    public static void insert(Client client) {
        try {
            post("/client/create", client.asParameters());
        } catch (HttpJsonException exception) {
            throw new ClientInsertException(exception.getMessage());
        }
    }

    public static void update(Client client) {
        try {
            post("/client/update", client.asParameters());
        } catch (HttpJsonException exception) {
            throw new ClientUpdateException(exception.getMessage());
        }
    }

    public static Client getClientById(Integer id) {
        return fetchClientById(getCredentials().add("id", id + ""));
    }

    public static List<Client> searchByCreateDate(String date) {
        return fetchClientsByParameters(getCredentials()
                .add("created", date));
    }

    public static List<Client> phonesFromDate(int days) {
        String date = getDateDaysAgo(days);

        return fetchClientsByParameters(getCredentials()
                .add("telDateSoonerThan", date));
    }

    private static String getDateDaysAgo(int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(DAY_OF_MONTH, -days);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(calendar.getTime());
    }

    public static List<Client> searchClients() {
        return fetchClientsByParameters(getCredentials());
    }

    public static List<Client> searchBySurname(String surname) {
        return fetchClientsByParameters(getCredentials()
                .add("surname", surname));
    }

    public static List<Client> searchByPesel(String pesel) {
        return fetchClientsByParameters(getCredentials()
                .add("pesel", pesel));
    }

    public static List<Client> searchByUser(User user) {
        return fetchClientsByParameters(getCredentials()
                .add("user_id", user.getId() + ""));
    }

    public static List<Client> searchBySurnameAndPesel(String surname, String pesel) {
        return fetchClientsByParameters(getCredentials()
                .add("surname", surname)
                .add("pesel", pesel)
        );
    }

    public static List<Client> searchByUserAndPesel(User user, String pesel) {
        return fetchClientsByParameters(getCredentials()
                .add("user_id", user.getId() + "")
                .add("pesel", pesel)
        );
    }

    public static List<Client> searchByUserSurnameAndPesel(User user, String surname, String pesel) {
        return fetchClientsByParameters(getCredentials()
                .add("user_id", user.getId() + "")
                .add("surname", surname)
                .add("pesel", pesel));
    }

    public static List<Client> searchByUserAndSurname(User user, String surname) {
        return fetchClientsByParameters(getCredentials()
                .add("user_id", user.getId() + "")
                .add("surname", surname));
    }

    public static List<Client> searchBySurnameAndPeselAndHasMail(String surname, String pesel) {
        return fetchClientsByParameters(getCredentials()
                .add("surname", surname)
                .add("pesel", pesel)
                .add("has_mail", "true"));
    }

    public static List<Client> searchByPeselAndHasMail(String pesel) {
        return fetchClientsByParameters(getCredentials()
                .add("pesel", pesel)
                .add("has_mail", "true"));
    }

    public static List<Client> searchBySurnameAndHasMail(String surname) {
        return fetchClientsByParameters(getCredentials()
                .add("surname", surname)
                .add("has_mail", "true"));
    }

    public static List<Client> searchHasMail() {
        return fetchClientsByParameters(getCredentials()
                .add("has_mail", "true"));
    }

    public static List<Client> searchHasSellChance() {
        return fetchClientsByParameters(getCredentials()
                .add("has_sellChance", "true"));
    }

    private static Client fetchClientById(Parameters parameters) {
        try {
            JSONObject object = HttpJson.get("/client/byId", parameters);
            return Client.createFromJson(object.getJSONObject("client"));
        } catch (HttpJsonException e) {
            throw new RuntimeException("Could not fetch client", e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Client> fetchClientsByParameters(Parameters parameters) {
        try {
            JSONObject object = HttpJson.get("/client", parameters);
            List<Client> clients = new ArrayList<>();
            JSONArray jsonUsers = object.getJSONArray("clients");
            for (int i = 0; i < jsonUsers.length(); i++) {
                clients.add(Client.createFromJson(jsonUsers.getJSONObject(i)));
            }
            return clients;
        } catch (HttpJsonException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not fetch clients", e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
