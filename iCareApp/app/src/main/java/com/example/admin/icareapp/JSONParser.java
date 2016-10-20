package com.example.admin.icareapp;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ADMIN on 19-Oct-16.
 */
public class JSONParser {
    private String url_str;
    private BufferedReader reader;
    private JSONArray jArray;
    private HttpURLConnection urlConnection;
    private URL url;

    private static JSONParser instance;

    public static JSONParser getInstance() {
        if (instance == null)
            instance = new JSONParser();

        return instance;
    }

    private JSONParser() {
    }

    public void getURL(String url){
        this.url_str = url;
    }

    public JSONArray getJSONFromUrl(){
        try{
            url = new URL(url_str);
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
            }catch (IOException e){
                System.out.println("CANNOT CONNECTTTT");
            }

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));


            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                sb.append(line);
            }
            reader.close();
            line = sb.toString();
            try {

                jArray = new JSONArray(line);

            } catch (JSONException e) {
                System.out.println("Error parsing JSON data");
            }

        } catch (MalformedURLException e) {
            System.out.println("No legal protocol is found in URL String or URL String cannot be parsed");
        } catch (IOException ioe){
            System.out.println("Cannot open URL connection");
        }

        return jArray;
    }

    public String getAuthenticateAndInsertNewUser(String login_id, String password){
        try{
            url = new URL(url_str + "?login_id=" + login_id + "&password=" + password);
        }catch (MalformedURLException e){
            System.out.println("No legal protocol is found in URL String or URL String cannot be parsed");
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        }catch (IOException e){
            System.out.println("Cannot open URL connection");
        }

        String line = "";

        try{
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            line = reader.readLine();
        }catch (IOException ioe){
            System.out.println("Cannot open IO Stream");
        }

        return line;
    }

    public String checkUserExistence(String login_id){
        try{
            url = new URL(url_str + "?login_id=" + login_id);
        }catch (MalformedURLException e){
            System.out.println("No legal protocol is found in URL String or URL String cannot be parsed");
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        }catch (IOException e){
            System.out.println("Cannot open URL connection");
        }

        String line = "";

        try{
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            line = reader.readLine();
        }catch (IOException ioe){
            System.out.println("Cannot open IO Stream");
        }

        return line;
    }
}
