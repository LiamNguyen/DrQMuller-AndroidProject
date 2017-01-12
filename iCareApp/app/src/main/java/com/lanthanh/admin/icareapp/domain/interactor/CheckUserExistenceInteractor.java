package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public interface CheckUserExistenceInteractor extends Interactor {
    interface Callback{
        void onUserExist();
        void onUserNotExist(String username, String password);
    }
}
