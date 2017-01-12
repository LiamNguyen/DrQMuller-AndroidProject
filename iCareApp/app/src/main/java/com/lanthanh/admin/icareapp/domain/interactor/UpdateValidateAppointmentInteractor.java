package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;

/**
 * Created by ADMIN on 13-Jan-17.
 */

public interface UpdateValidateAppointmentInteractor extends Interactor{
    interface Callback {
        void onValidateSuccess();
        void onValidateFail();
    }
}
