package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.SendEmailNotifyBookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class SendEmailNotifyBookingInteractorImpl extends AbstractInteractor implements SendEmailNotifyBookingInteractor {
    private SendEmailNotifyBookingInteractor.Callback mCallback;
    private SendEmailManager mSendEmailManager;

    public SendEmailNotifyBookingInteractorImpl(Executor executor, MainThread mainThread, Callback callback, SendEmailManager sendEmailManager){
        super(executor, mainThread);
        mCallback = callback;
        mSendEmailManager = sendEmailManager;
    }

    @Override
    public void run() {

    }
}
