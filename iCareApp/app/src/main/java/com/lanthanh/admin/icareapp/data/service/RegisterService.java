package com.lanthanh.admin.icareapp.data.service;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public interface RegisterService extends Service {
    @POST("Insert_NewCustomer.php")
    Observable<JsonObject> register(@Body RequestBody body);

    @POST("Update_VerifyAcc.php")
    Observable<JsonObject> verifyAccount(@Body RequestBody body);

    @POST("Update_BasicInfo.php")
    Observable<JsonObject> updateBasicInfo(@Body RequestBody body);

    @POST("Update_NecessaryInfo.php")
    Observable<JsonObject> updateNecessaryInfo(@Body RequestBody body);

    @POST("Update_ImportantInfo1.php")
    Observable<JsonObject> updateImportantInfo(@Body RequestBody body);

    @POST("Update_CustomerEmail.php")
    Observable<JsonObject> updateEmail(@Body RequestBody body);
}
