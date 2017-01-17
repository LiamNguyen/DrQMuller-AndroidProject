package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.RemoveTemporaryBookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 09-Jan-17.
 */

public class RemoveTemporaryBookingInteractorImpl extends AbstractInteractor implements RemoveTemporaryBookingInteractor {
    private RemoveTemporaryBookingInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;
    private DTOAppointmentSchedule dtoAppointmentSchedule;
    private int dayId, timeId;

    public RemoveTemporaryBookingInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager, DTOAppointmentSchedule dtoAppointmentSchedule){
        super(executor, mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
        this.dayId = dtoAppointmentSchedule.getDayId();
        this.timeId = dtoAppointmentSchedule.getHourId();
        this.dtoAppointmentSchedule = dtoAppointmentSchedule;
    }

    @Override
    public void run() {
        boolean result = mAppointmentManager.removeTempBooking(dayId, timeId);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveTempBookingSuccess(dtoAppointmentSchedule);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveTempBookingFail();
                }
            });
        }
    }
}
