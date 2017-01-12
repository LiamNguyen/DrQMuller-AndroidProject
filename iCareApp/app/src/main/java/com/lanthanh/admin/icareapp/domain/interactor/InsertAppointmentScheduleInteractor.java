package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public interface InsertAppointmentScheduleInteractor extends Interactor {
    interface Callback{
        void onInsertBookingTimeFail();
        void onInsertBookingTimeSuccess();
    }
}
