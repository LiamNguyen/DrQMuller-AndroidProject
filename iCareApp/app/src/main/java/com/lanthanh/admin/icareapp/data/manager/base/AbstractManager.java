package com.lanthanh.admin.icareapp.data.manager.base;

import com.google.gson.JsonElement;
import com.lanthanh.admin.icareapp.api.iCareApi;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public abstract class AbstractManager implements Manager, iCareApi.Callback{
    protected iCareApi mApi;

    public AbstractManager(iCareApi api){
        mApi = api;
    }

    @Override
    public boolean insert(Object o) {
        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean get(Object o) {
        return false;
    }

    public abstract void onResponse(String json);

    public abstract void resetResult();
}
