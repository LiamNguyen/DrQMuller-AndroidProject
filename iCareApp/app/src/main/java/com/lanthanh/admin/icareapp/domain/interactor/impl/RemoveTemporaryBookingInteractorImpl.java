package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 09-Jan-17.
 */

public class RemoveTemporaryBookingInteractorImpl extends AbstractInteractor implements RemoveTemporaryBookingInteractor {
    private RemoveTemporaryBookingInteractor.Callback mCallback;
    private AppointmentManager mAppointmentManager;
    private List<DTOAppointmentSchedule> list;
    private int locationId, machineId;

    public RemoveTemporaryBookingInteractorImpl(Executor executor, MainThread mainThread, Callback callback, AppointmentManager appointmentManager,
                                                int locationId, int machineId, DTOAppointmentSchedule... dtoAppointmentSchedule){
        super(executor, mainThread);
        mCallback = callback;
        mAppointmentManager = appointmentManager;
        this.locationId = locationId;
        this.machineId = machineId;
        list = new ArrayList<>();
        for (int i = 0; i < dtoAppointmentSchedule.length; i++){
            list.add(dtoAppointmentSchedule[i]);
        }
    }

    @Override
    public void run() {
        boolean result = mAppointmentManager.removeTempBooking(list, locationId, machineId);
        if (result){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveTempBookingSuccess(list);
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveTempBookingFail();
                }
            });
        }
    }
}
