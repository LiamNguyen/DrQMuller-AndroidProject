package com.lanthanh.admin.icareapp.data.service;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public interface LoginService extends Service {
    @POST("Select_ToAuthenticate.php")
    Observable<JsonObject> login(@Body RequestBody body);
}