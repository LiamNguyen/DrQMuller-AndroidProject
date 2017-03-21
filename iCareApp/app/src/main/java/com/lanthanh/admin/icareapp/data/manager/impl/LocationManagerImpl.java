package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;
import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.net.URL;
import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class LocationManagerImpl extends AbstractManager implements LocationManager{
    private JsonArray jsonArray;

    public LocationManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOLocation> getAllLocationByDistrictId(int id) {
        URL url = NetworkUtils.buildUrl(ModelURL.SELECT_LOCATIONS.getUrl(Manager.DB_TYPE),
                                        NetworkUtils.getKeys(DistrictManager.DISTRICT_ID_KEY),
                                        NetworkUtils.getValues(Integer.toString(id)));
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonToObjectList(jsonArray, DTOLocation.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_Locations")) {
            jsonArray = jsonObject.get("Select_Locations").getAsJsonArray();
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        jsonArray = null;
    }
}
