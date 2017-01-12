package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateValidateAppointmentInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 13-Jan-17.
 */

public class UpdateValidateAppointmentInteractorImpl extends AbstractInteractor implements UpdateValidateAppointmentInteractor {
    private UpdateValidateAppointmentInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;

    public UpdateValidateAppointmentInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager){
        super(executor, mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
    }
    @Override
    public void run() {
        boolean result = mAppointmentManager.validateAppointment();
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                   mCallback.onValidateSuccess();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onValidateFail();
                }
            });
        }
    }
}
