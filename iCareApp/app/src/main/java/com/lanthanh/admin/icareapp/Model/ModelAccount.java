package com.lanthanh.admin.icareapp.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ADMIN on 28-Oct-16.
 */

public class ModelAccount {
    private String username;
    private String password;

    public void setLoginID(String username){
        this.username = username;
    }

    public String getPostUsername(){
        StringBuilder result = new StringBuilder();

        try {
            result.append("username=").append(URLEncoder.encode(username, "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return result.toString();
    }

    public void setPassword(String pw){
        password = pw;
    }

    public String getPostData(){
        StringBuilder result = new StringBuilder();

        try {
            result.append("username=").append(URLEncoder.encode(username, "UTF-8")).append("&password=").append(URLEncoder.encode(password, "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return result.toString();
    }

}