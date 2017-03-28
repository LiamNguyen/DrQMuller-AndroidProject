package com.lanthanh.admin.icareapp.presentation.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author longv
 * Created on 20-Mar-17.
 */

public class UserInfo {
    @SerializedName("customerId") private String id;
    @SerializedName("customerName") private String name;
    @SerializedName("dob") private String dob;
    @SerializedName("gender") private String gender;
    @SerializedName("phone") private String phone;
    @SerializedName("address") private String address;
    @SerializedName("email") private String email;
    @SerializedName("sessionToken") private String token;
    @SerializedName("jwt") private String jwt;


    public UserInfo(String id, String name, String dob, String gender, String phone, String address, String email, String token, String jwt){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.token = token;
        this.jwt = jwt;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDateOfBirth(){
        return dob;
    }

    public String getGender(){
        return gender;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }

    public String getToken(){
        return token;
    }

    public boolean isValid() {
        boolean valid = false;
        try {
            valid = !name.isEmpty() && !dob.isEmpty() && !gender.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !email.isEmpty()
                    && !id.isEmpty() && !token.isEmpty();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return valid;
    }
}
