package com.lanthanh.admin.icareapp.data.converter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public class ConverterJson {
    private static Gson gson = new Gson();

    public static String convertToJson(Object o){
        return gson.toJson(o);
    }

    public static <T> T convertJsonToObject(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static <T> T convertJsonToObject(JsonElement json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static <T> String convertObjectToJson(T objectOfT, Class<T> classOfT){
        return gson.toJson(objectOfT, classOfT);
    }
}
