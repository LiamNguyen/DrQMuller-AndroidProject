package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface GetAllDistrictsByCityInteractor extends Interactor {
    interface Callback{
        void onNoDistrictFound();
        void onAllDistrictsReceive(List<DTODistrict> districtList);
    }
}
