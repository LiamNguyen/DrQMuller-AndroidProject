package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public interface UpdateAppointmentInteractor extends Interactor {
    interface Callback{
        void onUpdateAppointmentSuccess(String verificationCode);
        void onUpdateAppointmentFail();
    }
}
