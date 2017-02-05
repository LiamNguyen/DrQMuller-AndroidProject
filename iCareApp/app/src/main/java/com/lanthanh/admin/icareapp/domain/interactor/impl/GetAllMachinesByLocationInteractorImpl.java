package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.data.manager.MachineManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllMachinesByLocationInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOMachine;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.List;

/**
 * Created by ADMIN on 04-Feb-17.
 */

public class GetAllMachinesByLocationInteractorImpl extends AbstractInteractor implements GetAllMachinesByLocationInteractor {
    private GetAllMachinesByLocationInteractor.Callback mCallback;
    private MachineManager machineManager;
    private int locationId;

    public GetAllMachinesByLocationInteractorImpl(Executor executor, MainThread mainThread, Callback callback, MachineManager machineManager, int locationId){
        super(executor, mainThread);
        mCallback = callback;
        this.machineManager = machineManager;
        this.locationId = locationId;
    }

    @Override
    public void run() {
        final List<DTOMachine> machineList = machineManager.getAllMachines(locationId);
        if (machineList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoMachineFound();
                }
            });
        }else{
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllMachinesReceive(machineList);
                }
            });
        }
    }
}
