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

    public String getNumberOfCustomers(){
        try{
            url = new URL(url_str);
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

    public String updateUser(String cus_id, String name, String dob, String gender, String phone, String address, String email, String update_date){
        try{
            url = new URL(url_str + "?cus_id=" + cus_id + "&name=" + name + "&dob=" + dob + "&gender=" + gender + "&phone=" + phone + "&address=" + address + "&email=" + email + "&update_date=" + update_date);
            System.out.println(url.toString());
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
