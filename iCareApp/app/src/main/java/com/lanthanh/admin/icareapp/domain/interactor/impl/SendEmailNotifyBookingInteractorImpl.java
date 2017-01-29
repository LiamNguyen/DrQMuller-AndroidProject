package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.SendEmailNotifyBookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class SendEmailNotifyBookingInteractorImpl extends AbstractInteractor implements SendEmailNotifyBookingInteractor {
    private SendEmailNotifyBookingInteractor.Callback mCallback;
    private SendEmailManager mSendEmailManager;
    private DTOAppointment dtoAppointment;

    public SendEmailNotifyBookingInteractorImpl(Executor executor, MainThread mainThread, Callback callback, SendEmailManager sendEmailManager, DTOAppointment dtoAppointment){
        super(executor, mainThread);
        mCallback = callback;
        mSendEmailManager = sendEmailManager;
        this.dtoAppointment = dtoAppointment;
    }

    @Override
    public void run() {
        int result = mSendEmailManager.sendEmailNotifyBooking(dtoAppointment);
        if (result == SendEmailManager.STATUS_SENT){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEmailNotifyBookingSent();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEmailNotifyBookingNotSent();
                }
            });
        }
    }
}
