package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.manager.CountryManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;


import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public class CountryManagerImpl extends AbstractManager implements CountryManager{
    private String json;

    public CountryManagerImpl(iCareApi api) {
        super(api);
    }

    @Override
    public List<DTOCountry> getAllCountries() {
        mApi.sendPostRequest(this, ModelURL.SELECT_COUNTRIES.getUrl(Manager.isUAT), "");
        return ConverterJsonToDTO.convertJsonToDTOCountry(json);
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        this.json = jsonObject.get("Select_Countries").getAsString();
    }
}
