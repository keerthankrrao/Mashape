package com.keerthan.mashape;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by LENOVO on 30-01-2017.
 */

public class HTTPHandler {
    private BufferedReader reader;
    private String forecastJsonStr;
    private static final HTTPHandler INSTANCE = new HTTPHandler();

    private HTTPHandler()
    {

    }

    public static HTTPHandler getInstance()
    {
        return INSTANCE;
    }
    public String makeServiceCall(String URL)
    {
        try {
            URL url = new URL(URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setConnectTimeout(2000);
            urlConnection.setRequestProperty("X-Mashape-Key","CDiti8MVfAmshrz62cXzVve2qh9gp1L05D0jsnqwA77F8KBaWe");
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            Log.d("MainActivity","Response Code "+responseCode);
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            forecastJsonStr = buffer.toString();
            return  forecastJsonStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
