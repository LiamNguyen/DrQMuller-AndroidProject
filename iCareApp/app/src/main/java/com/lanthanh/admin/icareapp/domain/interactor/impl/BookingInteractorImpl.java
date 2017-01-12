package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.BookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.BookingSelectPresenterImpl;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public class BookingInteractorImpl extends AbstractInteractor implements BookingInteractor{
    private BookingInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;
    private int dayId, timeId;

    public BookingInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager, int dayId, int timeId){
        super(executor, mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
        this.dayId = dayId;
        this.timeId = timeId;
    }

    @Override
    public void run() {
        boolean result = mAppointmentManager.insertTempBooking(dayId, timeId);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onBookingSuccess();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onBookingFail();
                }
            });
        }
    }
}
