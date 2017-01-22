package com.lanthanh.admin.icareapp.api.impl;

import com.lanthanh.admin.icareapp.api.iCareApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ADMIN on 04-Jan-17.
 */
public class iCareApiImpl implements iCareApi {
    private HttpURLConnection urlConnection;
    private URL mUrl;
    BufferedReader reader;

    //private static iCareApiImpl api = new iCareApiImpl();

    public static iCareApiImpl getAPI() {
        return new iCareApiImpl();
    }

    private iCareApiImpl() {
    }

    @Override
    public void sendGetRequest(Callback callback, String url, String data) {

    }

    @Override
    public synchronized void sendPostRequest(Callback callback, String url, String data) {
        try{
            mUrl = new URL(url);
            System.out.println(mUrl.toString());
        }catch (MalformedURLException e){
            System.out.println("No legal protocol is found in URL String or URL String cannot be parsed");
        }

        try {
            urlConnection = (HttpURLConnection) mUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(data.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
        }catch (IOException ioe){
            System.out.println("Cannot open URL connection");
        }

        try {
            PrintWriter writer = new PrintWriter(urlConnection.getOutputStream(), true);
            writer.print(data);
            writer.flush();
            writer.close();
        }catch (IOException ioe){
            System.out.println("Cannot open output stream");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(data);

        receiveResponse(callback, urlConnection);
    }

    @Override
    public void receiveResponse(Callback callback, HttpURLConnection urlConnection) {
        String line;
        String response = "";

        try{
            InputStream is = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null){
                System.out.println("test " + line);
                response += line;
            }
            reader.close();
            is.close();
        }catch (IOException ioe){
            System.out.println("Cannot open input stream");
        }

        if (!response.isEmpty()){
            callback.onResponse(response);

        }else{
            callback.onResponse(null);
            System.out.println("No data received from server. Error in PHP files");
        }
        System.out.println(response);
    }
}
