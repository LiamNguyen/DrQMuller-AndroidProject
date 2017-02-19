package com.lanthanh.admin.icareapp.data.service;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public interface EmailService extends Service{
    @POST("SendEmail_VerifyAcc")
    Observable<JsonObject> sendEmailVerifyAcc(@Body RequestBody body);
}
