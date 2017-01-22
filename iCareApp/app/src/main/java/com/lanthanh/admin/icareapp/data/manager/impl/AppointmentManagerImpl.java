package com.lanthanh.admin.icareapp.data.manager.impl;

import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterToUrlData;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
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
import java.util.List;

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
    public boolean insertTempBooking(int dayId, int timeId) {
        String data = ConverterToUrlData.convertToUrlData(ConverterToUrlData.getKeys(WeekDayManager.DAY_ID_KEY, TimeManager.TIME_ID_KEY),
                                                          ConverterToUrlData.getValues(Integer.toString(dayId), Integer.toString(timeId)));
        mApi.sendPostRequest(this, ModelURL.BOOKING.getUrl(Manager.isUAT), data);
        return insertTempBookingResult;
    }

    @Override
    public boolean removeTempBooking(int dayId, int timeId) {
        String data = ConverterToUrlData.convertToUrlData(ConverterToUrlData.getKeys(WeekDayManager.DAY_ID_KEY, TimeManager.TIME_ID_KEY),
                ConverterToUrlData.getValues(Integer.toString(dayId), Integer.toString(timeId)));
        mApi.sendPostRequest(this, ModelURL.UPDATE_UNCHOSENTIME.getUrl(Manager.isUAT), data);
        return removeTempBookingResult;
    }

    @Override
    public boolean insertAppointment(DTOAppointment dtoAppointment) {
        String data = ConverterToUrlData.convertToUrlData(
        ConverterToUrlData.getKeys  (CustomerManager.CUSTOMER_ID_KEY, LocationManager.LOCATION_ID_KEY,
                                     VoucherManager.VOUCHER_ID_KEY, TypeManager.TYPE_ID_KEY,
                                     AppointmentManager.STARTDATE_KEY, AppointmentManager.EXPIREDATE_KEY,
                                     AppointmentManager.VERIFICATIONCODE_KEY, AppointmentManager.APPOINTMENT_BOOKINGTIME),
        ConverterToUrlData.getValues(Integer.toString(dtoAppointment.getCustomerId()), Integer.toString(dtoAppointment.getLocationId()),
                                     Integer.toString(dtoAppointment.getVoucherId()),  Integer.toString(dtoAppointment.getTypeId()),
                                     ConverterToUrlData.convertDateForDB(dtoAppointment.getStartDate()), ConverterToUrlData.convertDateForDB(dtoAppointment.getExpireDate()),
                                     dtoAppointment.getVerficationCode(), ConverterJson.convertObjectToJson(ConverterToUrlData.convertToBookingTimeArray(dtoAppointment.getAppointmentScheduleList())))
        );
        mApi.sendPostRequest(this, ModelURL.INSERT_NEWAPPOINTMENT.getUrl(Manager.isUAT), data);
//        if (insertAppointmentResult){
//            insertAppointmentSchedule(dtoAppointment);
//        }
        return insertAppointmentResult;
    }

    @Override
    public void insertAppointmentSchedule(DTOAppointment dtoAppointment) {
        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = dtoAppointment.getAppointmentScheduleList();
        for (DTOAppointmentSchedule dtoAppointmentSchedule: dtoAppointmentScheduleList){
            String data = ConverterToUrlData.convertToUrlData(
                    ConverterToUrlData.getKeys(WeekDayManager.DAY_ID_KEY, TimeManager.TIME_ID_KEY, AppointmentManager.VERIFICATIONCODE_KEY),
                    ConverterToUrlData.getValues(Integer.toString(dtoAppointmentSchedule.getDayId()), Integer.toString(dtoAppointmentSchedule.getHourId()), dtoAppointment.getVerficationCode())
            );
            mApi.sendPostRequest(this, ModelURL.INSERT_NEWBOOKING.getUrl(Manager.isUAT), data);
        }
    }

    @Override
    public boolean updateAppointment(int cusId, String verificationCode) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys  (CustomerManager.CUSTOMER_ID_KEY_2, AppointmentManager.VERIFICATIONCODE_KEY),
                ConverterToUrlData.getValues(Integer.toString(cusId), verificationCode)
        );
        mApi.sendPostRequest(this, ModelURL.UPDATE_APPOINTMENT.getUrl(Manager.isUAT), data);
        return updateAppointmentResult;
    }

    @Override
    public boolean validateAppointment() {
        mApi.sendPostRequest(this, ModelURL.UPDATE_VALIDATEAPPOINTMENT.getUrl(Manager.isUAT), "");
        return validateAppointmentResult;
    }

    @Override
    public List<DTOAppointment> getLocalAppointmentsFromPref(SharedPreferences sharedPreferences) {
        Type listType = new TypeToken<List<DTOAppointment>>(){}.getType();
        return ConverterJson.convertJsonToObject(sharedPreferences.getString("appointments", ""), listType);
    }

    @Override
    public void saveLocalAppointmentsToPref(SharedPreferences sharedPreferences, List<DTOAppointment> appointments) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointments", ConverterJson.convertObjectToJson(appointments));
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
            //step 1
            String result = jsonObject.get("Insert_NewAppointment").getAsString();
            if (result.equals("Inserted new appointment")){
                //step 2
                if (!jsonObject.has("Select_Appointment_ID")){
                    //step 3
                    if (jsonObject.has("Insert_NewAppointmentSchedule")){
                        String result_2 = jsonObject.get("Insert_NewAppointmentSchedule").getAsString();
                        if (result_2.equals("Inserted new booking time")){
                            insertAppointmentResult = true;
                        }else{
                            insertAppointmentResult = false;
                        }
                    }
                }else{
                    insertAppointmentResult = false;
                }
            }else{
                insertAppointmentResult = false;
            }
        }else if (jsonObject.has("BookingTransaction")){
            String result = jsonObject.get("BookingTransaction").getAsString();
            if (result.equals("NonExist")){
               insertTempBookingResult = true;
            }else{
                insertTempBookingResult = false;
            }
        }else if (jsonObject.has("Update_UnchosenTime")){
            String result = jsonObject.get("Update_UnchosenTime").getAsString();
            if (result.equals("Updated")) {
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
