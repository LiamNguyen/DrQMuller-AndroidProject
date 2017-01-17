package com.lanthanh.admin.icareapp.data.manager;

import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;

import java.util.List;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public interface AppointmentManager extends Manager {
    String APPOINTMENT = "bookings";
    String STARTDATE_KEY = "start_date";
    String EXPIREDATE_KEY = "expire_date";
    String VERIFICATIONCODE_KEY = "code";
    String CREATED_DAY = "created_day";
    boolean insertTempBooking(int dayId, int timeId);
    boolean removeTempBooking(int dayId, int timeId);
    boolean insertAppointment(DTOAppointment dtoAppointment);
    void insertAppointmentSchedule(DTOAppointment dtoAppointment);
    boolean updateAppointment(int cusId, String verificationCode);
    boolean validateAppointment();
    List<DTOAppointment> getLocalAppointmentsFromPref(SharedPreferences sharedPreferences);
    void saveLocalAppointmentsToPref(SharedPreferences sharedPreferences, List<DTOAppointment> appointments);
}
