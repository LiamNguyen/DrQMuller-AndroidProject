package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOCity;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public class GetAllCitiesByCountryInteractorImpl extends AbstractInteractor implements GetAllCitiesByCountryInteractor {
    private GetAllCitiesByCountryInteractor.Callback mCallback;
    private CityManager mCityManager;
    private int mId;

    public GetAllCitiesByCountryInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, CityManager cm, int id){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mCityManager = cm;
        mId = id;
    }

    @Override
    public void run() {
        final List<DTOCity> cityList = mCityManager.getAllCitiesByCountryId(mId);
        if (cityList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoCityFound();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllCitiesReceive(cityList);
                }
            });
        }
    }
}
