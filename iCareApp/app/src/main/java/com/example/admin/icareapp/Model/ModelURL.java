package com.example.admin.icareapp.Model;

/**
 * Created by ADMIN on 26-Oct-16.
 */

public enum ModelURL {
    INSERT_NEWCUSTOMER("http://192.168.0.106/Insert_NewCustomer"),
    SELECT_TOAUTHENTICATE("http://192.168.0.106/Select_ToAuthenticate"),
    SELECT_CHECKUSEREXISTENCE("http://192.168.0.106/Select_CheckUserExistence"),
    SELECT_NoOFCUSTOMERS("http://192.168.0.106/Select_NumberOfCustomer"),
    UPDATE_CUSTOMERINFO("http://192.168.0.106/Update_CustomerInfo"),
    SEND_EMAIL("http://192.168.0.106/sendemail");

    private String url;

    ModelURL(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
}
