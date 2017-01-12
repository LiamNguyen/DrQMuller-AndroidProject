package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllWeekDaysInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOWeekDay;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class GetAllWeekDaysInteractorImpl extends AbstractInteractor implements GetAllWeekDaysInteractor {
    private GetAllWeekDaysInteractor.Callback mCallback;
    private WeekDayManager mWeekDayManager;

    public GetAllWeekDaysInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, WeekDayManager wdm){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mWeekDayManager = wdm;
    }

    @Override
    public void run() {
        final List<DTOWeekDay> weekDayList = mWeekDayManager.getAllWeekDays();
        if (weekDayList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoWeekDayFound();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllWeekDaysReceive(weekDayList
                    );
                }
            });
        }
    }
}
