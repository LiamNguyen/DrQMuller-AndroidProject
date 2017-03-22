package com.lanthanh.admin.icareapp.data.repository.datasource;

import android.content.SharedPreferences;

import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;

/**
 * @author longv
 *         Created on 22-Mar-17.
 */

public class LocalStorage {
    private SharedPreferences preferences;

    public LocalStorage(SharedPreferences preferences){
        this.preferences = preferences;
    }

    public void saveUserToLocal(UserInfo user) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", ConverterJson.convertObjectToJson(user));
        editor.apply();
        editor.commit();
    }

    public UserInfo getUserFromLocal() {
        return ConverterJson.convertJsonToObject(preferences.getString("user", ""), UserInfo.class);
    }
}
