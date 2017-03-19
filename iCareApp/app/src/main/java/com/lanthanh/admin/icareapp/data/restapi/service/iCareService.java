package com.lanthanh.admin.icareapp.data.restapi.service;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
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
     * This request method is used for registering account
     * @param body body for request
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("/user/register")
    Observable<JsonObject> signup(@Body RequestBody body);

    /**
     * This request method is used for logging account
     * @param body body for request
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("/user/login")
    Observable<JsonObject> login(@Body RequestBody body);

    /**
     * This request method is used for updating user's basic info
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @PUT("/user/basicinformation")
    Observable<JsonObject> updateBasicInfo(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for updating user's necessary info
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @PUT("/user/necessaryinformation")
    Observable<JsonObject> updateNecessaryInfo(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for updating user's important info
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @PUT("/user/importantinfomation")
    Observable<JsonObject> updateImportantInfo(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for getting list of countries
     * @return an Observable
     */
    @GET("/datasource/countries")
    Observable<JsonObject> getCountries();

    /**
     * This request method is used for getting list of cities by country id
     * @param countryId id of country where needed cities are located
     * @return an Observable
     */
    @GET("/datasource/cities/{countryId}")
    Observable<JsonObject> getCitiesByCountryId(@Path("countryId") int countryId);

    /**
     * This request method is used for getting list of districts by city id
     * @param cityId id of city where needed districts are located
     * @return an Observable
     */
    @GET("/datasource/districts/{cityId}")
    Observable<JsonObject> getDistrictsByCityId(@Path("cityId") int cityId);

    /**
     * This request method is used for getting list of locations by district id
     * @param districtId id of district where needed locations are located
     * @return an Observable
     */
    @GET("/datasource/locations/{districtId}")
    Observable<JsonObject> getLocationsByDistrictId(@Path("districtId") int districtId);

    /**
     * This request method is used for getting list of vouchers
     * @return an Observable
     */
    @GET("/datasource/vouchers")
    Observable<JsonObject> getVouchers();

    /**
     * This request method is used for getting list of types
     * @return an Observable
     */
    @GET("/datasource/types")
    Observable<JsonObject> getTypes();

    /**
     * This request method is used for getting all time
     * @return an Observable
     */
    @GET("/time/alltime")
    Observable<JsonObject> getAllTime();

    /**
     * This request method is used for getting eco time
     * @return an Observable
     */
    @GET("/time/ecotime")
    Observable<JsonObject> getEcoTime();

    /**
     * This request method is used for getting selected time
     * @param dayId id of day which time was selected
     * @param locationId id of location where time was selected
     * @param machineId id of machine which time was selected
     * @return an Observable
     */
    @GET("/time/selectedtime/{dayId}/{locationId}/{timeId}")
    Observable<JsonObject> getSelectedTime(@Path("dayId") int dayId, @Path("locationId") int locationId, @Path("machineId") int machineId);

    /**
     * This request method is used for getting days of week
     * @return an Observable
     */
    @GET("/datasource/daysofweek")
    Observable<JsonObject> getDaysOfWeek();

    /**
     * This request method is used for getting machines by location id
     * @param locationId id of location where needed machines are located
     * @return an Observable
     */
    @GET("/datasource/machines/{locationId}")
    Observable<JsonObject> getMachinesByLocationId(@Path("locationId") int locationId);

    /**
     * This request method is used for booking time
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("/time/book")
    Observable<JsonObject> bookTime(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for releasing time that no longer being booked
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @PUT("/time/release")
    Observable<JsonObject> releaseTime(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for validate all appointments
     * @return an Observable
     */
    @PUT("/appointment/validate")
    Observable<JsonObject> validateAppointment();

    /**
     * This request method is used for creating an appointment
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
        "Content-Type: application/json"
    })
    @POST("/appointment/create")
    Observable<JsonObject> createAppointment(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for confirming an appointment
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/appointment/confirm")
    Observable<JsonObject> confirmAppointment(@Header("Authorization") String authToken, @Body RequestBody body);

    /**
     * This request method is used for canceling an apoointment
     * @param body body for request
     * @param authToken token for authorization
     * @return an Observable
     */
    @Headers({
            "Content-Type: application/json"
    })
    @PUT("/appointment/cancel")
    Observable<JsonObject> cancelAppointment(@Header("Authorization") String authToken, @Body RequestBody body);
}
