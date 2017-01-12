package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.TypeManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOType;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllTypesInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class GetAllTypesInteractorImpl extends AbstractInteractor implements GetAllTypesInteractor {
    private GetAllTypesInteractor.Callback mCallback;
    private TypeManager mTypeManager;

    public GetAllTypesInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, TypeManager tm){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mTypeManager = tm;
    }

    @Override
    public void run() {
        final List<DTOType> typeList = mTypeManager.getAllTypes();
        if (typeList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoTypeFound();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllTypesReceive(typeList);
                }
            });
        }
    }
}
