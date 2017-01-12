package com.lanthanh.admin.icareapp.data.manager.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.converter.ConverterToUrlData;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.base.AbstractManager;
import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;

import java.util.Calendar;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class CustomerManagerImpl extends AbstractManager implements CustomerManager {
    private String result;
    private boolean booleanResult;
    private int intResult;

    public CustomerManagerImpl(iCareApi api){
        super(api);
        booleanResult = false;
    }

    @Override
    public String logIn(String username, String password) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY, CustomerManager.CUSTOMER_PASSWORD_KEY),
                ConverterToUrlData.getValues(username, password)
        );
        mApi.sendPostRequest(this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(Manager.isUAT), data);
        return result;
    }

    @Override
    public boolean checkUserExistence(String username) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY),
                ConverterToUrlData.getValues(username)
        );
        mApi.sendPostRequest(this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(Manager.isUAT), data);
        return booleanResult;
    }

    @Override
    public boolean insertNewCustomer(String username, String password) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY, CustomerManager.CUSTOMER_PASSWORD_KEY),
                ConverterToUrlData.getValues(username, password)
        );
        mApi.sendPostRequest(this, ModelURL.INSERT_NEWCUSTOMER.getUrl(Manager.isUAT), data);
        return booleanResult;
    }

    @Override
    public int getCustomerId(String username) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY),
                ConverterToUrlData.getValues(username)
        );
        mApi.sendPostRequest(this, ModelURL.SELECT_NoOFCUSTOMERS.getUrl(Manager.isUAT), data);
        return intResult;
    }

    @Override
    public boolean updateCustomer(ModelUser user) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys("name", CustomerManager.CUSTOMER_ADDRESS_KEY,
                                           CustomerManager.CUSTOMER_DOB_KEY, CustomerManager.CUSTOMER_GENDER_KEY,
                                           CustomerManager.CUSTOMER_EMAIL_KEY, CustomerManager.CUSTOMER_PHONE_KEY,
                                           CustomerManager.CUSTOMER_UPDATE_DATE),
                ConverterToUrlData.getValues(user.getName(), user.getAddress(),
                                             user.getDOB(), user.getGender(),
                                             user.getEmail(), user.getPhone(),
                                             ConverterToUrlData.covertDateForDB(Calendar.getInstance().getTime()))
        );
        mApi.sendPostRequest(this, ModelURL.UPDATE_CUSTOMERINFO.getUrl(Manager.isUAT), data);
        return booleanResult;
    }

    @Override
    public boolean updateCustomerPassword(String username, String password) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY, CustomerManager.CUSTOMER_PASSWORD_KEY),
                ConverterToUrlData.getValues(username, password)
        );
        mApi.sendPostRequest(this, ModelURL.UPDATE_RESETPW.getUrl(Manager.isUAT), data);
        return booleanResult;
    }

    @Override
    public void onResponse(String json) {
        JsonObject jsonObject = ConverterJson.convertJsonToObject(json, JsonObject.class);

        if (jsonObject.has("Select_ToAuthenticate")){
            String result = jsonObject.get("Select_ToAuthenticate").getAsString();
            if (!result.equals("Fail"))
                this.result = result;
            else
                this.result = null;
        }else if (jsonObject.has("Select_CheckUserExistence")){
            String result = jsonObject.get("Select_CheckUserExistence").getAsString();
            if (result.equals("NonExist"))
                this.booleanResult = true;
            else
                this.booleanResult = false;
        }else if (jsonObject.has("Insert_NewCustomer")){
            String result = jsonObject.get("Insert_NewCustomer").getAsString();
            if (result.equals("Inserted"))
                this.booleanResult = true;
            else
                this.booleanResult = false;
        }else if (jsonObject.has("Select_NumberOfCustomers")){
            int result = jsonObject.get("Insert_NewCustomer").getAsInt();
            if (result == -1)
                this.intResult = 0;
            else
                this.intResult = result;
        }else if (jsonObject.has("Update_CustomerInfo")){
            String result = jsonObject.get("Update_CustomerInfo").getAsString();
            if (result.equals("Updated"))
                this.booleanResult = true;
            else
                this.booleanResult = false;
        }else if (jsonObject.has("Update_ResetPw")){
            String result = jsonObject.get("Update_ResetPw").getAsString();
            if (result.equals("Updated"))
                this.booleanResult = true;
            else
                this.booleanResult = false;
        }
    }
}
