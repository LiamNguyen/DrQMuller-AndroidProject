package com.lanthanh.admin.icareapp.service;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 13-Feb-17.
 */

public interface RegisterService {
    @POST("Insert_NewCustomer.php")
    Observable<String> register(@Body String json);

    @POST("Update_BasicInfo.php")
    Observable<String> updateBasicInfo(@Body String json);

    @POST("Update_NecessaryInfo")
    Observable<String> updateNecessaryInfo(@Body String json);

    @POST("Update_ImportantInfo")
    Observable<String> updateImportantInfo(@Body String json);
}
