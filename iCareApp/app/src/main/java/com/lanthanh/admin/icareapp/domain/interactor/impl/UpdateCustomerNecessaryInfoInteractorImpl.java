package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 12-Feb-17.
 */

public class UpdateCustomerNecessaryInfoInteractorImpl extends AbstractInteractor implements UpdateCustomerNecessaryInfo {
    private UpdateCustomerNecessaryInfo.Callback mCallback;
    private CustomerManager mCustomerManager;
    private ModelUser mUser;

    public UpdateCustomerNecessaryInfoInteractorImpl(Executor executor, MainThread mainThread, Callback callback, CustomerManager customerManager, ModelUser user){
        super(executor, mainThread);
        mCallback = callback;
        mCustomerManager = customerManager;
        mUser = user;
    }

    @Override
    public void run() {
        boolean result = mCustomerManager.updateCustomerNecessaryInfo(mUser);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUpdateNecessaryInfoSuccess();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onUpdateNecessaryInfoFail();
                }
            });
        }
    }
}
