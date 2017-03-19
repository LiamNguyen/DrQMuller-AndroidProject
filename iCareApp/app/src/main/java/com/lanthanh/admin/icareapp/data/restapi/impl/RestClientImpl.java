package com.lanthanh.admin.icareapp.data.restapi.impl;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public class RestClientImpl implements RestClient {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Retrofit retrofit;
    private static RestClientImpl restClient;

    private RestClientImpl(){
        retrofit = new Retrofit.Builder()
                            .baseUrl("http://210.211.109.180/drmuller_test/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
    }

    public static RestClient getRestClient(){
        if (restClient == null)
            restClient = new RestClientImpl();
        return restClient;
    }

    @Override
    public <T> T createService(Class<T> classOfT) {
        return retrofit.create(classOfT);
    }

    @Override
    public RequestBody createRequestBody(String[] keys, String[] values) {
        String json = NetworkUtils.convertJsonData(keys, values);
        return RequestBody.create(JSON, json);
    }
}
