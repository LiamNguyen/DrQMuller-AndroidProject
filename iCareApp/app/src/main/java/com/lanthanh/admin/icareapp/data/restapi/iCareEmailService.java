package com.lanthanh.admin.icareapp.data.restapi;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author longv
 *         Created on 02-Apr-17.
 */

public interface iCareEmailService {
    @GET("SendMail_NotifyBooking.php")
    Observable<Response<JsonObject>> sendEmailNotifyBooking(@Query("appointmentId") String appointmentId);
}
