package com.lanthanh.admin.icareapp.data.restapi;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public interface iCareService {
    /**
     * This request method is used for checking version code of the app
     * @return an Observable
     */
    @GET("version/android")
    Observable<Response<JsonObject>> checkVersionCode();

    /**
     * This request method is used for registering account
     * @param body body for request
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("user/register")
    Observable<Response<JsonObject>> signup(@Body RequestBody body);

    /**
     * This request method is used for logging account
     * @param body body for request
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("user/login")
    Observable<Response<JsonObject>> login(@Body RequestBody body);

    /**
     * This request method is used for updating user's basic info
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("user/basicinformation")
    Observable<Response<JsonObject>> updateBasicInfo(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for updating user's necessary info
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("user/necessaryinformation")
    Observable<Response<JsonObject>> updateNecessaryInfo(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for updating user's important info
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("user/importantinformation")
    Observable<Response<JsonObject>> updateImportantInfo(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for updating user's important info
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("user")
    Observable<Response<JsonObject>> updateCustomerInfo(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for getting list of countries
     * @return an Observable
     */
    @GET("datasource/countries")
    Observable<Response<JsonObject>> getCountries();

    /**
     * This request method is used for getting list of cities by country id
     * @param countryId id of country where needed cities are located
     * @return an Observable
     */
    @GET("datasource/cities/{countryId}")
    Observable<Response<JsonObject>> getCitiesByCountryId(@Path("countryId") int countryId);

    /**
     * This request method is used for getting list of districts by city id
     * @param cityId id of city where needed districts are located
     * @return an Observable
     */
    @GET("datasource/districts/{cityId}")
    Observable<Response<JsonObject>> getDistrictsByCityId(@Path("cityId") int cityId);

    /**
     * This request method is used for getting list of locations by district id
     * @param districtId id of district where needed locations are located
     * @return an Observable
     */
    @GET("datasource/locations/{districtId}")
    Observable<Response<JsonObject>> getLocationsByDistrictId(@Path("districtId") int districtId);

    /**
     * This request method is used for getting list of vouchers
     * @return an Observable
     */
    @GET("datasource/vouchers")
    Observable<Response<JsonObject>> getVouchers();

    /**
     * This request method is used for getting list of types
     * @return an Observable
     */
    @GET("datasource/types")
    Observable<Response<JsonObject>> getTypes();

    /**
     * This request method is used for getting all time
     * @return an Observable
     */
    @GET("time/alltime")
    Observable<Response<JsonObject>> getAllTime();

    /**
     * This request method is used for getting eco time
     * @return an Observable
     */
    @GET("time/ecotime")
    Observable<Response<JsonObject>> getEcoTime();

    /**
     * This request method is used for getting selected time
     * @param dayId id of day which time was selected
     * @param locationId id of location where time was selected
     * @param machineId id of machine which time was selected
     * @return an Observable
     */
    @GET("time/selectedtime/{dayId}/{locationId}/{machineId}")
    Observable<Response<JsonObject>> getSelectedTime(@Path("dayId") int dayId, @Path("locationId") int locationId, @Path("machineId") int machineId);

    /**
     * This request method is used for getting days of week
     * @return an Observable
     */
    @GET("datasource/daysofweek")
    Observable<Response<JsonObject>> getDaysOfWeek();

    /**
     * This request method is used for getting machines by location id
     * @param locationId id of location where needed machines are located
     * @return an Observable
     */
    @GET("datasource/machines/{locationId}")
    Observable<Response<JsonObject>> getMachinesByLocationId(@Path("locationId") int locationId);

    /**
     * This request method is used for booking time
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("time/book")
    Observable<Response<JsonObject>> bookTime(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for releasing time that no longer being booked
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("time/release")
    Observable<Response<JsonObject>> releaseTime(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for validate all appointments
     * @return an Observable
     */
    @POST("appointment/validate")
    Observable<Response<JsonObject>> validateAppointment();

    /**
     * This request method is used for creating an appointment
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("appointment/create")
    Observable<Response<JsonObject>> createAppointment(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for confirming an appointment
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
            "Content-Type: application/json"
    })
    @POST("appointment/confirm")
    Observable<Response<JsonObject>> confirmAppointment(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for canceling an apoointment
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
            "Content-Type: application/json"
    })
    @POST("appointment/cancel")
    Observable<Response<JsonObject>> cancelAppointment(@Header("Authorization") String authToken, @Body RequestBody body);
}
