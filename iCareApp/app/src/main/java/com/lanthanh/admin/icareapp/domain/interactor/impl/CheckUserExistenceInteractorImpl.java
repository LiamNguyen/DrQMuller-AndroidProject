package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.CheckUserExistenceInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class CheckUserExistenceInteractorImpl extends AbstractInteractor implements CheckUserExistenceInteractor {
    private CheckUserExistenceInteractor.Callback mCallback;
    private CustomerManager mCustomerManager;
    private String username, password;

    public CheckUserExistenceInteractorImpl(Executor executor, MainThread mainThread, Callback callback, CustomerManager customerManager, String username, String password){
        super(executor, mainThread);
        mCallback = callback;
        mCustomerManager = customerManager;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        boolean result = mCustomerManager.checkUserExistence(username);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUserExist();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUserNotExist(username, password);
                }
            });
        }
    }
}
