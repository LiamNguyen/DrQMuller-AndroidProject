package com.lanthanh.admin.icareapp.data.repository;

import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.data.restapi.service.iCareService;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class WelcomeRepositoryImpl implements WelcomeRepository{
    private RestClient restClient;
    private iCareService service;

    public WelcomeRepositoryImpl(){
        restClient = RestClientImpl.getRestClient();
        service = restClient.createService(iCareService.class);
    }

    @Override
    public Observable<JsonObject> login(String username, String password) {
        RequestBody body = restClient.createRequestBody(new String[]{"username", "password"}, new String[]{username, password});
        return service.login(body);
    }

    @Override
    public Observable<JsonObject> signup(String username, String password) {
        RequestBody body = restClient.createRequestBody(new String[]{"username", "password"}, new String[]{username, password});
        return service.signup(body);
    }

    @Override
    public Observable<JsonObject> updateCustomerBasicInfo(String name, String address) {
//        RequestBody body = restClient.createRequestBody(new String[]{"userId", "userName", "userAddress"}, new String[]{id, name, address});
//        return service.updateBasicInfo(,)
        return null;
    }

    @Override
    public Observable<JsonObject> updateCustomerNecessaryInfo(String dob, String gender) {
        return null;
    }

    @Override
    public Observable<JsonObject> updateCustomerImportantInfo(String email, String phone) {
        return null;
    }
}
