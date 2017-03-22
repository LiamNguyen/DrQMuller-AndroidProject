package com.lanthanh.admin.icareapp.data.repository;

import android.content.SharedPreferences;

import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;

import io.reactivex.Observable;

/**
 * @author longv
 *         Created on 22-Mar-17.
 */

public class UserRepositoryImpl implements UserRepository {
    private LocalStorage localStorage;

    UserRepositoryImpl(SharedPreferences preferences){
        localStorage = new LocalStorage(preferences);
    }

    @Override
    public Observable<RepositorySimpleStatus> checkUserLoggedIn() {
        return null;
    }
}
