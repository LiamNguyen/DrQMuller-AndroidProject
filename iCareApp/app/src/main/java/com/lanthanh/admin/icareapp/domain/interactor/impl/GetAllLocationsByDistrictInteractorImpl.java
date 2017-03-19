package com.lanthanh.admin.icareapp.domain.interactor.impl;



import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class GetAllLocationsByDistrictInteractorImpl extends AbstractInteractor implements GetAllLocationsByDistrictInteractor {
    private GetAllLocationsByDistrictInteractor.Callback mCallback;
    private LocationManager mLocationManager;
    private int mId;

    public GetAllLocationsByDistrictInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, LocationManager lm, int id){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mLocationManager = lm;
        mId = id;
    }

    @Override
    public void run() {
        final List<DTOLocation> locationList = mLocationManager.getAllLocationByDistrictId(mId);
        if (locationList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoLocationFound();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllLocationsReceive(locationList);
                }
            });
        }
    }
}
