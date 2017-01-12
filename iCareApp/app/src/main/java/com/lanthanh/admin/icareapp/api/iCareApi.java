package com.lanthanh.admin.icareapp.api;

import com.google.gson.JsonElement;

import java.net.HttpURLConnection;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface iCareApi {
    interface Callback{
        void onResponse(String json);
    }
    void sendGetRequest(Callback callback, String url, String data);
    void sendPostRequest(Callback callback, String url, String data);
    void receiveResponse(Callback callback, HttpURLConnection urlConnection);
}
