package com.lanthanh.admin.icareapp.utils.converter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public class ConverterJson {
    private static Gson gson = new Gson();

    public static <T> T convertJsonToObject(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static <T> T convertJsonToObject(String json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }

    public static <T> T convertGsonToObject(JsonElement json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static <T> List<T> convertGsonToObjectList(JsonArray jsonArray, Class<T> classOfT){
        List<T> resultList = new ArrayList<>();

        if (jsonArray != null && jsonArray.size() != 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                T objectT = gson.fromJson(jsonArray.get(i), classOfT);
                resultList.add(objectT);
            }
        }

        return resultList;
    }

//    public static <T> List<T> convertGsonToObjectList(JsonArray jsonArray, Class<T> classOfT){
//        Type listType = new TypeToken<List<T>>(){}.getType();
//        return gson.fromJson(jsonArray, listType);
//    }

    public static <T> String convertObjectToJson(T objectOfT, Class<T> classOfT){
        return gson.toJson(objectOfT, classOfT);
    }

    public static <T> String convertObjectToJson(T objectOfT){
        return gson.toJson(objectOfT);
    }
}
