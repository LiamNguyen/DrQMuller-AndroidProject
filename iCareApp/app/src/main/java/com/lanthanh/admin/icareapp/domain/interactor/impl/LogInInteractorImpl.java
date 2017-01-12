package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.LogInInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class LogInInteractorImpl extends AbstractInteractor implements LogInInteractor {
    private LogInInteractor.Callback mCallback;
    private CustomerManager mCustomerManager;
    private String username, password;

    public LogInInteractorImpl(Executor executor, MainThread mainThread, Callback callback, CustomerManager customerManager, String username, String password){
        super(executor, mainThread);
        mCallback = callback;
        mCustomerManager = customerManager;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        final String result = mCustomerManager.logIn(username, password);
        if (result != null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLogInSuccess(result, username);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLogInFail();
                }
            });
        }
    }
}
