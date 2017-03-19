package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 29-Jan-17.
 */

public class SendEmailVerifyAccInteractorImpl extends AbstractInteractor implements SendEmailVerifyAccInteractor {
    private SendEmailVerifyAccInteractor.Callback mCallback;
    private SendEmailManager mSendEmailManager;
    private String email;
    private int id;

    public SendEmailVerifyAccInteractorImpl(Executor executor, MainThread mainThread, Callback callback, SendEmailManager sendEmailManager, String email, int id){
        super(executor, mainThread);
        mCallback = callback;
        mSendEmailManager = sendEmailManager;
        this.email = email;
        this.id = id;
    }

    @Override
    public void run() {
        int result = mSendEmailManager.sendEmailVerifyAcc(email, id);
        if (result == SendEmailManager.STATUS_SENT){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEmailVerifyAccSent();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEmailVerifyAccNotSent();
                }
            });
        }
    }
}
