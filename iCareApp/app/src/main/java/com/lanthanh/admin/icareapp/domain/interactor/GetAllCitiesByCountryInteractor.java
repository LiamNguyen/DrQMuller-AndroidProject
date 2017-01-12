package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOCity;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface GetAllCitiesByCountryInteractor extends Interactor {
    interface Callback{
        void onNoCityFound();
        void onAllCitiesReceive(List<DTOCity> cityList);
    }
}
