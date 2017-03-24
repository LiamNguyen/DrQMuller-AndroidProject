package com.lanthanh.admin.icareapp.data.restapi.impl;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.iCareService;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;
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
    public Observable<List<DTOCountry>> getCountries() {
        return service.getCountries()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_Countries")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Countries"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOCity>> getCitiesByCountryId(int countryId) {
        return service.getCitiesByCountryId(countryId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Cities")) {
                                //TODO error handling needed in stead of null?
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Cities"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTODistrict>> getDistrictsByCityId(int cityId) {
        return service.getDistrictsByCityId(cityId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_Districts")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Districts"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId) {
        return service.getLocationsByDistrictId(districtId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_Locations")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Locations"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOVoucher>> getVouchers() {
        return service.getVouchers()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_Vouchers")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Vouchers"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOType>> getTypes() {
        return service.getTypes()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_Types")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Types"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOTime>> getAllTime() {
        return service.getAllTime()
                .map(
                    response -> {
                        //TODO error handling needed in stead of null?
                        if (response.code() == 200) {
                            if (response.body().has("Select_AllTime")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_AllTime"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOTime>> getEcoTime() {
        return service.getCountries()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_EcoTime")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_EcoTime"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOTime>> getSelectedTime(int dayId, int locationId, int machineId) {
        return service.getSelectedTime(dayId, locationId, machineId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_SelectedTime")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_SelectedTime"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOWeekDay>> getDaysOfWeek() {
        return service.getCountries()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_DaysOfWeek")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_DaysOfWeek"));
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<List<DTOMachine>> getMachinesByLocationId(int locationId) {
        return service.getMachinesByLocationId(locationId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_Machines")) {
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Machines"));
                            }
                        }
                        return null;
                    }
                );
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
