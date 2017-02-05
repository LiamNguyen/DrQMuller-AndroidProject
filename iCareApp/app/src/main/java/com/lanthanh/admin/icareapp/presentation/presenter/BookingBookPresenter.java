package com.lanthanh.admin.icareapp.presentation.presenter;

import com.lanthanh.admin.icareapp.domain.model.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.base.BaseView;

import java.util.Date;
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
        void updateMachineList(List<String> list);
        void autoExpandGroup(int groupPosition);
    }

    void refreshAvailableDays();
    void refreshAvailableTime();
    List<String> getAvailableTime(List<String> list);
    void getAllWeekDays();
    void getAllTime();
    void getAllEcoTime();
    void getAllMachines();
    void getSelectedTime(String day);
    int  getDayId(String day);
    int  getTimeId(String time);
    int  getNumberOfCartItems();
    void onTimeSelected(String day, String time);
    boolean checkAppointmentScheduleValidity(int dayId);
    String getDayOfWeek(Date date);
    void onMachineSelected(String machine);
    DTOMachine getMachine(String machine);
    boolean isMachineFilled();
}
