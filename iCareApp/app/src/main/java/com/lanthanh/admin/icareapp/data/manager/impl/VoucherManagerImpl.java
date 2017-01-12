package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class VoucherManagerImpl extends AbstractManager implements VoucherManager {
    private String json;

    public VoucherManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOVoucher> getAllVouchers() {
        mApi.sendPostRequest(this, ModelURL.SELECT_VOUCHERS.getUrl(Manager.isUAT), "");
        return ConverterJsonToDTO.convertJsonToDTOVoucher(json);
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        this.json = jsonObject.get("Select_Vouchers").getAsString();
    }
}
