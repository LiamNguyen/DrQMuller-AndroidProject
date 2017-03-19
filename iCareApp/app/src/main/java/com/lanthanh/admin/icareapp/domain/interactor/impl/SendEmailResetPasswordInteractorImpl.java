package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public class SendEmailResetPasswordInteractorImpl extends AbstractInteractor implements SendEmailResetPasswordInteractor {
    private SendEmailResetPasswordInteractor.Callback mCallback;
    private SendEmailManager mSendEmailManager;
    private String username;

    public SendEmailResetPasswordInteractorImpl(Executor executor, MainThread mainThread, Callback callback, SendEmailManager sendEmailManager, String username){
        super(executor, mainThread);
        mCallback = callback;
        mSendEmailManager = sendEmailManager;
        this.username = username;
    }

    @Override
    public void run() {
        int result = mSendEmailManager.sendEmailResetPassword(username);
        if (result == SendEmailManager.STATUS_SENT){
            mMainThread.post(new Runnable() {
                @Override
                public void run(){
                    mCallback.onEmailResetPasswordSent();
                }
            });
        }else if (result == SendEmailManager.STATUS_USERNAMEOREMAIL_NOTFOUND){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUsernameOrEmailNotFound();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEmailResetPasswordNotSent();
                }
            });
        }
    }
}
