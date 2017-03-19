package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public class InsertAppointmentInteractorImpl extends AbstractInteractor implements InsertAppointmentInteractor{
    private InsertAppointmentInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;
    private DTOAppointment dtoAppointment;

    public InsertAppointmentInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager, DTOAppointment dtoAppointment){
        super(executor, mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
        this.dtoAppointment = dtoAppointment;
    }

    @Override
    public void run() {
        final int result = mAppointmentManager.insertAppointment(dtoAppointment);
        if (result != 0){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onInsertAppointmentSuccess(result);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onInsertAppointmentFail();
                }
            });
        }
    }
}
