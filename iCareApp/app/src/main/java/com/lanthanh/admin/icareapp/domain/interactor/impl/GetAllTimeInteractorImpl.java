package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllTimeInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public class GetAllTimeInteractorImpl extends AbstractInteractor implements GetAllTimeInteractor{
    private GetAllTimeInteractor.Callback mCallback;
    private TimeManager mTimeManager;

    public GetAllTimeInteractorImpl(Executor executor, MainThread mainThread, Callback callback, TimeManager timeManager){
        super(executor, mainThread);
        mCallback = callback;
        mTimeManager = timeManager;
    }

    @Override
    public void run() {
        final List<DTOTime> timeList = mTimeManager.getAllTime();
        if (timeList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoTimeFound();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllTimeReceive(timeList);
                }
            });
        }
    }
}
