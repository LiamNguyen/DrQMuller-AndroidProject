package com.lanthanh.admin.icareapp.Model;

/**
 * Created by ADMIN on 26-Oct-16.
 */

public enum ModelURL {
    SELECT_TOAUTHENTICATE("http://210.211.109.180/drmuller/Select_ToAuthenticate.php", "http://drqmuller.com/drmuller/Select_ToAuthenticate.php"),
    SELECT_CHECKUSEREXISTENCE("http://210.211.109.180/drmuller/Select_CheckUserExistence.php", "http://drqmuller.com/drmuller/Select_CheckUserExistence.php"),
    SELECT_NoOFCUSTOMERS("http://210.211.109.180/drmuller/Select_NumberOfCustomer.php", "http://drqmuller.com/drmuller/Select_NumberOfCustomer.php"),
    SELECT_COUNTRIES("http://210.211.109.180/drmuller/Select_Countries.php", "http://drqmuller.com/drmuller/Select_Countries.php"),
    SELECT_CITIES("http://210.211.109.180/drmuller/Select_Cities.php", "http://drqmuller.com/drmuller/Select_Cities.php"),
    SELECT_DISTRICTS("http://210.211.109.180/drmuller/Select_Districts.php", "http://drqmuller.com/drmuller/Select_Districts.php"),
    SELECT_LOCATIONS("http://210.211.109.180/drmuller/Select_Locations.php", "http://drqmuller.com/drmuller/Select_Locations.php"),
    SELECT_VOUCHERS("http://210.211.109.180/drmuller/Select_Vouchers.php", "http://drqmuller.com/drmuller/Select_Vouchers.php"),
    SELECT_ALLTIMEINADAY("http://210.211.109.180/drmuller/Select_AllTime.php", "http://drqmuller.com/drmuller/Select_AllTime.php"),
    SELECT_DAYSOFWEEK("http://210.211.109.180/drmuller/Select_DaysOfWeek.php", "http://drqmuller.com/drmuller/Select_DaysOfWeek.php"),
    SELECT_SELECTEDTIME("http://210.211.109.180/drmuller/Select_SelectedTime.php", "http://drqmuller.com/drmuller/Select_SelectedTime.php"),
    SELECT_CHECKTIMECONCURRENCE("http://210.211.109.180/drmuller/Select_CheckTimeConcurrence.php","http://drqmuller.com/drmuller/Select_CheckTimeConcurrence.php"),
    SELECT_CHECKTIMEEXISTENCE("http://210.211.109.180/drmuller/Select_CheckTimeExistence.php", "http://drqmuller.com/drmuller/Select_CheckTimeExistence.php"),
    INSERT_NEWTEMPTIME("http://210.211.109.180/drmuller/Insert_NewTempTime.php", "http://drqmuller.com/drmuller/Insert_NewTempTime.php"),
    INSERT_NEWCUSTOMER("http://210.211.109.180/drmuller/Insert_NewCustomer.php", "http://drqmuller.com/drmuller/Insert_NewCustomer.php"),
    INSERT_NEWAPPOINTMENT("http://210.211.109.180/drmuller/Insert_NewAppointment.php", "http://drqmuller.com/drmuller/Insert_NewAppointment.php"),
    INSERT_NEWBOOKING("http://210.211.109.180/drmuller/Insert_NewBookingTime.php", "http://drqmuller.com/drmuller/Insert_NewBookingTime.php"),
    UPDATE_CHOSENTIME("http://210.211.109.180/drmuller/Update_ChosenTime.php", "http://drqmuller.com/drmuller/Update_ChosenTime.php"),
    UPDATE_UNCHOSENTIME("http://210.211.109.180/drmuller/Update_UnchosenTime.php", "http://drqmuller.com/drmuller/Update_UnchosenTime.php"),
    UPDATE_CUSTOMERINFO("http://210.211.109.180/drmuller/Update_CustomerInfo.php", "http://drqmuller.com/drmuller/Update_CustomerInfo.php"),
    UPDATE_VALIDATEAPPOINTMENT("http://210.211.109.180/drmuller/Update_ValidateAppointment.php", "http://drqmuller.com/drmuller/Update_ValidateAppointment.php"),
    UPDATE_APPOINTMENT("http://210.211.109.180/drmuller/Update_Appointment.php", "http://drqmuller.com/drmuller/Update_Appointment.php"),
    UPDATE_VERIFYACC("http://210.211.109.180/drmuller/Update_VerifyAcc.php", "http://drqmuller.com/drmuller/Update_VerifyAcc.php"),
    UPDATE_RESETPW("http://210.211.109.180/drmuller/Update_ResetPw.php", "http://drqmuller.com/drmuller/Update_ResetPw.php"),
    SEND_EMAIL("http://210.211.109.180/drmuller/SendMail_VerifyAcc.php", "http://drqmuller.com/drmuller/SendMail_VerifyAcc.php"),
    SENDEMAIL_RESETPW("http://210.211.109.180/drmuller/SendMail_ResetPassword.php", "http://drqmuller.com/drmuller/SendMail_ResetPassword.php"),
    BOOKING("http://210.211.109.180/drmuller/BookingTransaction.php", "http://drqmuller.com/drmuller/BookingTransaction.php");

    private String url, url_uat;

    ModelURL(String url, String url_uat){
        this.url = url;
        this.url_uat = url_uat;
    }

    public String getUrl(boolean isUAT){
        if (isUAT)
            return this.url_uat;
        else
            return this.url;
    }
}