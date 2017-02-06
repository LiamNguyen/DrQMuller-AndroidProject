package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateAppointmentInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public class UpdateAppointmentInteractorImpl extends AbstractInteractor implements UpdateAppointmentInteractor {
    private UpdateAppointmentInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;
    private int cusId;
    private String verificationCode;

    public UpdateAppointmentInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager, int cusId, String verificationCode){
        super(executor,mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
        this.cusId = cusId;
        this.verificationCode = verificationCode;
    }

    @Override
    public void run() {
        boolean result = mAppointmentManager.updateAppointment(cusId, verificationCode);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUpdateAppointmentSuccess(verificationCode);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUpdateAppointmentFail();
                }
            });
        }
    }
}