package com.lanthanh.admin.icareapp.data.manager;

/**
 * Created by ADMIN on 04-Jan-17.
 */

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;
import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface  CountryManager extends Manager {
    String COUNTRY_ID_KEY = "country_id";
    List<DTOCountry> getAllCountries();
}
