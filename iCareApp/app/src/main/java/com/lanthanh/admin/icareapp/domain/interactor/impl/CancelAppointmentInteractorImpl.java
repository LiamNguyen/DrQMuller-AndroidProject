package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.CancelAppointmentInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 12-Feb-17.
 */

public class CancelAppointmentInteractorImpl extends AbstractInteractor implements CancelAppointmentInteractor {
    private CancelAppointmentInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;
    private int appointmentId;

    public CancelAppointmentInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager, int appointmentId){
        super(executor, mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
        this.appointmentId = appointmentId;
    }

    @Override
    public void run() {
        boolean result = mAppointmentManager.cancelAppointment(appointmentId);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onCancelAppointmentSuccess(appointmentId);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onCancelAppointmentFail();
                }
            });
        }
    }
}
