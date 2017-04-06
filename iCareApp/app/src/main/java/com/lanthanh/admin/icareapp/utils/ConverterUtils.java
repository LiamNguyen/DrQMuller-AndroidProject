package com.lanthanh.admin.icareapp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by long.vu on 4/6/2017.
 */

public class ConverterUtils {
    public static class date {
        public static String convertDateForDb(Date date) {
            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            return sdf.format(date);
        }

        public static String convertDateForDisplay(Date date){
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            return sdf.format(date);
        }

        public static String convertDateForDisplay(int year, int monthOfYear, int dayOfMonth){
            String date = ((Integer.toString(dayOfMonth).length() == 1)?("0" + Integer.toString(dayOfMonth)) : Integer.toString(dayOfMonth))
                    + "/" + ((Integer.toString(monthOfYear + 1).length() == 1)?("0" + Integer.toString(monthOfYear + 1)) : Integer.toString(monthOfYear + 1))
                    + "/" + Integer.toString(year);
            return date;
        }

        public static String convertDateForDb(String date){
            if (date == null)
                return "";

            String myFormat = "dd/MM/yyyy";
            String dbFormat = "yyyy-MM-dd";
            SimpleDateFormat dbSdf = new SimpleDateFormat(dbFormat);
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            try {
                Date dateForm = sdf.parse(date);
                return dbSdf.format(dateForm);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }

        public static String convertDateForDisplay(String date){
            if (date == null)
                return "";

            String myFormat = "dd/MM/yyyy";
            String dbFormat = "yyyy-MM-dd";
            SimpleDateFormat dbSdf = new SimpleDateFormat(dbFormat);
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            try {
                Date dateForm = dbSdf.parse(date);
                return sdf.format(dateForm);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }

        public static Date convertStringToDate(String date){
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                return dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static int convertToHours(String time) {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            try {
                Date date = format.parse(time);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public static class gender {
        public static String convertGenderForDisplay(String gender, String male, String female){
            if (gender == null)
                return null;

            if (gender.equals("Male"))
                return male;
            else
                return female;
        }

        public static String convertGenderForDb(String gender, String male, String female){
            if (gender == null)
                return null;

            if (gender.equals(male))
                return "Male";
            else
                return "Female";
        }
    }

    public static class json {
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

        public static <T> String convertObjectToJson(T objectOfT){
            return gson.toJson(objectOfT);
        }

        public static String convertToApiJson(String[] keys, String[] values){
            JSONObject jsonObject= new JSONObject();
            try {
                for (int i = 0; i < keys.length; i++) {
                    jsonObject.put(keys[i], values[i]);
                }
            }catch (Exception e){
                return "";
            }
            return jsonObject.toString();
        }

    }
}
