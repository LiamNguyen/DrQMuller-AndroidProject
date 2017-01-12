package com.lanthanh.admin.icareapp.presentation.converter;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class ConverterForDisplay {

    public static List<String> convertToStringList(List l){
        List<String> result = new ArrayList<>();
        for (Object o: l){
            result.add(o.toString());
        }
        return result;
    }

    public static String convertDateToDisplay(Date date){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        return sdf.format(date);
    }

    public static String convertDateToDisplay(int year, int monthOfYear, int dayOfMonth){
        String date = ((Integer.toString(dayOfMonth).length() == 1)?("0" + Integer.toString(dayOfMonth)) : Integer.toString(dayOfMonth))
                + "/" + ((Integer.toString(monthOfYear + 1).length() == 1)?("0" + Integer.toString(monthOfYear + 1)) : Integer.toString(monthOfYear + 1))
                + "/" + Integer.toString(year);
        return date;
    }

    public static String convertStringDateFromDBToDisplay(String date){
        if (date == null)
            return null;

        String myFormat = "dd/MM/yyyy";
        String dbFormat = "yyyy-MM-dd";
        SimpleDateFormat dbSdf = new SimpleDateFormat(dbFormat);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        try {
            Date dateForm = dbSdf.parse(date);
            return sdf.format(dateForm);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertStringGenderFromDBToDisplay(String gender, String male, String female){
        if (gender == null)
            return null;

        if (gender.equals("Male"))
            return male;
        else
            return female;
    }
}
