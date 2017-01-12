package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public interface DistrictManager extends Manager{
    String DISTRICT_ID_KEY = "district_id";
    List<DTODistrict> getAllDistrictsByCityId(int id);
}
