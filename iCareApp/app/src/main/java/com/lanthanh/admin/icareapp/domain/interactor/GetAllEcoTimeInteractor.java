package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;

import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public interface GetAllEcoTimeInteractor extends Interactor{
    interface Callback{
        void onNoEcoTimeFound();
        void onEcoTimeFound(List<DTOTime> list);
    }
}
