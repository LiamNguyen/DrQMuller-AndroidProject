package com.lanthanh.admin.icareapp.presentation.presenter;

import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.BaseView;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public interface ConfirmBookingActivityPresenter extends Presenter{
    interface View extends BaseView{
        void navigateToMainActivity(int extra);
        String getStringResource(int id);
    }

    void updateAppointment(String verificationCode);
}
