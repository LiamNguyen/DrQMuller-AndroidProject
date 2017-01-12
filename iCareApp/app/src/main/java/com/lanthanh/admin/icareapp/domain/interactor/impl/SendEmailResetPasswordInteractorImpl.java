package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.SendEmailResetPasswordInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public class SendEmailResetPasswordInteractorImpl extends AbstractInteractor implements SendEmailResetPasswordInteractor {
    private SendEmailResetPasswordInteractor.Callback mCallback;
    private SendEmailManager mSendEmailManager;
    private String email, username;

    public SendEmailResetPasswordInteractorImpl(Executor executor, MainThread mainThread, Callback callback, SendEmailManager sendEmailManager, String email, String username){
        super(executor, mainThread);
        mCallback = callback;
        mSendEmailManager = sendEmailManager;
        this.email = email;
        this.username = username;
    }

    @Override
    public void run() {
        boolean result = mSendEmailManager.sendEmailResetPassword(email, username);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEmailSent();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEmailNotSent();
                }
            });
        }
    }
}
