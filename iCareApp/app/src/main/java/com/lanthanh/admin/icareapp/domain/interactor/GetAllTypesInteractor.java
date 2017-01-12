package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOType;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface GetAllTypesInteractor extends Interactor {
    interface Callback{
        void onNoTypeFound();
        void onAllTypesReceive(List<DTOType> typeList);
    }
}
