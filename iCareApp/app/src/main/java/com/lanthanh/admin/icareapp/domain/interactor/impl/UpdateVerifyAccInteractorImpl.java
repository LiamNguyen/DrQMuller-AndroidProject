package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 29-Jan-17.
 */

public class UpdateVerifyAccInteractorImpl extends AbstractInteractor implements UpdateVerifyAccInteractor {
    private UpdateVerifyAccInteractor.Callback mCallback;
    private CustomerManager mCustomerManager;
    private String id;

    public UpdateVerifyAccInteractorImpl(Executor executor, MainThread mainThread, Callback callback, CustomerManager customerManager, String id){
        super(executor, mainThread);
        mCallback = callback;
        mCustomerManager = customerManager;
        this.id = id;
    }
    @Override
    public void run() {
        boolean result = mCustomerManager.updateVerifyAcc(id);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUpdateVerifyAccSuccess();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUpdateVerifyAccFail();
                }
            });
        }
    }
}
