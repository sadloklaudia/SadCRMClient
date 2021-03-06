package com.sad.sadcrm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpJson {
    private static String BASE_URL;

    public static void useGlobalServer() {
        HttpJson.BASE_URL = "http://sadcrm.pe.hu";
    }

    public static void useLocalServer() {
        HttpJson.BASE_URL = "http://localhost/SadCRM";
    }

    public static JSONObject get(String urlToRead, Parameters parameters) throws HttpJsonException {
        String result = getHTML(urlToRead, parameters);

        try {
            JSONObject jsonObject = new JSONObject(result);
            throwOnSuccessFalse(jsonObject);
            return jsonObject;
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static JSONObject post(String urlToRead, Parameters parameters) throws HttpJsonException {
        String result = postHTML(urlToRead, parameters);
        try {
            JSONObject jsonObject = new JSONObject(result);
            throwOnSuccessFalse(jsonObject);
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage() + "\n" + result);
        }
    }

    private static void throwOnSuccessFalse(JSONObject jsonObject) throws HttpJsonException {
        try {
            if (!jsonObject.getBoolean("success")) {
                throw new HttpJsonException(jsonObject.getString("message"));
            }
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static String getHTML(String urlToRead, Parameters parameters) {
        try {
            URL url = new URL(BASE_URL + urlToRead + "?" + parameters.getParamString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return getStringFromInputStream(connection.getInputStream());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String postHTML(String urlToRead, Parameters parameters) {
        try {
            String paramString = parameters.getParamString();
//            paramString = Encryption.decrypt(paramString);
            byte[] postData = paramString.getBytes(UTF_8);
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

            try (OutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.write(postData);
            }
            String result = getStringFromInputStream(connection.getInputStream());
            //  result = Encryption.decrypt(result);
            return result;
        } catch (ConnectException exception) {
            throw new ServerConnectException(exception);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static String getStringFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }
}
