package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOWeekDay;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public class WeekDayManagerImpl extends AbstractManager implements WeekDayManager{
    private JsonArray jsonArray;

    public WeekDayManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOWeekDay> getAllWeekDays() {
        URL url = NetworkUtils.buildUrl(ModelURL.SELECT_DAYSOFWEEK.getUrl(Manager.DB_TYPE), null, null);
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonToObjectList(jsonArray, DTOWeekDay.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_DaysOfWeek")) {
            jsonArray = jsonObject.get("Select_DaysOfWeek").getAsJsonArray();
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        jsonArray = null;
    }
}
