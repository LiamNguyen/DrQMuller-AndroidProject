package com.lanthanh.admin.icareapp.domain.repository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public interface WelcomeRepository {
    Observable<RepositorySimpleStatus> login(String username, String password);
    Observable<RepositorySimpleStatus> signup(String username, String password);
    Observable<RepositorySimpleStatus> checkVersionCode(int versionCode);
}
