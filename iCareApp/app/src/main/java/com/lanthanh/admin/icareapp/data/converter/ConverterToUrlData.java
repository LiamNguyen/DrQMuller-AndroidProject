package com.lanthanh.admin.icareapp.data.converter;

import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class ConverterToUrlData {
    public static String convertToUrlData(String key, String value){
        StringBuilder result = new StringBuilder();
        result.append(key).append("=").append(value);
        return result.toString();
    }

    public static String[] getKeys(String... array){
        return array;
    }

    public static String[] getValues(String... array){
        return array;
    }

    public static String convertToUrlData(String[] keys, String[] values){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < keys.length; i++){
            if (i == 0){
                result.append(keys[i]).append("=").append(values[i]);
                continue;
            }
            result.append("&").append(keys[i]).append("=").append(values[i]);
        }
        return result.toString();
    }

    public static String convertDateForDB(Date date){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        return sdf.format(date);
    }

    public static List<int[]> convertToBookingTimeArray(List<DTOAppointmentSchedule> appointmentSchedulesList){
        List<int[]> result = new ArrayList<>();
        for (DTOAppointmentSchedule schedule : appointmentSchedulesList){
            int[] i = {schedule.getDayId(), schedule.getHourId()};
            result.add(i);
        }
        return result;
    }
}
