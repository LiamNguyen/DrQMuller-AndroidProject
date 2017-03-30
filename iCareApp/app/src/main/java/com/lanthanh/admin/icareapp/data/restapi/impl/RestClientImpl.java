package com.lanthanh.admin.icareapp.data.restapi.impl;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.model.BookedTime;
import com.lanthanh.admin.icareapp.data.model.DataMapper;
import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.iCareService;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.presentation.Function;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
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
    private DataMapper dataMapper;
    private List<RepositorySimpleStatus> validFailResponse;

    private RestClientImpl(){
        final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://210.211.109.180/beta_drmuller/api/index.php/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        service = retrofit.create(iCareService.class);
        dataMapper = new DataMapper();
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

    @Override
    public RequestBody createRequestBody(String json) {
        return RequestBody.create(JSON, json);
    }

    public Observable<RepositorySimpleStatus> login(Function.Void<UserInfo> saveUser, String username, String password){
        return service.login(createRequestBody(new String[]{"username", "password"}, new String[]{username, password}))
                .map(
                  response -> {
                      if (response.code() == 200) {
                          if (response.body().has("Select_ToAuthenticate")) {
                              UserInfo user = ConverterJson.convertGsonToObject(response.body().getAsJsonArray("Select_ToAuthenticate").get(0), UserInfo.class);
                              if (user != null) {
                                  saveUser.apply(user);
                                  return RepositorySimpleStatus.SUCCESS;
                              }
                          }
                          return RepositorySimpleStatus.UNKNOWN_ERROR;
                      }
                      return resolveErrorReponse(response.code(), response.errorBody().string(), "Select_ToAuthenticate");
//                          if (validFailResponse.contains(status)){
//                              return status;
//                          }

                      //return RepositorySimpleStatus.UNKNOWN_ERROR;
                  }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> signup(Function.Void<UserInfo> saveUser, String username, String password){
        return service.signup(createRequestBody(new String[]{"username", "password"}, new String[]{username, password}))
                .map(
                    response -> {
                        if (response.code() == 201) {
                            if (response.body().has("Insert_NewCustomer")) {
                                UserInfo user = ConverterJson.convertGsonToObject(response.body().getAsJsonArray("Insert_NewCustomer").get(0), UserInfo.class);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                }
                            }
                            return RepositorySimpleStatus.UNKNOWN_ERROR;
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Insert_NewCustomer");
//                            if (validFailResponse.contains(status)){
//                                return status;
//                            }
//                        }
//                        return RepositorySimpleStatus.UNKNOWN_ERROR;
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateBasicInfo(Function.Void<UserInfo> saveUser, String authToken, String userId, String name, String address) {
        //String userId=""; //TODO put user id here
        return service.updateBasicInfo(authToken, createRequestBody(new String[]{"userId", "userName", "userAddress"}, new String[]{userId, name, address}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Update_BasicInfo")) {
                                UserInfo user = ConverterJson.convertGsonToObject(response.body().getAsJsonArray("Update_BasicInfo").get(0), UserInfo.class);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                }
                            }
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Update_BasicInfo");
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateNecessaryInfo(Function.Void<UserInfo> saveUser, String authToken, String userId, String dob, String gender) {
        //String userId=""; //TODO put user id here
        return service.updateNecessaryInfo(authToken, createRequestBody(new String[]{"userId", "userDob", "userGender"}, new String[]{userId, dob, gender}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Update_NecessaryInfo")) {
                                UserInfo user = ConverterJson.convertGsonToObject(response.body().getAsJsonArray("Update_NecessaryInfo").get(0), UserInfo.class);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                }
                            }
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Update_NecessaryInfo");

                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateImportantInfo(Function.Void<UserInfo> saveUser, String authToken, String userId, String email, String phone) {
        //String userId=""; //TODO put user id here
        return service.updateImportantInfo(authToken, createRequestBody(new String[]{"userId", "userEmail", "userPhone"}, new String[]{userId, email, phone}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Update_ImportantInfo")) {
                                UserInfo user = ConverterJson.convertGsonToObject(response.body().getAsJsonArray("Update_ImportantInfo").get(0), UserInfo.class);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                }
                            }
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Update_ImportantInfo");
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Countries"), DTOCountry.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Cities"), DTOCity.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Districts"), DTODistrict.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Locations"), DTOLocation.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Vouchers"), DTOVoucher.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Types"), DTOType.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_AllTime"), DTOTime.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_EcoTime"), DTOTime.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_SelectedTime"), DTOTime.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_DaysOfWeek"), DTOWeekDay.class);
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
                                return ConverterJson.convertGsonToObjectList(response.body().getAsJsonArray("Select_Machines"), DTOMachine.class);
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> bookTime(String authToken, int locationId, int dayId, int timeId, int machineId) {
        String bookedTimeStr = ConverterJson.convertObjectToJson(new BookedTime[]{dataMapper.transform(dayId, timeId, machineId)});
        return service.bookTime(authToken, createRequestBody(new String[]{"locationId", "time"}, new String[]{Integer.toString(locationId), bookedTimeStr}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            return RepositorySimpleStatus.SUCCESS;
                        } else {
                            RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "BookingTransaction");
                            if (validFailResponse.contains(status)){
                                return status;
                            }
                        }
                        return RepositorySimpleStatus.UNKNOWN_ERROR;
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> releaseTime(String authToken, int locationId, int dayId, int timeId, int machineId) {
        String bookedTimeStr = ConverterJson.convertObjectToJson(new BookedTime[]{dataMapper.transform(dayId, timeId, machineId)});
        return service.releaseTime(authToken, createRequestBody(new String[]{"locationId", "time"}, new String[]{Integer.toString(locationId), bookedTimeStr}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            return RepositorySimpleStatus.SUCCESS;
                        } else {
                            RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "Update_ReleaseTime");
                            if (validFailResponse.contains(status)){
                                return status;
                            }
                        }
                        return RepositorySimpleStatus.UNKNOWN_ERROR;
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> validateAppointment() {
        return service.validateAppointment()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            return RepositorySimpleStatus.SUCCESS;
                        } else {
                            RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "Update_ValidateAppointment");
                            if (validFailResponse.contains(status)){
                                return status;
                            }
                        }
                        return RepositorySimpleStatus.UNKNOWN_ERROR;
                    }
                );
    }

    @Override
    public Observable<String> createAppointment(String authToken, DTOAppointment appointment) {
        String json  = ConverterJson.convertObjectToJson(dataMapper.transform(appointment));
        return service.createAppointment(authToken, createRequestBody(json))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Insert_NewAppointment")) {
                                return response.body().getAsJsonArray("Insert_Appointment")
                                        .get(0).getAsJsonObject()
                                        .get("appointmentId").getAsString(); //TODO save user json to pref
                            }
                        }
                        return null;
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> confirmAppointment(String authToken, String userId, String appointmentId) {
        return service.confirmAppointment(authToken, createRequestBody(new String[]{"userId", "appointmentId"}, new String[]{userId, appointmentId}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            return RepositorySimpleStatus.SUCCESS;
                        } else {
                            RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "BookingTransaction");
                            if (validFailResponse.contains(status)){
                                return status;
                            }
                        }
                        return RepositorySimpleStatus.UNKNOWN_ERROR;
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> cancelAppointment(String authToken, String userId, String appointmentId) {
        return service.cancelAppointment(authToken, createRequestBody(new String[]{"userId", "appointmentId"}, new String[]{userId, appointmentId}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            return RepositorySimpleStatus.SUCCESS;
                        } else {
                            RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "BookingTransaction");
                            if (validFailResponse.contains(status)){
                                return status;
                            }
                        }
                        return RepositorySimpleStatus.UNKNOWN_ERROR;
                    }
                );
    }

    private RepositorySimpleStatus resolveErrorReponse(int respCode, String error, String key) {
        JsonObject errorBody = ConverterJson.convertJsonToObject(error, JsonObject.class);
        if (respCode == 409) {
            if (key.equals("Insert_NewCustomer"))
                return RepositorySimpleStatus.USERNAME_EXISTED;
            else if (key.equals("BookingTransaction"))
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
