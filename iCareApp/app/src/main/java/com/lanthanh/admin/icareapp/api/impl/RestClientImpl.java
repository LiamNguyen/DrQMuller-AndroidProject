package com.lanthanh.admin.icareapp.api.impl;

import com.lanthanh.admin.icareapp.api.RestClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ADMIN on 13-Feb-17.
 */

public class RestClientImpl implements RestClient{
    private Retrofit retrofit;
    private static RestClientImpl restClient;

    private RestClientImpl(){
        retrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("")
                        .build();
    }

    public static RestClientImpl getRestClient(){
        if (restClient == null)
            restClient = new RestClientImpl();

        return restClient;
    }

    @Override
    public <T> T createService(Class<T> classOfT){
        return retrofit.create(classOfT);
    }
}
