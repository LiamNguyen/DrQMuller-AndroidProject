package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.GetCustomerIdInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class GetCustomerIdInteractorImpl extends AbstractInteractor implements GetCustomerIdInteractor {
    private GetCustomerIdInteractor.Callback mCallback;
    private CustomerManager mCustomerManager;
    private String username;

    public GetCustomerIdInteractorImpl(Executor executor, MainThread mainThread, Callback callback, CustomerManager customerManager, String username){
        super(executor, mainThread);
        mCallback = callback;
        mCustomerManager = customerManager;
        this.username = username;
    }

    @Override
    public void run() {
        final int id = mCustomerManager.getCustomerId(username);
        if (id != 0){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onCustomerIdFound(id);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onCustomerIdNotFound();
                }
            });
        }
    }
}
