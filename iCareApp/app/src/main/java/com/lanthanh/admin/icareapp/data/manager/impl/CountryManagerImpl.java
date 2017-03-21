package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.manager.CountryManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;


import java.net.URL;
import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public class CountryManagerImpl extends AbstractManager implements CountryManager{
    private JsonArray jsonArray;

    public CountryManagerImpl(iCareApi api) {
        super(api);
    }

    @Override
    public List<DTOCountry> getAllCountries() {
        URL url = NetworkUtils.buildUrl(ModelURL.SELECT_COUNTRIES.getUrl(Manager.DB_TYPE), null, null);
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonToObjectList(jsonArray, DTOCountry.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_Countries")) {
            jsonArray = jsonObject.get("Select_Countries").getAsJsonArray();
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        jsonArray = null;
    }
}
