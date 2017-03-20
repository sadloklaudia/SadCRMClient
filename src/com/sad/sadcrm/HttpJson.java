package com.sad.sadcrm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpJson {
    final static String BASE_URL = "http://localhost/SadCRM";

    public static void main(String[] args) throws JSONException {
        String result = postHTML("/address/create",
                new Parameters("daniel", "daniel")
                        .add("street", "Jakas tam")
                        .add("number", "jakis tam"));
        System.out.println(result);
    }

    public static JSONObject get(String urlToRead) {
        String result = getHTML(urlToRead);
        try {
            return new JSONObject(result);
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static JSONObject post(String urlToRead, Parameters parameters) {
        String result = postHTML(urlToRead, parameters);
        try {
            return new JSONObject(result);
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static String getHTML(String urlToRead) {
        try {
            URL url = new URL(BASE_URL + urlToRead);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return getStringFromInputStream(connection.getInputStream());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String postHTML(String urlToRead, Parameters parameters) {
        try {
            byte[] postData = parameters.getParamString().getBytes(UTF_8);
            int postDataLength = postData.length;
            URL url = new URL(BASE_URL + urlToRead);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }
            return getStringFromInputStream(connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getStringFromInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String result = "";
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
        }
    }
}
