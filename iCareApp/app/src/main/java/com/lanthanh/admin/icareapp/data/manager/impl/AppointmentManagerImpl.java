package com.lanthanh.admin.icareapp.data.manager.impl;

import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.MachineManager;
import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.data.manager.TypeManager;
import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.data.manager.WeekDayManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ADMIN on 08-Jan-17.
 */

public class AppointmentManagerImpl extends AbstractManager implements AppointmentManager{
    private boolean insertTempBookingResult,
                    removeTempBookingResult,
                    insertAppointmentResult,
                    updateAppointmentResult,
                    validateAppointmentResult;

    public AppointmentManagerImpl(iCareApi api){
        super(api);
        resetResult();
    }

    @Override
    public boolean insertTempBooking(int dayId, int timeId, int locationId, int machineId) {
        mApi.sendPostRequest(this, ModelURL.BOOKING.getUrl(Manager.DB_TYPE),
                NetworkUtils.getKeys(WeekDayManager.DAY_ID_KEY, TimeManager.TIME_ID_KEY,
                        LocationManager.LOCATION_ID_KEY, MachineManager.MACHINE_ID_KEY),
                NetworkUtils.getValues(Integer.toString(dayId), Integer.toString(timeId),
                        Integer.toString(locationId), Integer.toString(machineId)));
        return insertTempBookingResult;
    }

    @Override
    public boolean removeTempBooking(List<DTOAppointmentSchedule> list, int locationId, int machineId) {
        mApi.sendPostRequest(this, ModelURL.UPDATE_UNCHOSENTIME.getUrl(Manager.DB_TYPE),
                NetworkUtils.getKeys(AppointmentManager.APPOINTMENT_BOOKINGTIME,
                        LocationManager.LOCATION_ID_KEY, MachineManager.MACHINE_ID_KEY),
                NetworkUtils.getValues(ConverterJson.convertObjectToJson(NetworkUtils.convertToBookingTimeArray(list)),
                        Integer.toString(locationId), Integer.toString(machineId)));
        return removeTempBookingResult;
    }

    @Override
    public boolean insertAppointment(DTOAppointment dtoAppointment) {
        mApi.sendPostRequest(this, ModelURL.INSERT_NEWAPPOINTMENT.getUrl(Manager.DB_TYPE),
                NetworkUtils.getKeys  (CustomerManager.CUSTOMER_ID_KEY, LocationManager.LOCATION_ID_KEY,
                        VoucherManager.VOUCHER_ID_KEY, TypeManager.TYPE_ID_KEY, MachineManager.MACHINE_ID_KEY,
                        AppointmentManager.STARTDATE_KEY, AppointmentManager.EXPIREDATE_KEY,
                        AppointmentManager.VERIFICATIONCODE_KEY, AppointmentManager.APPOINTMENT_BOOKINGTIME),
                NetworkUtils.getValues(Integer.toString(dtoAppointment.getCustomerId()), Integer.toString(dtoAppointment.getLocationId()),
                        Integer.toString(dtoAppointment.getVoucherId()),  Integer.toString(dtoAppointment.getTypeId()), Integer.toString(dtoAppointment.getMachineId()),
                        NetworkUtils.convertDateForDB(dtoAppointment.getStartDate()), NetworkUtils.convertDateForDB(dtoAppointment.getExpireDate()),
                        dtoAppointment.getVerficationCode(), ConverterJson.convertObjectToJson(NetworkUtils.convertToBookingTimeArray(dtoAppointment.getAppointmentScheduleList())))
                );
        return insertAppointmentResult;
    }

    @Override
    public void insertAppointmentSchedule(DTOAppointment dtoAppointment) {
        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = dtoAppointment.getAppointmentScheduleList();
        for (DTOAppointmentSchedule dtoAppointmentSchedule: dtoAppointmentScheduleList){
            mApi.sendPostRequest(this, ModelURL.INSERT_NEWBOOKING.getUrl(Manager.DB_TYPE),
                    NetworkUtils.getKeys(WeekDayManager.DAY_ID_KEY, TimeManager.TIME_ID_KEY, AppointmentManager.VERIFICATIONCODE_KEY),
                    NetworkUtils.getValues(Integer.toString(dtoAppointmentSchedule.getDayId()), Integer.toString(dtoAppointmentSchedule.getHourId()), dtoAppointment.getVerficationCode()));
        }
    }

    @Override
    public boolean updateAppointment(int cusId, String verificationCode) {
        mApi.sendPostRequest(this, ModelURL.UPDATE_APPOINTMENT.getUrl(Manager.DB_TYPE),
                    NetworkUtils.getKeys  (CustomerManager.CUSTOMER_ID_KEY_2, AppointmentManager.VERIFICATIONCODE_KEY),
                    NetworkUtils.getValues(Integer.toString(cusId), verificationCode));
        return updateAppointmentResult;
    }

    @Override
    public boolean validateAppointment() {
        mApi.sendPostRequest(this, ModelURL.UPDATE_VALIDATEAPPOINTMENT.getUrl(Manager.DB_TYPE), null, null);
        return validateAppointmentResult;
    }

    @Override
    public List<DTOAppointment> getLocalAppointmentsFromPref(SharedPreferences sharedPreferences, int userId) {
        Type mapType = new TypeToken<Map<Integer ,String>>(){}.getType();
        Type listType = new TypeToken<List<DTOAppointment>>(){}.getType();
        Map<Integer, String> appointmentDB = ConverterJson.convertJsonToObject(sharedPreferences.getString("appointmentDB", ""), mapType);
        if (appointmentDB == null)
            appointmentDB = new HashMap<>();
        for (int i : appointmentDB.keySet()){
            if (i == userId)
                return ConverterJson.convertJsonToObject(appointmentDB.get(i), listType);
        }
        return null;
    }

    @Override
    public void saveLocalAppointmentsToPref(SharedPreferences sharedPreferences, List<DTOAppointment> appointments) {
        Type mapType = new TypeToken<Map<Integer ,String>>(){}.getType();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<Integer, String> appointmentDB = ConverterJson.convertJsonToObject(sharedPreferences.getString("appointmentDB", ""), mapType);
        if (appointmentDB == null)
            appointmentDB = new HashMap<>();
        appointmentDB.put(appointments.get(0).getCustomerId(), ConverterJson.convertObjectToJson(appointments));
        editor.putString("appointmentDB", ConverterJson.convertObjectToJson(appointmentDB));
        editor.apply();
        editor.commit();
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Insert_NewAppointment")){
            JsonArray result = jsonObject.get("Insert_NewAppointment").getAsJsonArray();
            if (result.size() != 0 && result.get(1).getAsJsonObject().get("Status").getAsString().equals("1")){
                insertAppointmentResult = true;
            }else{
                insertAppointmentResult = false;
            }
        }else if (jsonObject.has("BookingTransaction")){
            JsonArray result = jsonObject.get("BookingTransaction").getAsJsonArray();
            if (result.size() != 0 && result.get(0).getAsJsonObject().get("existency").getAsString().equals("0")){
               insertTempBookingResult = true;
            }else{
                insertTempBookingResult = false;
            }
        }else if (jsonObject.has("Update_UnchosenTime")){
            JsonArray result = jsonObject.get("Update_UnchosenTime").getAsJsonArray();
            if (result.size() != 0 && result.get(0).getAsJsonObject().get("Status").getAsString().equals("1")) {
                removeTempBookingResult = true;
            }else {
                removeTempBookingResult = false;
            }
        }else if (jsonObject.has("Update_Appointment")){
            String result = jsonObject.get("Update_Appointment").getAsString();
            if (result.equals("Updated")) {
                updateAppointmentResult = true;
            }else {
                updateAppointmentResult = false;
            }
        }else if (jsonObject.has("Update_ValidateAppointment")){
            String result = jsonObject.get("Update_ValidateAppointment").getAsString();
            if (result.equals("Validate")) {
                validateAppointmentResult = true;
            }else{
                validateAppointmentResult = false;
            }
        }else
            resetResult();
    }

    @Override
    public void resetResult() {
        insertAppointmentResult = false;
        insertTempBookingResult = false;
        removeTempBookingResult = false;
        updateAppointmentResult = false;
        validateAppointmentResult = false;
    }
}
