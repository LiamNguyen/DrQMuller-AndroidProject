package com.lanthanh.admin.icareapp.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ADMIN on 25-Oct-16.
 */

public class ModelUserInfo {
    private Map<String,String> info;

    public ModelUserInfo(){
        info = new HashMap<>();
    }

    public void addInfo(String k, String v) {
        info.put(k, v);
    }

    public boolean isInfoExist(String k) {
        return info.containsKey(k);
    }

    //Get email under post data for HTTP body ---> send email for activation
    public String getPostEmail(){
        StringBuilder result = new StringBuilder();

        try {
            result.append("email=").append(URLEncoder.encode(info.get("email"), "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return result.toString();
    }

    //Get all data under post data for HTTP body
    public String getPostData(){
        StringBuilder result = new StringBuilder();
        Boolean firstPara = true;

        for (String para: info.keySet()){
            if (!firstPara){
                result.append("&");
            }
            try {
                result.append(para).append("=").append(URLEncoder.encode(info.get(para), "UTF-8"));
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            firstPara = false;
        }

        return result.toString();
    }

    public String getID(){
        return info.get("cus_id");

    }

    public String getName(){
        return info.get("name");
    }

    public String getAddress(){
        return info.get("address");
    }

    public String getDOB(){
        return formatDate(info.get("dob"));
    }

    public String getGender(){
        return info.get("gender");
    }

    public String getEmail(){
        return info.get("email");
    }

    public String getPhone(){
        return info.get("phone");
    }

    public String formatDate(String s){
        StringBuilder builder = new StringBuilder();
        builder.append(s.substring(s.lastIndexOf("-")+1, s.length()));
        builder.append("-");
        builder.append(s.substring(s.indexOf("-")+1, s.lastIndexOf("-")));
        builder.append("-");
        builder.append(s.substring(0, s.indexOf("-")));
        return builder.toString();
    }
}
