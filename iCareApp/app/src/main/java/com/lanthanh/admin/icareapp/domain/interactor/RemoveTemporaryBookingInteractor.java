package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;

/**
 * Created by ADMIN on 09-Jan-17.
 */

public interface RemoveTemporaryBookingInteractor extends Interactor{
    interface Callback{
        void onRemoveTempBookingSuccess(DTOAppointmentSchedule appointmentSchedule);
        void onRemoveTempBookingFail();
    }
}
