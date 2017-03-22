package com.lanthanh.admin.icareapp.domain.repository;

import com.google.gson.JsonObject;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public interface WelcomeRepository {
    Observable<RepositorySimpleStatus> login(String username, String password);
    Observable<RepositorySimpleStatus> signup(String username, String password);
    Observable<RepositorySimpleStatus> updateCustomerBasicInfo(String name, String address);
    Observable<RepositorySimpleStatus> updateCustomerNecessaryInfo(String dob, String gender);
    Observable<RepositorySimpleStatus> updateCustomerImportantInfo(String email, String phone);
}
