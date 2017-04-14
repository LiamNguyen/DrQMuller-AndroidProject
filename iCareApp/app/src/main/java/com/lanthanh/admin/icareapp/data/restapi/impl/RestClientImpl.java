package com.lanthanh.admin.icareapp.data.restapi.impl;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lanthanh.admin.icareapp.data.restapi.iCareEmailService;
import com.lanthanh.admin.icareapp.utils.ConverterUtils;
import com.lanthanh.admin.icareapp.data.model.BookedAppointment;
import com.lanthanh.admin.icareapp.data.model.BookedSchedule;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.iCareService;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.utils.Function;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    private final iCareEmailService emailService;
    private List<RepositorySimpleStatus> validFailResponse;

    private RestClientImpl(){
        final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://210.211.109.180/beta_drmuller/api/index.php/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        final Retrofit emailRetrofit  = new Retrofit.Builder()
                            .baseUrl("http://210.211.109.180/beta_drmuller/api/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        service = retrofit.create(iCareService.class);
        emailService = emailRetrofit.create(iCareEmailService.class);
        validFailResponse = Arrays.asList(
            RepositorySimpleStatus.TIME_HAS_BEEN_BOOKED,
            RepositorySimpleStatus.USERNAME_EXISTED,
            RepositorySimpleStatus.INVALID_TOKEN,
            RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH
        );
    }

    public static RestClient createRestClient(){
        return new RestClientImpl();
    }

    @Override
    public RequestBody createRequestBody(String[] keys, String[] values) {
        String json = ConverterUtils.json.convertToApiJson(keys, values);
        return RequestBody.create(JSON, json);
    }

    @Override
    public RequestBody createRequestBody(String json) {
        return RequestBody.create(JSON, json);
    }

    @Override
    public Observable<RepositorySimpleStatus> checkVersionCode(int versionCode) {
        return service.checkVersionCode().map(
                response -> {
                    if (response.code() == 200) {
                        if (response.body().has("Select_LatestBuild")) {
                            int _versionCode = response.body().getAsJsonArray("Select_LatestBuild")
                                    .get(0).getAsJsonObject()
                                    .get("build").getAsInt();
                            if (versionCode != _versionCode) {
                                return RepositorySimpleStatus.UPDATE_NEEDED;
                            } else {
                                return RepositorySimpleStatus.SUCCESS;
                            }
                        }
                        return RepositorySimpleStatus.API_ERROR;
                    }
                    return resolveErrorReponse(response.code(), response.errorBody().string(), "Select_LatestBuild");
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> login(Function.VoidParam<UserInfo> saveUser, String username, String password){
        return service.login(createRequestBody(new String[]{"username", "password"}, new String[]{username, password}))
                .map(
                  response -> {
                      if (response.code() == 200) {
                          if (response.body().has("Select_ToAuthenticate")) {
                              String jwt = response.body().getAsJsonArray("Select_ToAuthenticate")
                                                          .get(0).getAsJsonObject()
                                                          .get("jwt").getAsString();
                              UserInfo user = parseUserJwt(jwt);
                              if (user != null) {
                                  saveUser.apply(user);
                                  return RepositorySimpleStatus.SUCCESS;
                              } else {
                                  return RepositorySimpleStatus.PARSING_ERROR;
                              }
                          }
                          return RepositorySimpleStatus.API_ERROR;
                      }
                      return resolveErrorReponse(response.code(), response.errorBody().string(), "Select_ToAuthenticate");
                  }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> signup(Function.VoidParam<UserInfo> saveUser, String username, String password){
        return service.signup(createRequestBody(new String[]{"username", "password"}, new String[]{username, password}))
                .map(
                    response -> {
                        if (response.code() == 201) {
                            if (response.body().has("Insert_NewCustomer")) {
                                String jwt = response.body().getAsJsonArray("Insert_NewCustomer")
                                        .get(0).getAsJsonObject()
                                        .get("jwt").getAsString();
                                UserInfo user = parseUserJwt(jwt);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                } else {
                                    return RepositorySimpleStatus.PARSING_ERROR;
                                }
                            }
                            return RepositorySimpleStatus.API_ERROR;
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Insert_NewCustomer");
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateBasicInfo(Function.VoidParam<UserInfo> saveUser, String authToken, String userId, String name, String address) {
        return service.updateBasicInfo(authToken, createRequestBody(new String[]{"userId", "userName", "userAddress"}, new String[]{userId, name, address}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Update_BasicInfo")) {
                                String jwt = response.body().getAsJsonArray("Update_BasicInfo")
                                        .get(0).getAsJsonObject()
                                        .get("jwt").getAsString();
                                UserInfo user = parseUserJwt(jwt);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                } else {
                                    return RepositorySimpleStatus.PARSING_ERROR;
                                }
                            }
                            return RepositorySimpleStatus.API_ERROR;
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Update_BasicInfo");
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateNecessaryInfo(Function.VoidParam<UserInfo> saveUser, String authToken, String userId, String dob, String gender) {
        return service.updateNecessaryInfo(authToken, createRequestBody(new String[]{"userId", "userDob", "userGender"}, new String[]{userId, dob, gender}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Update_NecessaryInfo")) {
                                String jwt = response.body().getAsJsonArray("Update_NecessaryInfo")
                                        .get(0).getAsJsonObject()
                                        .get("jwt").getAsString();
                                UserInfo user = parseUserJwt(jwt);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                } else {
                                    return RepositorySimpleStatus.PARSING_ERROR;
                                }
                            }
                            return RepositorySimpleStatus.API_ERROR;
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Update_NecessaryInfo");
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateImportantInfo(Function.VoidParam<UserInfo> saveUser, String authToken, String userId, String email, String phone) {
        return service.updateImportantInfo(authToken, createRequestBody(new String[]{"userId", "userEmail", "userPhone"}, new String[]{userId, email, phone}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Update_ImportantInfo")) {
                                String jwt = response.body().getAsJsonArray("Update_ImportantInfo")
                                        .get(0).getAsJsonObject()
                                        .get("jwt").getAsString();
                                UserInfo user = parseUserJwt(jwt);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                } else {
                                    return RepositorySimpleStatus.PARSING_ERROR;
                                }
                            }
                            return RepositorySimpleStatus.API_ERROR;
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Update_ImportantInfo");
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerInfo(Function.VoidParam<UserInfo> saveUser, String authToken, String userId, String name, String address,
                                                                 String dob, String gender, String email, String phone) {
        return service.updateCustomerInfo(
                authToken,
                createRequestBody(new String[]{"userId", "userName", "userAddress", "userDob", "userGender", "userEmail", "userPhone"},
                                  new String[]{userId, name, address, dob, gender, email, phone}))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Update_CustomerInformation")) {
                                String jwt = response.body().getAsJsonArray("Update_CustomerInformation")
                                        .get(0).getAsJsonObject()
                                        .get("jwt").getAsString();
                                UserInfo user = parseUserJwt(jwt);
                                if (user != null) {
                                    saveUser.apply(user);
                                    return RepositorySimpleStatus.SUCCESS;
                                } else {
                                    return RepositorySimpleStatus.PARSING_ERROR;
                                }
                            }
                            return RepositorySimpleStatus.API_ERROR;
                        }
                        return resolveErrorReponse(response.code(), response.errorBody().string(), "Update_CustomerInformation");
                    }
                );
    }

    @Override
    public Observable<List<DTOCountry>> getCountries() {
        return service.getCountries()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Countries")) {
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_Countries"), DTOCountry.class);
                            }
                        }
                        return new ArrayList<>();
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
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_Cities"), DTOCity.class);
                            }
                        }
                        return new ArrayList<>();
                    }
                );
    }

    @Override
    public Observable<List<DTODistrict>> getDistrictsByCityId(int cityId) {
        return service.getDistrictsByCityId(cityId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Districts")) {
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_Districts"), DTODistrict.class);
                            }
                        }
                        return new ArrayList<>();
                    }
                );
    }

    @Override
    public Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId) {
        return service.getLocationsByDistrictId(districtId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Locations")) {
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_Locations"), DTOLocation.class);
                            }
                        }
                        return new ArrayList<>();
                    }
                );
    }

    @Override
    public Observable<List<DTOVoucher>> getVouchers() {
        return service.getVouchers()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Vouchers")) {
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_Vouchers"), DTOVoucher.class);
                            }
                        }
                        return new ArrayList<>();
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
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_Types"), DTOType.class);
                            }
                        }
                        return new ArrayList<>();
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
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_AllTime"), DTOTime.class);
                            }
                        }
                        return new ArrayList<>();
                    }
                );
    }

    @Override
    public Observable<List<DTOTime>> getEcoTime() {
        return service.getEcoTime()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            //TODO error handling needed in stead of null?
                            if (response.body().has("Select_EcoTime")) {
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_EcoTime"), DTOTime.class);
                            }
                        }
                        return new ArrayList<>();
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
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_SelectedTime"), DTOTime.class);
                            }
                        }
                        return new ArrayList<>();
                    }
                );
    }

    @Override
    public Observable<List<DTOWeekDay>> getDaysOfWeek() {
        return service.getDaysOfWeek()
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_DaysOfWeek")) {
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_DaysOfWeek"), DTOWeekDay.class);
                            }
                        }
                        return new ArrayList<>();
                    }
                );
    }

    @Override
    public Observable<List<DTOMachine>> getMachinesByLocationId(int locationId) {
        return service.getMachinesByLocationId(locationId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Select_Machines")) {
                                return ConverterUtils.json.convertGsonToObjectList(response.body().getAsJsonArray("Select_Machines"), DTOMachine.class);
                            }
                        }
                        return new ArrayList<>();
                    }
                );
    }

    @Override
    public Observable<RepositorySimpleStatus> bookTime(String authToken, BookedSchedule bookedSchedules) {
        String json = ConverterUtils.json.convertObjectToJson(bookedSchedules);
        return service.bookTime(authToken, createRequestBody(json))
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
    public Observable<RepositorySimpleStatus> releaseTime(String authToken, BookedSchedule bookedSchedules) {
        String json = ConverterUtils.json.convertObjectToJson(bookedSchedules);
        return service.releaseTime(authToken, createRequestBody(json))
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
    public Observable<String> createAppointment(String authToken, BookedAppointment appointment) {
        String json = ConverterUtils.json.convertObjectToJson(appointment);
        return service.createAppointment(authToken, createRequestBody(json))
                .map(
                    response -> {
                        if (response.code() == 200) {
                            if (response.body().has("Insert_NewAppointment")) {
                                return response.body().getAsJsonArray("Insert_NewAppointment")
                                        .get(0).getAsJsonObject()
                                        .get("appointmentId").getAsString(); //TODO save user json to pref
                            }
                        }
                        return "";
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

    @Override
    public Observable<RepositorySimpleStatus> sendEmailNotifyBooking(String appointmentId) {
        return emailService.sendEmailNotifyBooking(appointmentId)
                .map(
                    response -> {
                        if (response.code() == 200) {
                            return RepositorySimpleStatus.SUCCESS;
                        } else {
                            RepositorySimpleStatus status = resolveErrorReponse(response.code(), response.errorBody().string(), "SendMail_NotifyBooking");
                            if (validFailResponse.contains(status)){
                                return status;
                            }
                        }
                        return RepositorySimpleStatus.UNKNOWN_ERROR;
                    }
                );
    }

    private RepositorySimpleStatus resolveErrorReponse(int respCode, String error, String key) {
        JsonObject errorBody = ConverterUtils.json.convertJsonToObject(error, JsonObject.class);
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

    public UserInfo parseUserJwt (String token) {
        UserInfo user = null;
        try {
            Claims jwt = Jwts.parser().setSigningKey("drmuller".getBytes("UTF-8")).parseClaimsJws(token).getBody();
            HashMap data = (HashMap) jwt.get("data");
            user = new UserInfo(
                (String) data.get("userId"),
                (String)data.get("userName"),
                (String)data.get("userDob"),
                (String)data.get("userGender"),
                (String)data.get("userPhone"),
                (String)data.get("userAddress"),
                (String)data.get("userEmail"),
                (String)data.get("sessionToken"),
                (String)data.get("step"),
                (int) data.get("active")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
