package com.lanthanh.admin.icareapp.data.repository;

import android.content.Context;

import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.data.restapi.iCareService;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class WelcomeRepositoryImpl implements WelcomeRepository{
    private RestClient restClient;
    private LocalStorage localStorage;

    public WelcomeRepositoryImpl(Context context){
        restClient = RestClientImpl.createRestClient();
        localStorage =  new LocalStorage(context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE));
    }

    @Override
    public Observable<RepositorySimpleStatus> login(String username, String password) {
        return restClient.login(localStorage::saveUserToLocal, username, password);
    }

    @Override
    public Observable<RepositorySimpleStatus> signup(String username, String password) {
        return restClient.signup(localStorage::saveUserToLocal, username, password);
    }
}
