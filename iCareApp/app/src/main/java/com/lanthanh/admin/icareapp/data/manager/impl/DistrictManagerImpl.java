package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.converter.ConverterToUrlData;
import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class DistrictManagerImpl extends AbstractManager implements DistrictManager {
    private String json;

    public DistrictManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTODistrict> getAllDistrictsByCityId(int id) {
        String data = ConverterToUrlData.convertToUrlData(CityManager.CITY_ID_KEY, Integer.toString(id));
        mApi.sendGetRequest(this, ModelURL.SELECT_DISTRICTS.getUrl(Manager.isUAT), data);
        return ConverterJsonToDTO.convertJsonToDTODistrict(json);
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        this.json = jsonObject.get("Select_Districts").getAsString();
    }
}
