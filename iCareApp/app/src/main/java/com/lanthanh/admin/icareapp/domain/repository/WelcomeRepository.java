package com.lanthanh.admin.icareapp.domain.repository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public interface WelcomeRepository {
    Observable<RepositorySimpleStatus> login2(String username, String password);
    Single<RepositorySimpleStatus> login(String username, String password);
    Observable<RepositorySimpleStatus> signup(String username, String password);
    Observable<RepositorySimpleStatus> checkVersionCode(int versionCode);
}
