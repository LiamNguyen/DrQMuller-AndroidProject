package com.lanthanh.admin.icareapp.data.manager.impl;

import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.api.iCareApi;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;
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
    private String logInResult, newCusResult;
    private boolean basicInfo, necessaryInfo, importantInfo, updateCusResult, updatePwResult, updateVerifyAcc;

    public CustomerManagerImpl(iCareApi api){
        super(api);
        resetResult();
    }

    @Override
    public String logIn(String username, String password) {
        String json = NetworkUtils.convertJsonData(new String[]{"username", "password"}, new String[]{username, password});
        mApi.sendPostRequest(this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(Manager.DB_TYPE), json);
        return logInResult;
    }

    @Override
    public String insertNewCustomer(String username, String password) {
        String json = NetworkUtils.convertJsonData(new String[]{"username", "password"}, new String[]{username, password});
        mApi.sendPostRequest(this, ModelURL.INSERT_NEWCUSTOMER.getUrl(Manager.DB_TYPE), json);
        return newCusResult;
    }

    @Override
    public boolean updateCustomer(ModelUser user) {
        mApi.sendPostRequest(this, ModelURL.UPDATE_CUSTOMERINFO.getUrl(Manager.DB_TYPE),
                NetworkUtils.getKeys(CustomerManager.CUSTOMER_ID_KEY_2,
                        CustomerManager.CUSTOMER_NAME_KEY, CustomerManager.CUSTOMER_ADDRESS_KEY,
                        CustomerManager.CUSTOMER_DOB_KEY, CustomerManager.CUSTOMER_GENDER_KEY,
                        CustomerManager.CUSTOMER_EMAIL_KEY, CustomerManager.CUSTOMER_PHONE_KEY,
                        CustomerManager.CUSTOMER_UPDATE_DATE),
                NetworkUtils.getValues(Integer.toString(user.getID()),
                        user.getName(), user.getAddress(),
                        user.getDOB(), user.getGender(),
                        user.getEmail(), user.getPhone(),
                        NetworkUtils.convertDateForDB(Calendar.getInstance().getTime()))
        );
        return updateCusResult;
    }

    @Override
    public boolean updateCustomerBasicInfo(ModelUser user) {
        String json = NetworkUtils.convertJsonData(new String[]{"userId", "userName", "userAddress", "updatedAt"},
                                                   new String[]{Integer.toString(user.getID()), user.getName(), user.getAddress(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
        mApi.sendPostRequest(this, ModelURL.UPDATE_BASICINFO.getUrl(Manager.DB_TYPE), json);
        return basicInfo;
    }

    @Override
    public boolean updateCustomerNecessaryInfo(ModelUser user) {
        String json = NetworkUtils.convertJsonData(new String[]{"userId", "userDob", "userGender", "updatedAt"},
                new String[]{Integer.toString(user.getID()), user.getDOB(), user.getGender(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
        mApi.sendPostRequest(this, ModelURL.UPDATE_NECESSARYINFO.getUrl(Manager.DB_TYPE), json);
        return necessaryInfo;
    }

    @Override
    public boolean updateCustomerImpotantInfo(ModelUser user) {
        String json = NetworkUtils.convertJsonData(new String[]{"userId", "userEmail", "userPhone", "updatedAt"},
                new String[]{Integer.toString(user.getID()), user.getEmail(), user.getPhone(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
        mApi.sendPostRequest(this, ModelURL.UPDATE_IMPORTANTINFO.getUrl(Manager.DB_TYPE), json);
        return importantInfo;
    }

    @Override
    public boolean updateCustomerPassword(String username, String password) {
        mApi.sendPostRequest(this, ModelURL.UPDATE_RESETPW.getUrl(Manager.DB_TYPE),
                NetworkUtils.getKeys(CustomerManager.CUSTOMER_USERNAME_KEY_2, CustomerManager.CUSTOMER_PASSWORD_KEY),
                NetworkUtils.getValues(username, password)
        );
        return updatePwResult;
    }

    @Override
    public boolean updateVerifyAcc(String id) {
        mApi.sendPostRequest(this, ModelURL.UPDATE_VERIFYACC.getUrl(Manager.DB_TYPE),
                NetworkUtils.getKeys(CustomerManager.CUSTOMER_ID_KEY_2),
                NetworkUtils.getValues(id)
        );
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
            JsonArray result = jsonObject.get("Select_ToAuthenticate").getAsJsonArray();
            if (result.size() == 1 || result.size() == 0)
                this.logInResult = null;
            else
                this.logInResult = result.get(1).getAsJsonObject().get("jwt").getAsString();
        }else if (jsonObject.has("Insert_NewCustomer")){
            JsonArray result = jsonObject.get("Insert_NewCustomer").getAsJsonArray();
            if (!result.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                this.newCusResult = result.get(0).getAsJsonObject().get("Status").getAsString();
            else
                this.newCusResult = result.get(1).getAsJsonObject().get("jwt").getAsString();
        }else if (jsonObject.has("Update_CustomerInfo")){
            String result = jsonObject.get("Update_CustomerInfo").getAsString();
            if (result.equals("Updated"))
                this.updateCusResult = true;
            else
                this.updateCusResult = false;
        }else if (jsonObject.has("Update_BasicInfo")){
            JsonArray result = jsonObject.get("Update_BasicInfo").getAsJsonArray();
            if (!result.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                this.basicInfo = false;
            else
                this.basicInfo = true;
        }else if (jsonObject.has("Update_NecessaryInfo")){
            JsonArray result = jsonObject.get("Update_NecessaryInfo").getAsJsonArray();
            if (!result.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                this.necessaryInfo = false;
            else
                this.necessaryInfo = true;
        }else if (jsonObject.has("Update_ImportantInfo")){
            JsonArray result = jsonObject.get("Update_ImportantInfo").getAsJsonArray();
            if (!result.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                this.importantInfo = false;
            else
                this.importantInfo = true;
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
        newCusResult = null;
        updateCusResult = false;
        updatePwResult = false;
        updateVerifyAcc = false;
        basicInfo = false;
        necessaryInfo = false;
        importantInfo = false;
    }
}
