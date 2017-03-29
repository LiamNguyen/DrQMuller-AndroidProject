package com.lanthanh.admin.icareapp.domain.repository;

import com.lanthanh.admin.icareapp.presentation.model.UserInfo;

import io.reactivex.Observable;

/**
 * @author longv
 *         Created on 22-Mar-17.
 */

public interface UserRepository {
    Observable<Boolean> checkUserLoggedIn();
    Observable<Boolean> logout();
    Observable<RepositorySimpleStatus> checkUserInformationValidity();
    Observable<UserInfo> getUserInformation();
}
