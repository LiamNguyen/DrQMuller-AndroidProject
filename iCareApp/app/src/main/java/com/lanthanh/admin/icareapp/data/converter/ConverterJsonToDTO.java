package com.lanthanh.admin.icareapp.data.converter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lanthanh.admin.icareapp.domain.model.DTOCity;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;
import com.lanthanh.admin.icareapp.domain.model.DTOType;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;
import com.lanthanh.admin.icareapp.domain.model.DTOWeekDay;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public class ConverterJsonToDTO {
    public static List<DTOCountry> convertJsonToDTOCountry(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTOCountry>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static List<DTOCity> convertJsonToDTOCity(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTOCity>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static List<DTODistrict> convertJsonToDTODistrict(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTODistrict>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static List<DTOLocation> convertJsonToDTOLocation(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTOLocation>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static List<DTOVoucher> convertJsonToDTOVoucher(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTOVoucher>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static List<DTOType> convertJsonToDTOType(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTOType>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static List<DTOWeekDay> convertJsonToDTOTWeekDay(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTOWeekDay>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static List<DTOTime> convertJsonToDTOTime(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<DTOTime>>(){}.getType();
        return gson.fromJson(json, listType);
    }
}
