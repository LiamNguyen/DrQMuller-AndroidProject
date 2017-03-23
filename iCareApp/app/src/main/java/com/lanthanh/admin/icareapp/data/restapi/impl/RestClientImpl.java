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
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTimeEco;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTimeSelected;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import java.util.ArrayList;
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
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Countries")) {
                                List<DTOCountry> countries = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Countries")); //TODO save user json to pref
                                return Observable.just(countries);
                            }
                        }
                        return Observable.just(new ArrayList<DTOCountry>());
                    }
                );
    }

    @Override
    public Observable<List<DTOCity>> getCitiesByCountryId(int countryId) {
        return service.getCitiesByCountryId(countryId)
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Cities")) {
                                List<DTOCity> cities = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Cities")); //TODO save user json to pref
                                return Observable.just(cities);
                            }
                        }
                        return Observable.just(new ArrayList<DTOCity>());
                    }
                );
    }

    @Override
    public Observable<List<DTODistrict>> getDistrictsByCityId(int cityId) {
        return service.getDistrictsByCityId(cityId)
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Districts")) {
                                List<DTODistrict> districts = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Districts")); //TODO save user json to pref
                                return Observable.just(districts);
                            }
                        }
                        return Observable.just(new ArrayList<DTODistrict>());
                    }
                );
    }

    @Override
    public Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId) {
        return service.getLocationsByDistrictId(districtId)
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Locations")) {
                                List<DTOLocation> locations = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Locations")); //TODO save user json to pref
                                return Observable.just(locations);
                            }
                        }
                        return Observable.just(new ArrayList<DTOLocation>());
                    }
                );
    }

    @Override
    public Observable<List<DTOVoucher>> getVouchers() {
        return service.getVouchers()
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Vouchers")) {
                                List<DTOVoucher> vouchers = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Vouchers")); //TODO save user json to pref
                                return Observable.just(vouchers);
                            }
                        }
                        return Observable.just(new ArrayList<DTOVoucher>());
                    }
                );
    }

    @Override
    public Observable<List<DTOType>> getTypes() {
        return service.getTypes()
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Types")) {
                                List<DTOType> types = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Types")); //TODO save user json to pref
                                return Observable.just(types);
                            }
                        }
                        return Observable.just(new ArrayList<DTOType>());
                    }
                );
    }

    @Override
    public Observable<List<DTOTime>> getAllTime() {
        return service.getAllTime()
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_AllTime")) {
                                List<DTOTime> time = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_AllTime")); //TODO save user json to pref
                                return Observable.just(time);
                            }
                        }
                        return Observable.just(new ArrayList<DTOTime>());
                    }
                );
    }

    @Override
    public Observable<List<DTOTimeEco>> getEcoTime() {
        return service.getCountries()
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_EcoTime")) {
                                List<DTOTimeEco> ecoTime = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_EcoTime")); //TODO save user json to pref
                                return Observable.just(ecoTime);
                            }
                        }
                        return Observable.just(new ArrayList<DTOTimeEco>());
                    }
                );
    }

    @Override
    public Observable<List<DTOTimeSelected>> getSelectedTime(int dayId, int locationId, int machineId) {
        return service.getSelectedTime(dayId, locationId, machineId)
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_SelectedTime")) {
                                List<DTOTimeSelected> selectedTime = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_SelectedTime")); //TODO save user json to pref
                                return Observable.just(selectedTime);
                            }
                        }
                        return Observable.just(new ArrayList<DTOTimeSelected>());
                    }
                );
    }

    @Override
    public Observable<List<DTOWeekDay>> getDaysOfWeek() {
        return service.getCountries()
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_DaysOfWeek")) {
                                List<DTOWeekDay> weekdays = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_DaysOfWeek")); //TODO save user json to pref
                                return Observable.just(weekdays);
                            }
                        }
                        return Observable.just(new ArrayList<DTOWeekDay>());
                    }
                );
    }

    @Override
    public Observable<List<DTOMachine>> getMachinesByLocationId(int locationId) {
        return service.getMachinesByLocationId(locationId)
                .concatMap(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Machines")) {
                                List<DTOMachine> machines = ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Machines")); //TODO save user json to pref
                                return Observable.just(machines);
                            }
                        }
                        return Observable.just(new ArrayList<DTOMachine>());
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
