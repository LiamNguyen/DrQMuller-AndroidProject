package com.lanthanh.admin.icareapp.data.manager.impl;

import android.content.SharedPreferences;

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
    private String logInResult;
    private boolean userExistenceResult, newCusResult, updateCusResult, updatePwResult, updateVerifyAcc;
    private int getIdResult;

    public CustomerManagerImpl(iCareApi api){
        super(api);
        resetResult();
    }

    @Override
    public String logIn(String username, String password) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY, CustomerManager.CUSTOMER_PASSWORD_KEY),
                ConverterToUrlData.getValues(username, password)
        );
        mApi.sendPostRequest(this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(Manager.isUAT), data);
        return logInResult;
    }

    @Override
    public boolean checkUserExistence(String username) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY),
                ConverterToUrlData.getValues(username)
        );
        mApi.sendPostRequest(this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(Manager.isUAT), data);
        return userExistenceResult;
    }

    @Override
    public boolean insertNewCustomer(String username, String password) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY, CustomerManager.CUSTOMER_PASSWORD_KEY),
                ConverterToUrlData.getValues(username, password)
        );
        mApi.sendPostRequest(this, ModelURL.INSERT_NEWCUSTOMER.getUrl(Manager.isUAT), data);
        return newCusResult;
    }

    @Override
    public int getCustomerId(String username) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY),
                ConverterToUrlData.getValues(username)
        );
        mApi.sendPostRequest(this, ModelURL.SELECT_NoOFCUSTOMERS.getUrl(Manager.isUAT), data);
        return getIdResult;
    }

    @Override
    public boolean updateCustomer(ModelUser user) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_ID_KEY_2,
                                           CustomerManager.CUSTOMER_NAME_KEY, CustomerManager.CUSTOMER_ADDRESS_KEY,
                                           CustomerManager.CUSTOMER_DOB_KEY, CustomerManager.CUSTOMER_GENDER_KEY,
                                           CustomerManager.CUSTOMER_EMAIL_KEY, CustomerManager.CUSTOMER_PHONE_KEY,
                                           CustomerManager.CUSTOMER_UPDATE_DATE),
                ConverterToUrlData.getValues(Integer.toString(user.getID()),
                                             user.getName(), user.getAddress(),
                                             user.getDOB(), user.getGender(),
                                             user.getEmail(), user.getPhone(),
                                             ConverterToUrlData.convertDateForDB(Calendar.getInstance().getTime()))
        );
        mApi.sendPostRequest(this, ModelURL.UPDATE_CUSTOMERINFO.getUrl(Manager.isUAT), data);
        return updateCusResult;
    }

    @Override
    public boolean updateCustomerPassword(String username, String password) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY_2, CustomerManager.CUSTOMER_PASSWORD_KEY),
                ConverterToUrlData.getValues(username, password)
        );
        mApi.sendPostRequest(this, ModelURL.UPDATE_RESETPW.getUrl(Manager.isUAT), data);
        return updatePwResult;
    }

    @Override
    public boolean updateVerifyAcc(String id) {
        String data = ConverterToUrlData.convertToUrlData(
                ConverterToUrlData.getKeys(CustomerManager.CUSTOMER_ID_KEY_2),
                ConverterToUrlData.getValues(id)
        );
        mApi.sendPostRequest(this, ModelURL.UPDATE_VERIFYACC.getUrl(Manager.isUAT), data);
        return updateVerifyAcc;
    }

    @Override
    public ModelUser getLocalUserFromPref(SharedPreferences sharedPreferences) {
        return ConverterJson.convertJsonToObject(sharedPreferences.getString("user", ""), ModelUser.class);
    }

    @Override
    public void saveLocalUserToPref(SharedPreferences sharedPreferences, ModelUser user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", ConverterJson.convertObjectToJson(user, ModelUser.class));
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

        if (jsonObject.has("Select_ToAuthenticate")){
            String result = jsonObject.get("Select_ToAuthenticate").getAsString();
            if (result.equals("LoginFail") || result.equals("QueryFail"))
                this.logInResult = null;
            else
                this.logInResult = result;
        }else if (jsonObject.has("Select_CheckUserExistence")){
            String result = jsonObject.get("Select_CheckUserExistence").getAsString();
            if (result.equals("NonExist"))
                this.userExistenceResult = true;
            else
                this.userExistenceResult = false;
        }else if (jsonObject.has("Insert_NewCustomer")){
            String result = jsonObject.get("Insert_NewCustomer").getAsString();
            if (result.equals("Inserted"))
                this.newCusResult = true;
            else
                this.newCusResult = false;
        }else if (jsonObject.has("Select_NumberOfCustomers")){
            int result = jsonObject.get("Select_NumberOfCustomers").getAsInt();
            if (result == -1)
                this.getIdResult = 0;
            else
                this.getIdResult = result;
        }else if (jsonObject.has("Update_CustomerInfo")){
            String result = jsonObject.get("Update_CustomerInfo").getAsString();
            if (result.equals("Updated"))
                this.updateCusResult = true;
            else
                this.updateCusResult = false;
        }else if (jsonObject.has("Update_ResetPw")){
            String result = jsonObject.get("Update_ResetPw").getAsString();
            if (result.equals("Updated"))
                this.updatePwResult = true;
            else
                this.updatePwResult = false;
        }else if (jsonObject.has("Update_VerifyAcc")){
            String result = jsonObject.get("Update_VerifyAcc").getAsString();
            if (result.equals("Updated"))
                this.updateVerifyAcc = true;
            else
                this.updateVerifyAcc = false;
        }else{
            resetResult();
        }
    }

    @Override
    public void resetResult() {
        logInResult = null;
        userExistenceResult = false;
        newCusResult = false;
        updateCusResult = false;
        updatePwResult = false;
        updateVerifyAcc = false;
        getIdResult = 0;
    }
}
