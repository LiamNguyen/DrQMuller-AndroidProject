package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.RemoveTemporaryBookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 09-Jan-17.
 */

public class RemoveTemporaryBookingInteractorImpl extends AbstractInteractor implements RemoveTemporaryBookingInteractor {
    private RemoveTemporaryBookingInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;
    private int dayId, timeId;

    public RemoveTemporaryBookingInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager, int dayId, int timeId){
        super(executor, mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
        this.dayId = dayId;
        this.timeId = dayId;
    }

    @Override
    public void run() {
        boolean result = mAppointmentManager.removeTempBooking(dayId, timeId);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveSuccess();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveFail();
                }
            });
        }
    }
}
