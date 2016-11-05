package com.example.admin.icareapp.Model;

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
        System.out.println(result.toString());
        return result.toString();
    }
}
