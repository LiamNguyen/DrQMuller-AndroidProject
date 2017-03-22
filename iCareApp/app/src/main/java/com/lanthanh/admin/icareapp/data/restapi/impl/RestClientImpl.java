package com.lanthanh.admin.icareapp.data.restapi.impl;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.iCareService;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public class RestClientImpl implements RestClient {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final iCareService service;
    private LocalStorage localStorage;
    private List<RepositorySimpleStatus> validFailResponse;

    private RestClientImpl(){
        final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://210.211.109.180/beta_drmuller/api/index.php/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        service = retrofit.create(iCareService.class);
        validFailResponse = Arrays.asList(RepositorySimpleStatus.TIME_BOOKED_SUCCESSFULLY,
                                         RepositorySimpleStatus.TIME_HAS_BEEN_BOOKED,
                                         RepositorySimpleStatus.USERNAME_EXISTED,
                                         RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH);
    }

    private RestClientImpl(LocalStorage localStorage){
        this();
        this.localStorage = localStorage;
    }

    public static RestClient createRestClient(){
        return new RestClientImpl();
    }

    public static RestClient createRestClient(LocalStorage localStorage){
        return new RestClientImpl(localStorage);
    }

    @Override
    public RequestBody createRequestBody(String[] keys, String[] values) {
        String json = NetworkUtils.convertJsonData(keys, values);
        return RequestBody.create(JSON, json);
    }

    public Observable<RepositorySimpleStatus> login(String username, String password){
        return service.login(createRequestBody(new String[]{"username", "password"}, new String[]{username, password}))
                      .concatMap(
                          response -> {
                              if (response.code() == 200) {
                                  if (response.body().has("Select_ToAuthenticate")) {
                                      UserInfo user = ConverterJson.convertGsonToObject(response.body().getAsJsonArray("Select_ToAuthenticate").get(0), UserInfo.class);
                                      localStorage.saveUserToLocal(user);
                                      return Observable.just(RepositorySimpleStatus.SUCCESS);
                                  }
                              } else {
                                  RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "Select_ToAuthenticate");
                                  if (validFailResponse.contains(status)){
                                      return Observable.just(status);
                                  }
                              }
                              return Observable.just(RepositorySimpleStatus.SUCCESS);
                          }
                      );
    }

    public Observable<RepositorySimpleStatus> signup(String username, String password){
        String userId=""; //TODO put user id here
        return service.login(createRequestBody(new String[]{"userId", "username", "password"}, new String[]{userId, username, password}))
                        .concatMap(
                            response -> {
                                if (response.code() == 200) {
                                    if (response.body().has("Insert_NewCustomer")) {
                                        UserInfo user = ConverterJson.convertGsonToObject(response.body().getAsJsonArray("Select_ToAuthenticate").get(0), UserInfo.class);
                                        localStorage.saveUserToLocal(user);
                                        return Observable.just(RepositorySimpleStatus.SUCCESS);
                                    }
                                } else {
                                    RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "Insert_NewCustomer");
                                    if (validFailResponse.contains(status)){
                                        return Observable.just(status);
                                    }
                                }
                                return Observable.just(RepositorySimpleStatus.SUCCESS);
                            }
                        );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateBasicInfo(String authToken, String name, String address) {
        String userId=""; //TODO put user id here
        return service.updateBasicInfo(authToken, createRequestBody(new String[]{"userId", "userName", "userAddress"}, new String[]{userId, name, address}))
                        .concatMap(
                            response -> {
                                if (response.code() == 200) {
                                    if (response.body().has("Update_BasicInfo")) {
                                        response.body().getAsJsonArray("Update_BasicInfo").getAsString(); //TODO save user json to pref
                                        return Observable.just(RepositorySimpleStatus.SUCCESS);
                                    }
                                } else {
                                    RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "Update_BasicInfo");
                                    if (validFailResponse.contains(status)){
                                        return Observable.just(status);
                                    }
                                }
                                return Observable.just(RepositorySimpleStatus.SUCCESS);
                            }
                        );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateNecessaryInfo(String authToken, String dob, String gender) {
        String userId=""; //TODO put user id here
        return service.updateNecessaryInfo(authToken, createRequestBody(new String[]{"userId", "userDob", "userGender"}, new String[]{userId, dob, gender}))
                .concatMap(
                        response -> {
                            if (response.code() == 200) {
                                if (response.body().has("Update_NecessaryInfo")) {
                                    response.body().getAsJsonArray("Update_NecessaryInfo").getAsString(); //TODO save user json to pref
                                    return Observable.just(RepositorySimpleStatus.SUCCESS);
                                }
                            } else {
                                RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "Update_NecessaryInfo");
                                if (validFailResponse.contains(status)){
                                    return Observable.just(status);
                                }
                            }
                            return Observable.just(RepositorySimpleStatus.SUCCESS);
                        }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateImportantInfo(String authToken, String email, String phone) {
        String userId=""; //TODO put user id here
        return service.updateImportantInfo(authToken, createRequestBody(new String[]{"userId", "userEmail", "userPhone"}, new String[]{userId, email, phone}))
                .concatMap(
                        response -> {
                            if (response.code() == 200) {
                                if (response.body().has("Update_ImportantInfo")) {
                                    response.body().getAsJsonArray("Update_ImportantInfo").getAsString(); //TODO save user json to pref
                                    return Observable.just(RepositorySimpleStatus.SUCCESS);
                                }
                            } else {
                                RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "Update_ImportantInfo");
                                if (validFailResponse.contains(status)){
                                    return Observable.just(status);
                                }
                            }
                            return Observable.just(RepositorySimpleStatus.SUCCESS);
                        }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> getCountries() {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getCitiesByCountryId(int countryId) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getDistrictsByCityId(int cityId) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getLocationsByDistrictId(int districtId) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getVouchers() {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getTypes() {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getAllTime() {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getEcoTime() {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getSelectedTime(int dayId, int locationId, int machineId) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getDaysOfWeek() {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> getMachinesByLocationId(int locationId) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> bookTime(String authToken, RequestBody body) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> releaseTime(String authToken, RequestBody body) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> validateAppointment() {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> createAppointment(String authToken, RequestBody body) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> confirmAppointment(String authToken, RequestBody body) {
        return null;
    }

    @Override
    public Observable<RepositorySimpleStatus> cancelAppointment(String authToken, RequestBody body) {
        return null;
    }

    private RepositorySimpleStatus resolveErrorReponse(int respCode, String error, String key) {
        JsonObject errorBody = ConverterJson.convertJsonToObject(error, JsonObject.class);
        if (respCode == 409) {
            return RepositorySimpleStatus.TIME_HAS_BEEN_BOOKED;
        } else if (respCode == 404) {
            return RepositorySimpleStatus.PAGE_NOT_FOUND;
        } else {
            if (errorBody.has(key)) {
                String errorCode = errorBody.getAsJsonArray(key)
                        .get(0).getAsJsonObject()
                        .get("errorCode").getAsString();
                switch (errorCode) {
                    case "pattern-fail":
                        return RepositorySimpleStatus.PATTERN_FAIL;
                    case "invalid-username-or-password":
                        return RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH;
                    case "customer-existed":
                        return RepositorySimpleStatus.USERNAME_EXISTED;
                    case "invalid-token":
                        return RepositorySimpleStatus.INVALID_TOKEN;
                    case "required-fields-missing":
                        return RepositorySimpleStatus.REQUIRED_FIELD_MISSING;
                }
            }
        }
        return RepositorySimpleStatus.UNKNOWN_ERROR;
    }
}
