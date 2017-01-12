package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public interface GetCustomerIdInteractor extends Interactor {
    interface Callback{
        void onCustomerIdFound(int id);
        void onCustomerIdNotFound();
    }
}
