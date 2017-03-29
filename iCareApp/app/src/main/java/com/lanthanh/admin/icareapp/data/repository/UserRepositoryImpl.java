package com.lanthanh.admin.icareapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    @Override
    public Observable<RepositorySimpleStatus> checkUserInformationValidity() {
        UserInfo user = localStorage.getUserFromLocal();
        if (user != null) {
            if (!user.isBasicInfoValid())
                return Observable.just(RepositorySimpleStatus.MISSING_NAME_AND_ADDRESS);
            else if (!user.isNecessaryInfoValid())
                return Observable.just(RepositorySimpleStatus.MISSING_DOB_AND_GENDER);
            else if (!user.isImportantInfoValid())
                return Observable.just(RepositorySimpleStatus.MISSING_EMAIL_AND_PHONE);
            else {
                return Observable.just(RepositorySimpleStatus.VALID_USER);
            }
        }
        return Observable.just(RepositorySimpleStatus.MISSING_USER);
    }

    @Override
    public Observable<Boolean> logout() {
        try {
            localStorage.removeUserFromLocal();
            return Observable.just(true);
        }catch (Exception e) {
            Log.e(this.getClass().getName(), "Error removing user from pref");
            return Observable.just(false);
        }
    }

    @Override
    public Observable<UserInfo> getUserInformation() {
        return Observable.just(localStorage.getUserFromLocal());
    }
}
