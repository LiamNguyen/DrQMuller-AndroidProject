package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class GetAllVouchersInteractorImpl extends AbstractInteractor implements GetAllVouchersInteractor{
    private GetAllVouchersInteractor.Callback mCallback;
    private VoucherManager mVoucherManager;

    public GetAllVouchersInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, VoucherManager vm){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mVoucherManager = vm;
    }

    @Override
    public void run() {
        final List<DTOVoucher> voucherList = mVoucherManager.getAllVouchers();
        if (voucherList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoVoucherFound();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllVouchersReceive(voucherList);
                }
            });
        }
    }
}
