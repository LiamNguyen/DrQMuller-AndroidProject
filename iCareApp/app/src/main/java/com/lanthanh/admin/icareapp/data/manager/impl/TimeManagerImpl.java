package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.MachineManager;
import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.net.URL;
import java.util.List;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public class TimeManagerImpl extends AbstractManager implements TimeManager{
    private JsonArray ecoTimeJsonArray, allTimeJsonArray, selectedTimeJsonArray;

    public TimeManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOTime> getAllTime() {
        URL url =  NetworkUtils.buildUrl(ModelURL.SELECT_ALLTIMEINADAY.getUrl(Manager.DB_TYPE), null, null);
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonObjectToObjectList(allTimeJsonArray, DTOTime.class);
    }

    @Override
    public List<DTOTime> getAllSelectedTime(int dayId, int locationId, int machineId) {
        URL url = NetworkUtils.buildUrl(ModelURL.SELECT_SELECTEDTIME.getUrl(Manager.DB_TYPE),
                                              NetworkUtils.getKeys(WeekDayManager.DAY_ID_KEY, LocationManager.LOCATION_ID_KEY, MachineManager.MACHINE_ID_KEY),
                                              NetworkUtils.getValues(Integer.toString(dayId), Integer.toString(locationId), Integer.toString(machineId)));
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonObjectToObjectList(selectedTimeJsonArray, DTOTime.class);
    }

    @Override
    public List<DTOTime> getAllEcoTime() {
        URL url =  NetworkUtils.buildUrl(ModelURL.SELECT_ECOTIME.getUrl(Manager.DB_TYPE), null, null);
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonObjectToObjectList(ecoTimeJsonArray, DTOTime.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_SelectedTime")){
            selectedTimeJsonArray = jsonObject.get("Select_SelectedTime").getAsJsonArray();
        }else if (jsonObject.has("Select_AllTime")){
            allTimeJsonArray = jsonObject.get("Select_AllTime").getAsJsonArray();
        }else if (jsonObject.has("Select_EcoTime")){
            ecoTimeJsonArray = jsonObject.get("Select_EcoTime").getAsJsonArray();
        }else{
            resetResult();
            return;
        }
    }

    @Override
    public void resetResult() {
        ecoTimeJsonArray = null;
        allTimeJsonArray = null;
        selectedTimeJsonArray = null;
    }
}
