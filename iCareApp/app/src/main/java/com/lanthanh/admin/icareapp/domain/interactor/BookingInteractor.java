package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public interface BookingInteractor extends Interactor{
    interface Callback{
        void onBookingFail();
        void onBookingSuccess();
    }
}
