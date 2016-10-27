package com.example.admin.icareapp.Model;

/**
 * Created by ADMIN on 26-Oct-16.
 */

public enum ModelURL {
    INSERT_NEWCUSTOMER("http://192.168.0.105/Insert_NewCustomer.php"),
    SELECT_TOAUTHENTICATE("http://192.168.0.105/Select_ToAuthenticate.php"),
    SELECT_CHECKUSEREXISTENCE("http://192.168.0.105/Select_CheckUserExistence.php"),
    SELECT_NoOFCUSTOMERS("http://192.168.0.105/Select_NumberOfCustomer.php"),
    UPDATE_CUSTOMERINFO("http://192.168.0.105/Update_CustomerInfo.php");

    private String url;

    private ModelURL(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
}
