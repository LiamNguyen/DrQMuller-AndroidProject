package com.lanthanh.admin.icareapp.utils.converter;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class ConverterForDisplay {
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

    public static Date convertStringToDate(String date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
