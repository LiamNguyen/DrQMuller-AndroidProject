package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;

import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public interface GetAllTimeInteractor extends Interactor{
    interface Callback{
        void onNoTimeFound();
        void onAllTimeReceive(List<DTOTime> timeList);
    }
}
