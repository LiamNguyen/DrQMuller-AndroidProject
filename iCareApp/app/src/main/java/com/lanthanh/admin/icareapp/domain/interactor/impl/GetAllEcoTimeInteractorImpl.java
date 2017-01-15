package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllEcoTimeInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public class GetAllEcoTimeInteractorImpl extends AbstractInteractor implements GetAllEcoTimeInteractor {
    private GetAllEcoTimeInteractor.Callback mCallback;
    private TimeManager mTimeManager;

    public GetAllEcoTimeInteractorImpl(Executor executor, MainThread mainThread, Callback callback, TimeManager timeManager){
        super(executor, mainThread);
        mCallback = callback;
        mTimeManager = timeManager;
    }

    @Override
    public void run() {
        final List<DTOTime> result = mTimeManager.getAllEcoTime();
        if (result != null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEcoTimeFound(result);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoEcoTimeFound();
                }
            });
        }
    }
}
