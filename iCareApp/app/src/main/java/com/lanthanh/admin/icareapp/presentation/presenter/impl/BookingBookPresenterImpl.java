package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.BookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllEcoTimeInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllSelectedTimeInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllTimeInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllWeekDaysInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.BookingInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllEcoTimeInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllSelectedTimeInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllTimeInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllWeekDaysInteractorImpl;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;
import com.lanthanh.admin.icareapp.domain.model.DTOWeekDay;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingBookPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class BookingBookPresenterImpl extends AbstractPresenter implements BookingBookPresenter,
        GetAllTimeInteractor.Callback, GetAllSelectedTimeInteractor.Callback, GetAllWeekDaysInteractor.Callback,
        GetAllEcoTimeInteractor.Callback, BookingInteractor.Callback{
    private BookingBookPresenter.View mView;
    private DTOAppointment dtoAppointment;
    private List<DTOTime> timeList, ecoTimeList, selectedTimeList;
    private List<DTOWeekDay> weekDayList;
    private List<String> vipDays, ecoDays, vipTime, ecoTime, selectedTime;
            ;

    public BookingBookPresenterImpl(Executor executor, MainThread mainThread, View view, DTOAppointment dtoAppointment) {
        super(executor, mainThread);
        mView = view;
        this.dtoAppointment = dtoAppointment;
        vipDays = new ArrayList<>();
        ecoDays = new ArrayList<>();
        vipTime = new ArrayList<>();
        ecoTime = new ArrayList<>();
        selectedTime = new ArrayList<>();
    }

    @Override
    public void resume() {
        refreshAvailableDays();
        refreshAvailableTime();
    }

    @Override
    public int getNumberOfCartItems() {
        return dtoAppointment.getAppointmentScheduleList().size();
    }

    @Override
    public void refreshAvailableDays() {
        if (dtoAppointment.getVoucherId() == 1)
            mView.setAvailableDay(ecoDays);
        else
            mView.setAvailableDay(vipDays);
    }

    @Override
    public void refreshAvailableTime() {
        if (dtoAppointment.getVoucherId() == 1)
            mView.setAvailableTime(getAvailableTime(ecoTime));
        else
            mView.setAvailableTime(getAvailableTime(vipTime));
    }

    /*========================= TIME =========================*/
    @Override
    public void getAllTime(TimeManager timeManager) {
        GetAllTimeInteractor getAllTimeInteractor = new GetAllTimeInteractorImpl(mExecutor, mMainThread, this, timeManager);
        getAllTimeInteractor.execute();
    }

    @Override
    public int getTimeId(String time) {
        for (DTOTime dtoTime: timeList){
            if (dtoTime.getTimeName().equals(time))
                return dtoTime.getId();
        }
        return -1;
    }

    @Override
    public void onAllTimeReceive(List<DTOTime> timeList) {
        this.timeList = timeList;
        vipTime = ConverterForDisplay.convertToStringList(timeList);
    }

    @Override
    public void onNoTimeFound() {
        mView.showError("No time found");
    }

    @Override
    public List<String> getAvailableTime(List<String> list) {
        List<String> result = new ArrayList<>();
        for (String s: selectedTime){
            if (!list.contains(s))
                result.add(s);
        }
        return result;
    }

    /*========================= ECO TIME =========================*/
    @Override
    public void getAllEcoTime(TimeManager timeManager) {
        GetAllEcoTimeInteractor getAllEcoTimeInteractor = new GetAllEcoTimeInteractorImpl(mExecutor, mMainThread, this, timeManager);
        getAllEcoTimeInteractor.execute();
    }

    @Override
    public void onEcoTimeFound(List<DTOTime> ecoTimeList) {
        this.ecoTimeList = ecoTimeList;
        ecoTime = ConverterForDisplay.convertToStringList(ecoTimeList);
    }

    @Override
    public void onNoEcoTimeFound() {
        mView.showError("No eco time found");
    }

    /*========================= WEEK DAY =========================*/
    @Override
    public void getAllWeekDays(WeekDayManager weekDayManager) {
        GetAllWeekDaysInteractor getAllWeekDaysInteractor = new GetAllWeekDaysInteractorImpl(mExecutor, mMainThread, this, weekDayManager);
        getAllWeekDaysInteractor.execute();
    }

    @Override
    public void onAllWeekDaysReceive(List<DTOWeekDay> weekDayList) {
        this.weekDayList = weekDayList;
        //Get week days for VIP voucher
        vipDays = ConverterForDisplay.convertToStringList(weekDayList);
        //Get week days for ECO voucher
        ecoDays.addAll(vipDays);
        ecoDays.remove(ecoDays.size() - 1);//Sunday
        ecoDays.remove(ecoDays.size() - 2);//Saturday
        //Update
        refreshAvailableDays();
    }

    @Override
    public int getDayId(String day) {
        for (DTOWeekDay dtoWeekDay: weekDayList){
            if (dtoWeekDay.getDayName().equals(day))
                return dtoWeekDay.getId();
        }
        return -1;
    }

    @Override
    public void onNoWeekDayFound() {
        mView.showError("No week day found");
    }

    /*========================= SELECTED TIME =========================*/
    @Override
    public void getSelectedTime(String day, TimeManager timeManager) {
        int id = getDayId(day);
        if (id == -1){
            onError("No id found for this day");
            return;
        }
        GetAllSelectedTimeInteractor getAllSelectedTimeInteractor = new GetAllSelectedTimeInteractorImpl(mExecutor, mMainThread, this, timeManager, id);
        getAllSelectedTimeInteractor.execute();
    }

    @Override
    public void onSelectedTimeReceive(List<DTOTime> selectedTimeList) {
        this.selectedTimeList = selectedTimeList;
        selectedTime = ConverterForDisplay.convertToStringList(selectedTimeList);
        refreshAvailableTime();
    }

    @Override
    public void onNoSelectedTimeFound() {
        mView.showError("No selected time found");
    }

    /*========================= BOOKING =========================*/
    @Override
    public void onTimeSelected(String day, String time, AppointmentManager appointmentManager) {
        int dayId = getDayId(day);
        int timeId = getTimeId(time);
        if (dayId == -1 || timeId == -1){
            onError("No id found for day or time for appointment schedule");
            return;
        }
        if (checkAppointmentScheduleValidity(dayId)) {
            DTOAppointmentSchedule appointmentSchedule = new DTOAppointmentSchedule();
            appointmentSchedule.setDayName(day);
            appointmentSchedule.setDayId(dayId);
            appointmentSchedule.setHourName(time);
            appointmentSchedule.setHourId(timeId);
            dtoAppointment.addAppointment(appointmentSchedule);
            BookingInteractor bookingInteractor = new BookingInteractorImpl(mExecutor, mMainThread, this, appointmentManager, dayId, timeId);
            bookingInteractor.execute();
        }else
            mView.showError(mView.getResourceString(R.string.selected_day));
    }

    @Override
    public boolean checkAppointmentScheduleValidity(int dayId) {
        for (DTOAppointmentSchedule dtoAppointmentSchedule: dtoAppointment.getAppointmentScheduleList()){
            if (dtoAppointmentSchedule.getDayId() == dayId)
                return false;
        }
        return true;
    }

    @Override
    public void onBookingFail() {
        mView.showError(mView.getResourceString(R.string.already_booked));
    }

    @Override
    public void onBookingSuccess() {
        mView.updateCart();
    }
}
