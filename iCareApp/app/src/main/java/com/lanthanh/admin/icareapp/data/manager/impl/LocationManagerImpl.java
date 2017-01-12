package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.converter.ConverterToUrlData;
import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class LocationManagerImpl extends AbstractManager implements LocationManager{
    private String json;

    public LocationManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOLocation> getAllLocationByDistrictId(int id) {
        String data = ConverterToUrlData.convertToUrlData(DistrictManager.DISTRICT_ID_KEY, Integer.toString(id));
        mApi.sendGetRequest(this, ModelURL.SELECT_LOCATIONS.getUrl(Manager.isUAT), data);
        return ConverterJsonToDTO.convertJsonToDTOLocation(json);
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        this.json = jsonObject.get("Select_Locations").getAsString();
    }
}
