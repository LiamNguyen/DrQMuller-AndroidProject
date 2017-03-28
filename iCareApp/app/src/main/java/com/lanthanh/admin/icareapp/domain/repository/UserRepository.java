package com.lanthanh.admin.icareapp.domain.repository;

import io.reactivex.Observable;

/**
 * @author longv
 *         Created on 22-Mar-17.
 */

public interface UserRepository {
    Observable<Boolean> checkUserLoggedIn();
}
