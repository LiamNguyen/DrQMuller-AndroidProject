package com.example.admin.icareapp.Model;

/**
 * Created by ADMIN on 26-Oct-16.
 */

public enum ModelURL {
    SELECT_COUNTRIES("http://192.168.0.103/Select_Countries"),
    SELECT_CITIES("http://192.168.0.103/Select_Cities"),
    SELECT_DISTRICTS("http://192.168.0.103/Select_Districts"),
    SELECT_LOCATIONS("http://192.168.0.103/Select_Locations"),
    SELECT_VOUCHERS("http://192.168.0.103/Select_Vouchers"),
    SELECT_ALLTIMEINADAY("http://192.168.0.103/Select_AllTime"),
    INSERT_NEWCUSTOMER("http://192.168.0.103/Insert_NewCustomer"),
    SELECT_TOAUTHENTICATE("http://192.168.0.103/Select_ToAuthenticate"),
    SELECT_CHECKUSEREXISTENCE("http://192.168.0.103/Select_CheckUserExistence"),
    SELECT_NoOFCUSTOMERS("http://192.168.0.103/Select_NumberOfCustomer"),
    UPDATE_CUSTOMERINFO("http://192.168.0.103/Update_CustomerInfo"),
    SEND_EMAIL("http://192.168.0.103/sendemail");

    private String url;

    ModelURL(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
}
