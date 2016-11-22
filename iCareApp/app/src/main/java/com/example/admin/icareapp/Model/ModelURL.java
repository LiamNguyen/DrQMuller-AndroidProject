package com.example.admin.icareapp.Model;

/**
 * Created by ADMIN on 26-Oct-16.
 */

public enum ModelURL {
    SELECT_TOAUTHENTICATE("http://210.211.109.180/drmuller/Select_ToAuthenticate.php"),
    SELECT_CHECKUSEREXISTENCE("http://210.211.109.180/drmuller/Select_CheckUserExistence.php"),
    SELECT_NoOFCUSTOMERS("http://210.211.109.180/drmuller/Select_NumberOfCustomer.php"),
    SELECT_COUNTRIES("http://210.211.109.180/drmuller/Select_Countries.php"),
    SELECT_CITIES("http://210.211.109.180/drmuller/Select_Cities.php"),
    SELECT_DISTRICTS("http://210.211.109.180/drmuller/Select_Districts.php"),
    SELECT_LOCATIONS("http://210.211.109.180/drmuller/Select_Locations.php"),
    SELECT_VOUCHERS("http://210.211.109.180/drmuller/Select_Vouchers.php"),
    SELECT_ALLTIMEINADAY("http://210.211.109.180/drmuller/Select_AllTime.php"),
    SELECT_DAYSOFWEEK("http://210.211.109.180/drmuller/Select_DaysOfWeek.php"),
    SELECT_SELECTEDTIME("http://210.211.109.180/drmuller/Select_SelectedTime.php"),
    SELECT_CHECKTIMEEXISTENCE("http://210.211.109.180/drmuller/Select_CheckTimeExistence.php"),
    INSERT_NEWTEMPTIME("http://210.211.109.180/drmuller/Insert_NewTempTime.php"),
    INSERT_NEWCUSTOMER("http://210.211.109.180/drmuller/Insert_NewCustomer.php"),
    INSERT_NEWAPPOINTMENT("http://210.211.109.180/drmuller/Insert_NewAppointment.php"),
    INSERT_NEWBOOKING("http://210.211.109.180/drmuller/Insert_NewBookingTime.php"),
    UPDATE_CHOSENTIME("http://210.211.109.180/drmuller/Update_ChosenTime.php"),
    UPDATE_UNCHOSENTIME("http://210.211.109.180/drmuller/Update_UnchosenTime.php"),
    UPDATE_CUSTOMERINFO("http://210.211.109.180/drmuller/Update_CustomerInfo.php"),
    UPDATE_VALIDATEAPPOINTMENT("http://210.211.109.180/drmuller/Update_ValidateAppointment.php"),
    UPDATE_APPOINTMENT("http://210.211.109.180/drmuller/Update_Appointment.php"),
    SEND_EMAIL("http://210.211.109.180/drmuller/SendMail_VerifyAcc.php"),
    SENDEMAIL_RESETPW("http://210.211.109.180/drmuller/SendMail_ResetPassword.php");

    private String url;

    ModelURL(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
}
