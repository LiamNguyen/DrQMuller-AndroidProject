package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public interface UpdateCustomerPasswordInteractor extends Interactor {
    interface Callback{
        void onResetPasswordSuccess();
        void onResetPasswordFail();
    }
}
