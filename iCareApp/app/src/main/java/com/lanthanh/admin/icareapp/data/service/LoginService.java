package com.lanthanh.admin.icareapp.data.service;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public interface LoginService {
    @POST("Select_ToAuthenticate.php")
    Observable<JsonObject> login(@Body RequestBody body);

    class Status{
        public final static String SUCCESS = "success";
        public final static String UNAUTHORIZED = "unauthorized";
        public final static String INTERNAL_ERROR = "internal_error";
    }
}
