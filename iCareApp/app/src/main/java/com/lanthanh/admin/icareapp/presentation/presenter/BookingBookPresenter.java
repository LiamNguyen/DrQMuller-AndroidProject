package com.lanthanh.admin.icareapp.presentation.presenter;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.BaseView;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public interface BookingBookPresenter extends Presenter{
    interface View extends BaseView {
        void setAvailableDay(List<String> list);
        void setAvailableTime(List<String> list);
        String getResourceString(int id);
        void updateCart();
    }

    void refreshAvailableDays();
    void refreshAvailableTime();
    List<String> getAvailableTime(List<String> list);
    void getAllWeekDays(WeekDayManager weekDayManager);
    void getAllTime(TimeManager timeManager);
    void getAllEcoTime(TimeManager timeManager);
    void getSelectedTime(String day, TimeManager timeManager);
    int  getDayId(String day);
    int  getTimeId(String time);
    int  getNumberOfCartItems();
    void onTimeSelected(String day, String time, AppointmentManager appointmentManager);
    boolean checkAppointmentScheduleValidity(int dayId);
}
