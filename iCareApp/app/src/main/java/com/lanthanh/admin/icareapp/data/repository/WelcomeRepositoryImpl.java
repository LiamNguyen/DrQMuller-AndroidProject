package com.lanthanh.admin.icareapp.data.repository;

import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.data.restapi.iCareService;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class WelcomeRepositoryImpl implements WelcomeRepository{
    private RestClient restClient;

    public WelcomeRepositoryImpl(){
        restClient = RestClientImpl.createRestClient();
    }

    @Override
    public Observable<RepositorySimpleStatus> login(String username, String password) {
        return restClient.login(username, password);
    }

    @Override
    public Observable<RepositorySimpleStatus> signup(String username, String password) {
        return restClient.signup(username, password);
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerBasicInfo(String name, String address) {
        return restClient.updateBasicInfo("", name, address); //TODO check 1st params
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerNecessaryInfo(String dob, String gender) {
        return restClient.updateNecessaryInfo("", dob, gender); //TODO check 1st params
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerImportantInfo(String email, String phone) {
        return restClient.updateImportantInfo("", email, phone); //TODO check 1st params
    }
}
