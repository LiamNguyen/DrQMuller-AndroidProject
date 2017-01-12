package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOWeekDay;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public interface GetAllWeekDaysInteractor extends Interactor{
    interface Callback{
        void onNoWeekDayFound();
        void onAllWeekDaysReceive(List<DTOWeekDay> weekDayList);
    }
}
