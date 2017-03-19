package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class GetAllDistrictsByCityInteractorImpl extends AbstractInteractor implements GetAllDistrictsByCityInteractor{
    private GetAllDistrictsByCityInteractor.Callback mCallback;
    private DistrictManager mDistrictManager;
    private int mId;

    public GetAllDistrictsByCityInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, DistrictManager dm, int id){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mDistrictManager = dm;
        mId = id;
    }

    @Override
    public void run() {
        final List<DTODistrict> districtList = mDistrictManager.getAllDistrictsByCityId(mId);
        if (districtList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoDistrictFound();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllDistrictsReceive(districtList);
                }
            });
        }
    }
}
