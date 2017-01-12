package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterJsonToDTO;
import com.lanthanh.admin.icareapp.data.manager.TypeManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOType;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class TypeManagerImpl extends AbstractManager implements TypeManager {
    private String json;

    public TypeManagerImpl(iCareApi api){
        super(api);
    }

    @Override
    public List<DTOType> getAllTypes() {
        mApi.sendPostRequest(this, ModelURL.SELECT_TYPES.getUrl(Manager.isUAT), "");
        return ConverterJsonToDTO.convertJsonToDTOType(json);
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        this.json = jsonObject.get("Select_Types").getAsString();
    }
}
