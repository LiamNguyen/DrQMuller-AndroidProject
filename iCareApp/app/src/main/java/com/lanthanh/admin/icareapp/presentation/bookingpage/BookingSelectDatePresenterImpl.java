package com.lanthanh.admin.icareapp.presentation.bookingpage;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingSelectDatePresenter;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.Calendar;

/**
 * Created by ADMIN on 25-Jan-17.
 */

public class BookingSelectDatePresenterImpl extends BasePresenter implements BookingSelectDatePresenter {
    private BookingSelectDatePresenter.View mView;
    private DTOAppointment dtoAppointment;
    private Calendar startDate, expireDate;

    public BookingSelectDatePresenterImpl(Executor executor, MainThread mainThread, View view, DTOAppointment dtoAppointment){
        mView= view;
        this.dtoAppointment = dtoAppointment;
    }


    @Override
    public void resume() {
        mView.dateDisplayOnTypeOrVoucherChange();
    }

    /*========================== DATE ==========================*/
    @Override
    public void onStartDatePickerClick() {
        mView.showStartDatePicker(Calendar.getInstance());
    }

    @Override
    public void onExpireDatePickerClick() {
        if (this.startDate == null)
            mView.showExpireDatePicker(Calendar.getInstance());
        else {
            Calendar minDate = Calendar.getInstance();
            minDate.setTime(this.startDate.getTime());
            minDate.add(Calendar.DATE, 1);
            mView.showExpireDatePicker(minDate);
        }
    }

    //Add start date when start date is selected from View
    @Override
    public void onStartDateSet(Calendar startDate) {
        if (dtoAppointment.getVoucherId() == 1){
            if (!ecoBookingDayCheck(startDate)){
                mView.showError(mView.getStringResource(R.string.booking_error_eco_date));
                return;
            }
        }
        //Because we only care about the date, set time (hour, minute, second, millisecond) to 0
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        //Get current date and do the same
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        /*Check new start date with current date
         *If new start date is before the current date, show error to user and return
         */
        if (startDate.compareTo(currentDate) >= 0){
            this.startDate = startDate;
        }else{
            mView.showError("Ngày được chọn không phù hợp");
            return;
        }

        /*If select start date successfully, check new start date with old expire date
         *If new start date is after old expire date, reset expire date
         */
        if (this.expireDate != null) {
            if (this.startDate.compareTo(this.expireDate) >= 0) {
                this.expireDate = null;
                mView.displayExpireDate(null);
            }
        }

        dtoAppointment.setStartDate(this.startDate.getTime());
        String date = ConverterForDisplay.convertDateToDisplay(this.startDate.getTime());
        mView.displayStartDate(date);
        mView.enableExpireDate();
    }

    //Add expire date when expire date is selected from View
    @Override
    public void onExpireDateSet(Calendar expireDate) {
        if (dtoAppointment.getVoucherId() == 1){
            if (!ecoBookingDayCheck(expireDate)){
                mView.showError(mView.getStringResource(R.string.booking_error_eco_date));
                return;
            }
        }

        //Because we only care about the date, set time (hour, minute, second, millisecond) to 0
        expireDate.set(Calendar.HOUR_OF_DAY, 0);
        expireDate.set(Calendar.MINUTE, 0);
        expireDate.set(Calendar.SECOND, 0);
        expireDate.set(Calendar.MILLISECOND, 0);

        /*Check new expire date with current start date
         *If start date is null (type = Tu Do), check with current date
         *If new expire date is before the current start date, show error to user and return
         */
        if (this.startDate == null){
            //Get current date and set time (hour, minute, second, millisecond) to 0
            Calendar currentDate = Calendar.getInstance();
            currentDate.set(Calendar.HOUR_OF_DAY, 0);
            currentDate.set(Calendar.MINUTE, 0);
            currentDate.set(Calendar.SECOND, 0);
            currentDate.set(Calendar.MILLISECOND, 0);
            if (expireDate.compareTo(currentDate) >= 0) {
                this.expireDate = expireDate;
            } else {
                mView.showError("Ngày được chọn không phù hợp");
                return;
            }
        }else {
            if (expireDate.compareTo(this.startDate) > 0) {
                this.expireDate = expireDate;
            } else {
                mView.showError("Ngày được chọn không phù hợp");
                return;
            }
        }

        dtoAppointment.setExpireDate(this.expireDate.getTime());
        String date = ConverterForDisplay.convertDateToDisplay(this.expireDate.getTime());
        mView.displayExpireDate(date);
    }

    @Override
    public void resetExpireDate() {
        this.startDate = null;
        dtoAppointment.setStartDate(null);
    }

    @Override
    public void resetStartDate() {
        this.expireDate = null;
        dtoAppointment.setExpireDate(null);
    }

    @Override
    public boolean ecoBookingDayCheck(Calendar c) {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return false;
        else
            return true;
    }

    @Override
    public boolean isAllInfoFilled() {
        return dtoAppointment.isDateSelectFilled();
    }
}
