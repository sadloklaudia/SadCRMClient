package com.sad.sadcrm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTest {
    public static void main(String[] args) throws JSONException {
        // String wynik = executePost("http://localhost/SadCRM/client/pesel/89042200841", "");
        String wynik = getHTML("http://localhost/SadCRM/client/pesel/89042200841");


        JSONObject obj = new JSONObject(wynik);
        JSONArray array = obj.getJSONArray("hej");

        for (int i = 0; i < array.length(); i++) {
            int liczba = array.getInt(i);
            int imie = array.getString(i);
            int nazwisko = array.getString(i);

            dataGRidView.addRow(liczba, imie, nazwisko)
        }

    }

    public static String getHTML(String urlToRead) {
        try {
            URL url = new URL(urlToRead);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            String result = "";
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
