package com.lanthanh.admin.icareapp.presentation.presenter;

import com.lanthanh.admin.icareapp.presentation.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.base.BaseView;

import java.util.Calendar;

/**
 * Created by ADMIN on 25-Jan-17.
 */

public interface BookingSelectDatePresenter extends Presenter {
    interface View extends BaseView {
        void displayStartDate(String startDate);
        void displayExpireDate(String expireDate);
        void showStartDatePicker(Calendar calendar);
        void showExpireDatePicker(Calendar calendar);
        void enableExpireDate();
        void dateDisplayOnTypeOrVoucherChange();
        String getStringResource(int id);
    }

    void onStartDateSet(Calendar startDate);
    void onExpireDateSet(Calendar expireDate);
    void onStartDatePickerClick();
    void onExpireDatePickerClick();
    void resetStartDate();
    void resetExpireDate();
    boolean isAllInfoFilled();
    boolean ecoBookingDayCheck(Calendar c);
}
