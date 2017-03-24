package com.lanthanh.admin.icareapp.presentation.application;


import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author longv
 *         Created on 23-Mar-17.
 */

public class ApplicationProvider {
    private List<DTOCountry> countries;
    private List<DTOCity> cities;
    private List<DTODistrict> districts;
    private List<DTOLocation> locations;
    private List<DTOVoucher> vouchers;
    private List<DTOType> types;
    private List<DTOMachine> machines;
    private List<DTOWeekDay> weekDays;
    private List<DTOTime> allTime;
    private List<DTOTime> ecoTime;
    private List<DTOTime> selectedTime;
    private List<DTOAppointment> appointments;
    private DTOAppointment currentAppointment;
    private SharedPreferences sharedPreferences;
    private UserInfo user;

    public ApplicationProvider(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public UserInfo getUser() {
        return ConverterJson.convertJsonToObject(sharedPreferences.getString("user", ""), UserInfo.class);
    }

    public void saveUserToLocal(UserInfo user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", ConverterJson.convertObjectToJson(user, UserInfo.class));
        editor.apply();
        editor.commit();
    }

    public List<DTOAppointment> getAppointments() {
        Type mapType = new TypeToken<Map<String ,String>>(){}.getType();
        Type listType = new TypeToken<List<DTOAppointment>>(){}.getType();
        Map<String, String> appointmentDB = ConverterJson.convertJsonToObject(sharedPreferences.getString("appointmentDB", ""), mapType);
        if (appointmentDB == null)
            appointmentDB = new HashMap<>();
        for (String i : appointmentDB.keySet()){
            if (i.equals(getUser().getId()))
                return ConverterJson.convertJsonToObject(appointmentDB.get(i), listType);
        }
        return null;
    }

    public void saveAppointmentsToLocal(SharedPreferences sharedPreferences, List<DTOAppointment> appointments) {
        Type mapType = new TypeToken<Map<String ,String>>(){}.getType();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<String, String> appointmentDB = ConverterJson.convertJsonToObject(sharedPreferences.getString("appointmentDB", ""), mapType);
        if (appointmentDB == null)
            appointmentDB = new HashMap<>();
        if (appointments == null || appointments.size() == 0)
            appointmentDB.remove(getUser().getId());
        else
            appointmentDB.put(getUser().getId(), ConverterJson.convertObjectToJson(appointments));
        editor.putString("appointmentDB", ConverterJson.convertObjectToJson(appointmentDB));
        editor.apply();
        editor.commit();
    }

    public List<DTOCountry> getCountries() {
        if (countries == null) {
            countries = new ArrayList<>();
        }
        return countries;
    }

    public void setCountries(List<DTOCountry> countries) {
        this.countries = countries;
    }

    public List<DTOCity> getCities() {
        if (cities == null) {
            cities = new ArrayList<>();
        }
        return cities;
    }

    public void setCities(List<DTOCity> cities) {
        this.cities = cities;
    }

    public List<DTODistrict> getDistricts() {
        if (districts == null) {
            districts = new ArrayList<>();
        }
        return districts;
    }

    public void setDistricts(List<DTODistrict> districts) {
        this.districts = districts;
    }

    public List<DTOLocation> getLocations() {
        if (locations == null) {
            locations = new ArrayList<>();
        }
        return locations;
    }

    public void setLocations(List<DTOLocation> locations) {
        this.locations = locations;
    }

    public List<DTOVoucher> getVouchers() {
        if (vouchers == null) {
            vouchers = new ArrayList<>();
        }
        return vouchers;
    }

    public void setVouchers(List<DTOVoucher> vouchers) {
        this.vouchers = vouchers;
    }

    public List<DTOType> getTypes() {
        if (types == null) {
            types = new ArrayList<>();
        }
        return types;
    }

    public void setTypes(List<DTOType> types) {
        this.types = types;
    }

    public List<DTOMachine> getMachines() {
        if (machines == null) {
            machines = new ArrayList<>();
        }
        return machines;
    }

    public void setMachines(List<DTOMachine> machines) {
        this.machines = machines;
    }

    public List<DTOWeekDay> getWeekDays() {
        if (weekDays == null) {
            weekDays = new ArrayList<>();
        }
        return weekDays;
    }

    public void setWeekDays(List<DTOWeekDay> weekDays) {
        this.weekDays = weekDays;
    }

    public List<DTOTime> getAllTime() {
        if (allTime == null) {
            allTime = new ArrayList<>();
        }
        return allTime;
    }

    public void setAllTime(List<DTOTime> allTime) {
        this.allTime = allTime;
    }

    public List<DTOTime> getEcoTime() {
        if (ecoTime == null) {
            ecoTime = new ArrayList<>();
        }
        return ecoTime;
    }

    public void setEcoTime(List<DTOTime> ecoTime) {
        this.ecoTime = ecoTime;
    }

    public List<DTOTime> getSelectedTime() {
        if (selectedTime == null) {
            selectedTime = new ArrayList<>();
        }
        return selectedTime;
    }

    public void setSelectedTime(List<DTOTime> selectedTime) {
        this.selectedTime = selectedTime;
    }

    public DTOAppointment getCurrentAppointment() {
        if (currentAppointment == null) {
            currentAppointment = new DTOAppointment();
            currentAppointment.setUser(getUser());
        }
        return currentAppointment;
    }

    public void setCurrentAppointment(DTOAppointment currentAppointment) {
        this.currentAppointment = currentAppointment;
    }


}
