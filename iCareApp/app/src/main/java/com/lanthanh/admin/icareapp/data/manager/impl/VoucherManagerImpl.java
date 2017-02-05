package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class VoucherManagerImpl extends AbstractManager implements VoucherManager {
    private JsonArray jsonArray;

    public VoucherManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOVoucher> getAllVouchers() {
        URL url = NetworkUtils.buildUrl(ModelURL.SELECT_VOUCHERS.getUrl(Manager.DB_TYPE), null, null);
        mApi.sendGetRequest(this, url);
        return ConverterJson.convertGsonObjectToObjectList(jsonArray, DTOVoucher.class);
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_Vouchers")) {
            jsonArray = jsonObject.get("Select_Vouchers").getAsJsonArray();
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        jsonArray = null;
    }
}
