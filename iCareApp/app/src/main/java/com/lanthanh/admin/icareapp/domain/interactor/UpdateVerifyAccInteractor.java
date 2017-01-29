package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 29-Jan-17.
 */

public interface UpdateVerifyAccInteractor extends Interactor {
    interface Callback{
        void onUpdateVerifyAccSuccess();
        void onUpdateVerifyAccFail();
    }
}
