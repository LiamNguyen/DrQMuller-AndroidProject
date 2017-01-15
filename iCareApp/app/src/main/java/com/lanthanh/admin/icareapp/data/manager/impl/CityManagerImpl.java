package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.converter.ConverterToUrlData;
import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.data.manager.CountryManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOCity;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class CityManagerImpl extends AbstractManager implements CityManager{
    private JsonArray jsonArray;

    public CityManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOCity> getAllCitiesByCountryId(int id) {
        String data = ConverterToUrlData.convertToUrlData(CountryManager.COUNTRY_ID_KEY, Integer.toString(id));
        mApi.sendPostRequest(this, ModelURL.SELECT_CITIES.getUrl(Manager.isUAT), data);
        return ConverterJson.convertGsonObjectToObjectList(jsonArray, DTOCity.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_Cities")) {
            jsonArray = jsonObject.get("Select_Cities").getAsJsonArray();
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        jsonArray = null;
    }
}
