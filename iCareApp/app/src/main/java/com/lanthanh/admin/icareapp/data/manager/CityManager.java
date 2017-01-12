package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOCity;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public interface CityManager extends Manager {
    String CITY_ID_KEY = "city_id";
    List<DTOCity> getAllCitiesByCountryId(int id);
}
