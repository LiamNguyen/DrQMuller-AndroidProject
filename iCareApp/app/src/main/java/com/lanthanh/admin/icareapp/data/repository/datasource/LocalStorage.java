package com.lanthanh.admin.icareapp.data.repository.datasource;

import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.lanthanh.admin.icareapp.utils.converter.ConverterJson;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void removeUserFromLocal() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user");
        editor.apply();
        editor.commit();
    }

    public List<DTOAppointment> getAppointmentsFromLocal() {
        String userId = getUserFromLocal().getId();
        Type mapType = new TypeToken<Map<String ,String>>(){}.getType();
        Type listType = new TypeToken<List<DTOAppointment>>(){}.getType();
        Map<String, String> appointmentDB = ConverterJson.convertJsonToObject(preferences.getString("appointmentDB", ""), mapType);
        if (appointmentDB == null)
            appointmentDB = new HashMap<>();
        for (String i : appointmentDB.keySet()){
            if (i.equals(userId))
                return ConverterJson.convertJsonToObject(appointmentDB.get(i), listType);
        }
        return new ArrayList<>();
    }

    public void saveAppointmentsToLocal(List<DTOAppointment> appointments) {
        String userId = getUserFromLocal().getId();
        Type mapType = new TypeToken<Map<String ,String>>(){}.getType();
        SharedPreferences.Editor editor = preferences.edit();
        Map<String, String> appointmentDB = ConverterJson.convertJsonToObject(preferences.getString("appointmentDB", ""), mapType);
        if (appointmentDB == null)
            appointmentDB = new HashMap<>();
        if (appointments == null || appointments.size() == 0)
            appointmentDB.remove(userId);
        else
            appointmentDB.put(userId, ConverterJson.convertObjectToJson(appointments));
        editor.putString("appointmentDB", ConverterJson.convertObjectToJson(appointmentDB));
        editor.apply();
        editor.commit();
    }
}
