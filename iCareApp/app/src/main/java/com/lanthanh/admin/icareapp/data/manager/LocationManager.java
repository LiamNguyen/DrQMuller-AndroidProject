package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public interface LocationManager extends Manager{
    String LOCATION_ID_KEY = "location_id";
    String LOCATION_NAME_KEY = "location";
    List<DTOLocation> getAllLocationByDistrictId(int id);
}
