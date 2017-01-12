package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface GetAllLocationsByDistrictInteractor extends Interactor {
    interface Callback{
        void onNoLocationFound();
        void onAllLocationsReceive(List<DTOLocation> locationList);
    }
}
