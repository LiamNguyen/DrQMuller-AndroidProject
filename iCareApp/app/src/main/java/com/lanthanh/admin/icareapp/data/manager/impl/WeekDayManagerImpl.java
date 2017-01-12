package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOWeekDay;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public class WeekDayManagerImpl extends AbstractManager implements WeekDayManager{
    private String json;

    public WeekDayManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOWeekDay> getAllWeekDays() {
        mApi.sendPostRequest(this, ModelURL.SELECT_DAYSOFWEEK.getUrl(Manager.isUAT), "");
        return ConverterJsonToDTO.convertJsonToDTOTWeekDay(json);
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        this.json = jsonObject.get("Select_DaysOfWeek").getAsString();
    }
}
