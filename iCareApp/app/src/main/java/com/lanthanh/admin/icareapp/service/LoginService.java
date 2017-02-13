package com.lanthanh.admin.icareapp.service;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 13-Feb-17.
 */

public interface LoginService {
    @POST("Select_ToAuthenticate.php")
    Observable<String> logIn(@Body String json);
}
