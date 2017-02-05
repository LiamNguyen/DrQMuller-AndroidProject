package com.lanthanh.admin.icareapp.api;

import com.google.gson.JsonElement;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface
iCareApi {
    interface Callback{
        void onResponse(String json);
    }
    void sendGetRequest(Callback callback, URL url);
    void sendPostRequest(Callback callback, String url, String data);
    void receiveResponse(Callback callback, HttpURLConnection urlConnection);
}
