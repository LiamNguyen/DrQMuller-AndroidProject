package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.converter.ConverterToUrlData;
import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.util.List;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public class TimeManagerImpl extends AbstractManager implements TimeManager{
    private String json;

    public TimeManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOTime> getAllTime() {
        mApi.sendPostRequest(this, ModelURL.SELECT_ALLTIMEINADAY.getUrl(Manager.isUAT), "");
        return ConverterJsonToDTO.convertJsonToDTOTime(json);
    }

    @Override
    public List<DTOTime> getAllSelectedTime(int id) {
        String data = ConverterToUrlData.convertToUrlData(WeekDayManager.DAY_ID_KEY, Integer.toString(id));
        mApi.sendPostRequest(this, ModelURL.SELECT_SELECTEDTIME.getUrl(Manager.isUAT), data);
        return ConverterJsonToDTO.convertJsonToDTOTime(json);
    }

    @Override
    public List<DTOTime> getAllEcoTime() {
        mApi.sendPostRequest(this, ModelURL.SELECT_ECOTIME.getUrl(Manager.isUAT), "");
        return ConverterJsonToDTO.convertJsonToDTOTime(json);
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        if (jsonObject.has("Select_SelectedTime")){
            this.json = jsonObject.get("Select_SelectedTime").getAsString();
        }else if (jsonObject.has("Select_AllTime")){
            this.json = jsonObject.get("Select_AllTime").getAsString();
        }else if (jsonObject.has("Select_EcoTime")){
            this.json = jsonObject.get("Select_EcoTime").getAsString();
        }
    }
}
