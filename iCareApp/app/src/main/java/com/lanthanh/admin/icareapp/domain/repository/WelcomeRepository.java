package com.lanthanh.admin.icareapp.domain.repository;

import com.google.gson.JsonObject;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public interface WelcomeRepository {
    Observable<JsonObject> login(String username, String password);
    Observable<JsonObject> signup(String username, String password);
    Observable<JsonObject> updateCustomerBasicInfo(String name, String address);
    Observable<JsonObject> updateCustomerNecessaryInfo(String dob, String gender);
    Observable<JsonObject> updateCustomerImportantInfo(String email, String phone);
}
