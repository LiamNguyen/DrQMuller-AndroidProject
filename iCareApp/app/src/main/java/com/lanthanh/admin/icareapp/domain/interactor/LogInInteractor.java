package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public interface LogInInteractor extends Interactor {
    interface Callback{
        void onLogInFail();
        void onLogInSuccess(String jwt);
    }
}
