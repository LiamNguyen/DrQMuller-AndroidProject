package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
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

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class SendEmailManagerImpl extends AbstractManager implements SendEmailManager {
    private boolean result;

    public SendEmailManagerImpl(iCareApi api){
        super(api);
        result = false;
    }

    @Override
    public boolean sendEmailNotifyBooking(DTOAppointment dtoAppointment) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys  (CustomerManager.CUSTOMER_NAME_KEY, LocationManager.LOCATION_NAME_KEY,
                        VoucherManager.VOUCHER_NAME_KEY, TypeManager.TYPE_NAME_KEY,
                        AppointmentManager.STARTDATE_KEY, AppointmentManager.EXPIREDATE_KEY,
                        AppointmentManager.VERIFICATIONCODE_KEY, AppointmentManager.APPOINTMENT),
                ConverterToUrlData.getValues(Integer.toString(dtoAppointment.getCustomerId()), Integer.toString(dtoAppointment.getLocationId()),
                        Integer.toString(dtoAppointment.getVoucherId()),  Integer.toString(dtoAppointment.getTypeId()),
                        ConverterToUrlData.covertDateForDB(dtoAppointment.getStartDate()), ConverterToUrlData.covertDateForDB(dtoAppointment.getExpireDate()),
                        dtoAppointment.getVerficationCode(), ConverterJson.convertToJson(ConverterForDisplay.convertToStringList(dtoAppointment.getAppointmentScheduleList())))
        );
        mApi.sendPostRequest(this, ModelURL.SENDEMAIL_NOTIFYBOOKING.getUrl(Manager.isUAT), data);
        return result;
    }

    @Override
    public boolean sendEmailResetPassword(String email, String username) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys  (CustomerManager.CUSTOMER_USERNAME_KEY_2, CustomerManager.CUSTOMER_EMAIL_KEY),
                ConverterToUrlData.getValues(username, email)
        );
        mApi.sendPostRequest(this, ModelURL.SENDEMAIL_RESETPW.getUrl(Manager.isUAT), data);
        return result;
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);
        String result = jsonObject.get("Send_Email").getAsString();
        this.result = result.equals("Message has been sent");
    }

    @Override
    public void resetResult() {

    }
}
