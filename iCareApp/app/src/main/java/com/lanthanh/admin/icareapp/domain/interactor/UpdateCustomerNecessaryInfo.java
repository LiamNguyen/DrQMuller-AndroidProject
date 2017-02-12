package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 12-Feb-17.
 */

public interface UpdateCustomerNecessaryInfo extends Interactor{
    interface Callback{
        void onUpdateNecessaryInfoSuccess();
        void onUpdateNecessaryInfoFail();
    }
}
