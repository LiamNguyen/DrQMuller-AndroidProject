package com.example.admin.icareapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ADMIN on 27-Oct-16.
 */

public class DatabaseQuery {
    private String url_str;
    private BufferedReader reader;
    private JSONObject jsonObj;
    private HttpURLConnection urlConnection;
    private URL url;

    public void getURL(String url){
        this.url_str = url;
    }

    public JSONObject sendRequest(String m){
        try{
            url = new URL(url_str);
            System.out.println(url.toString());
        }catch (MalformedURLException e){
            System.out.println("No legal protocol is found in URL String or URL String cannot be parsed");
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(m.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
        }catch (IOException ioe){
            System.out.println("Cannot open URL connection");
        }

        try {
            PrintWriter writer = new PrintWriter(urlConnection.getOutputStream(), true);
            writer.print(m);
            writer.close();
        }catch (IOException ioe){
            System.out.println("Cannot open output stream");
        }catch (Exception e){
            e.printStackTrace();
        }

        String line = "";

        try{
            InputStream is = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();
            reader.close();
            is.close();
        }catch (IOException ioe){
            System.out.println("Cannot open input Stream");
        }

        try {
            jsonObj = new JSONObject(line);
        }catch (JSONException je){
            System.out.println("Problem with JSON API");
        }

        return jsonObj;
    }

}
