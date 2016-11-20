package com.example.admin.icareapp.Model;

/**
 * Created by ADMIN on 26-Oct-16.
 */

public enum ModelURL {
    SELECT_COUNTRIES("http://192.168.0.106/Select_Countries"),
    SELECT_CITIES("http://192.168.0.106/Select_Cities"),
    SELECT_DISTRICTS("http://192.168.0.106/Select_Districts"),
    SELECT_LOCATIONS("http://192.168.0.106/Select_Locations"),
    SELECT_VOUCHERS("http://192.168.0.106/Select_Vouchers"),
    SELECT_ALLTIMEINADAY("http://192.168.0.106/Select_AllTime"),
    SELECT_DAYSOFWEEK("http://192.168.0.106/Select_DaysOfWeek"),
    SELECT_SELECTEDTIME("http://192.168.0.106/Select_SelectedTime"),
    SELECT_CHECKTIMEEXISTENCE("http://192.168.0.106/Select_CheckTimeExistence"),
    INSERT_NEWTEMPTIME("http://192.168.0.106/Insert_NewTempTime"),
    UPDATE_CHOSENTIME("http://192.168.0.106/Update_ChosenTime"),
    UPDATE_UNCHOSENTIME("http://192.168.0.106/Update_UnchosenTime"),
    INSERT_NEWCUSTOMER("http://192.168.0.106/Insert_NewCustomer"),
    SELECT_TOAUTHENTICATE("http://192.168.0.106/Select_ToAuthenticate"),
    SELECT_CHECKUSEREXISTENCE("http://192.168.0.106/Select_CheckUserExistence"),
    SELECT_NoOFCUSTOMERS("http://192.168.0.106/Select_NumberOfCustomer"),
    UPDATE_CUSTOMERINFO("http://192.168.0.106/Update_CustomerInfo"),
    UPDATE_VALIDATEAPPOINTMENT("http://192.168.0.106/Update_ValidateAppointment"),
    SEND_EMAIL("http://192.168.0.106/sendemail");

    private String url;

    ModelURL(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
}
