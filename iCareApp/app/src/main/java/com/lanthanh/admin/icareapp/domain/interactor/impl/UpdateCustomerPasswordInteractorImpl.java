package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public class UpdateCustomerPasswordInteractorImpl extends AbstractInteractor implements UpdateCustomerPasswordInteractor {
    private UpdateCustomerPasswordInteractor.Callback mCallback;
    private CustomerManager mCustomerManager;
    private String username, password;

    public UpdateCustomerPasswordInteractorImpl(Executor executor, MainThread mainThread, Callback callback, CustomerManager customerManager, String username, String password){
        super(executor, mainThread);
        mCallback = callback;
        mCustomerManager = customerManager;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        boolean result = mCustomerManager.updateCustomerPassword(username, password);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onResetPasswordSuccess();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onResetPasswordFail();
                }
            });
        }
    }
}
