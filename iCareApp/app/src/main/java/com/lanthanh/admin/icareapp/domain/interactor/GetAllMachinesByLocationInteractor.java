package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOMachine;

import java.util.List;

/**
 * Created by ADMIN on 04-Feb-17.
 */

public interface GetAllMachinesByLocationInteractor extends Interactor{
    interface Callback{
        void onNoMachineFound();
        void onAllMachinesReceive(List<DTOMachine> machineList);
    }
}
