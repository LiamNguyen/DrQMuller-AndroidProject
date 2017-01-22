package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterToUrlData;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.data.manager.TypeManager;
import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;

import java.util.Calendar;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class SendEmailManagerImpl extends AbstractManager implements SendEmailManager {
    private boolean notifyBookingResult, verifyAccResult, resetPwResult;

    public SendEmailManagerImpl(iCareApi api){
        super(api);
        resetResult();
    }

    @Override
    public boolean sendEmailNotifyBooking(DTOAppointment dtoAppointment) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys  (
                        CustomerManager.CUSTOMER_ID_KEY_2, AppointmentManager.CREATED_DAY,
                        CustomerManager.CUSTOMER_NAME_KEY, LocationManager.LOCATION_NAME_KEY,
                        VoucherManager.VOUCHER_NAME_KEY, TypeManager.TYPE_NAME_KEY,
                        AppointmentManager.STARTDATE_KEY, AppointmentManager.EXPIREDATE_KEY,
                        AppointmentManager.VERIFICATIONCODE_KEY, AppointmentManager.APPOINTMENT_SCHEDULE),
                ConverterToUrlData.getValues(Integer.toString(dtoAppointment.getCustomerId()), ConverterForDisplay.convertDateToDisplay(Calendar.getInstance().getTime()),
                        dtoAppointment.getCustomerName(), dtoAppointment.getLocationName(),
                        dtoAppointment.getVoucherName(),  dtoAppointment.getTypeName(),
                        ConverterForDisplay.convertDateToDisplay(dtoAppointment.getStartDate()), ConverterForDisplay.convertDateToDisplay(dtoAppointment.getExpireDate()),
                        dtoAppointment.getVerficationCode(), ConverterJson.convertObjectToJson(ConverterForDisplay.convertToStringList(dtoAppointment.getAppointmentScheduleList())))
        );
        mApi.sendPostRequest(this, ModelURL.SENDEMAIL_NOTIFYBOOKING.getUrl(Manager.isUAT), data);
        return notifyBookingResult;
    }

    @Override
    public boolean sendEmailResetPassword(String email, String username) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys  (CustomerManager.CUSTOMER_USERNAME_KEY_2, CustomerManager.CUSTOMER_EMAIL_KEY),
                ConverterToUrlData.getValues(username, email)
        );
        mApi.sendPostRequest(this, ModelURL.SENDEMAIL_RESETPW.getUrl(Manager.isUAT), data);
        return resetPwResult;
    }

    @Override
    public void onResponse(String json) {
        if (json == null){
            resetResult();
            return;
        }

        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("SendMail_NotifyBooking")) {
            String result = jsonObject.get("SendMail_NotifyBooking").getAsString();
            if (result.equals("Message has been sent"))
                notifyBookingResult = true;
            else
                notifyBookingResult = false;
        }else if (jsonObject.has("Send_Mail")) {

        }
        else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        notifyBookingResult = false;
        verifyAccResult = false;
        resetPwResult = false;
    }
}
