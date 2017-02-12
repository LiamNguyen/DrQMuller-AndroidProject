package com.lanthanh.admin.icareapp.api.impl;

import android.util.Log;

import com.lanthanh.admin.icareapp.api.iCareApi;

import java.io.IOException;;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ADMIN on 04-Jan-17.
 */
public class iCareApiImpl implements iCareApi {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final String TAG = iCareApiImpl.class.getSimpleName();

    public static iCareApiImpl getAPI() {
        return new iCareApiImpl();
    }

    private iCareApiImpl() {
    }

    @Override
    public synchronized void sendGetRequest(Callback callback, URL url) {
        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(3, TimeUnit.MINUTES);
        b.readTimeout(3, TimeUnit.MINUTES);
        b.writeTimeout(3, TimeUnit.MINUTES);
        OkHttpClient client = b.build();

        String result = null;

        Request request = new Request.Builder()
                                .url(url)
                                .method("GET", null)
                                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            System.out.println(result + " GET");
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        callback.onResponse(result);
    }

    @Override
    public synchronized void sendPostRequest(Callback callback, String url, String[] keys, String[] values) {
        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(3, TimeUnit.MINUTES);
        b.readTimeout(3, TimeUnit.MINUTES);
        b.writeTimeout(3, TimeUnit.MINUTES);
        OkHttpClient client = b.build();

        String result = null;

        FormBody.Builder formBuilder = new FormBody.Builder();
        for (int i = 0; i < keys.length; i++){
            formBuilder.add(keys[i], values[i]);
        }
        RequestBody body = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            System.out.println(result + " POST");
        } catch (IOException e) {
            e.printStackTrace();
        }
        callback.onResponse(result);
    }

    @Override
    public synchronized void sendPostRequest(Callback callback, String url, String json) {
        System.out.println(json + "JSON data");
        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(3, TimeUnit.MINUTES);
        b.readTimeout(3, TimeUnit.MINUTES);
        b.writeTimeout(3, TimeUnit.MINUTES);
        OkHttpClient client = b.build();

        String result = null;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            System.out.println(result + " POST");
        } catch (IOException e) {
            e.printStackTrace();
        }
        callback.onResponse(result);
    }
}
