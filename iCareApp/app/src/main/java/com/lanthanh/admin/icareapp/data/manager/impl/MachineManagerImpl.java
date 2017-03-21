package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.MachineManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOMachine;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by ADMIN on 04-Feb-17.
 */

public class MachineManagerImpl extends AbstractManager implements MachineManager {
    private JsonArray jsonArray;

    public MachineManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOMachine> getAllMachines(int id) {
        URL url = NetworkUtils.buildUrl(ModelURL.SELECT_MACHINES.getUrl(Manager.DB_TYPE),
                                        NetworkUtils.getKeys(LocationManager.LOCATION_ID_KEY),
                                        NetworkUtils.getValues(Integer.toString(id)));
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonToObjectList(jsonArray, DTOMachine.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_Machines")) {
            jsonArray = jsonObject.get("Select_Machines").getAsJsonArray();
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        jsonArray = null;
    }
}
