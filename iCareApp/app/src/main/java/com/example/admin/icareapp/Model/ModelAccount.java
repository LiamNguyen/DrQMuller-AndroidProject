package com.example.admin.icareapp.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ADMIN on 28-Oct-16.
 */

public class ModelAccount {
    private String loginID;
    private String password;



    public void setLoginID(String id){
        loginID = id;
    }

    public String getPostLoginID(){
        StringBuilder result = new StringBuilder();

        result.append("login_id=").append(loginID);

        return result.toString();
    }

    public void setPassword(String pw){
        password = pw;
    }

    public String getPostData(){
        StringBuilder result = new StringBuilder();

        result.append("login_id=").append(loginID).append("&password=").append(password);
        System.out.println(result.toString());
        return result.toString();
    }

}
