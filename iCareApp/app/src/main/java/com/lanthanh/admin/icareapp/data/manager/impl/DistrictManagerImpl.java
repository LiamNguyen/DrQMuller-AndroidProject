package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;
import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;

import java.net.URL;
import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class DistrictManagerImpl extends AbstractManager implements DistrictManager {
    private JsonArray jsonArray;

    public DistrictManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTODistrict> getAllDistrictsByCityId(int id) {
        URL url = NetworkUtils.buildUrl(ModelURL.SELECT_DISTRICTS.getUrl(Manager.isUAT),
                                        NetworkUtils.getKeys(CityManager.CITY_ID_KEY),
                                        NetworkUtils.getValues(Integer.toString(id)));
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonObjectToObjectList(jsonArray, DTODistrict.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_Districts")) {
            jsonArray = jsonObject.get("Select_Districts").getAsJsonArray();
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        jsonArray = null;
    }
}
