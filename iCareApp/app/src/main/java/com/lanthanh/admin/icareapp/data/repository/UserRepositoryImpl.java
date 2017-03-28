package com.lanthanh.admin.icareapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;

import io.reactivex.Observable;

/**
 * @author longv
 *         Created on 22-Mar-17.
 */

public class UserRepositoryImpl implements UserRepository {
    private LocalStorage localStorage;

    public UserRepositoryImpl(Context context){
        localStorage = new LocalStorage(context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE));
    }

    @Override
    public Observable<Boolean> checkUserLoggedIn() {
        UserInfo user = localStorage.getUserFromLocal();
        if (user != null && user.isValid())
            return Observable.just(true);
        return Observable.just(false);
    }
}
