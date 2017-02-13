package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 13-Feb-17.
 */

public interface UpdateCustomerEmailInteractor extends Interactor {
    interface Callback{
        void onUpdateCustomerEmailSuccess();
        void onUpdateCustomerEmailFail();
    }
}
