package com.lanthanh.admin.icareapp.presentation.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author longv
 * Created on 20-Mar-17.
 */

public class
UserInfo {
    @SerializedName("customerId") private String id;
    @SerializedName("customerName") private String name;
    @SerializedName("address") private String address;
    @SerializedName("dob") private String dob;
    @SerializedName("gender") private String gender;
    @SerializedName("phone") private String phone;
    @SerializedName("email") private String email;
    @SerializedName("sessionToken") private String token;
    @SerializedName("jwt") private String jwt;
    @SerializedName("message") private String message;


    public UserInfo(String id, String name, String dob, String gender, String phone, String address, String email, String token, String jwt, String message){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.token = token;
        this.jwt = jwt;
        this.message = message;
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

    public String getMessage() {
        return message;
    }


    /**
     * Check whether user is valid (all information is provided)
     * @return boolean True if valid, else false
     * @see #isBasicInfoValid()}
     * @see #isNecessaryInfoValid()
     * @see #isImportantInfoValid()
     */
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

    /**
     * Check whether user's basic information is valid
     * @return boolean True if valid, else false
     * @see #isValid()
     * @see #isNecessaryInfoValid()
     * @see #isImportantInfoValid()
     */
    public boolean isBasicInfoValid() {
        boolean valid = false;
        try {
            valid = !name.isEmpty() && !address.isEmpty() && !id.isEmpty() && !token.isEmpty();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return valid;
    }

    /**
     * Check whether user's necessary information is valid
     * @return boolean True if valid, else false
     * @see #isValid()
     * @see #isBasicInfoValid()
     * @see #isImportantInfoValid()
     */
    public boolean isNecessaryInfoValid() {
        boolean valid = false;
        try {
            valid = !dob.isEmpty() && !gender.isEmpty() && !id.isEmpty() && !token.isEmpty();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return valid;
    }

    /**
     * Check whether user's important information is valid
     * @return boolean True if valid, else false
     * @see #isValid()
     * @see #isBasicInfoValid()
     * @see #isNecessaryInfoValid()
     */
    public boolean isImportantInfoValid() {
        boolean valid = false;
        try {
            valid = !phone.isEmpty() && !email.isEmpty() && !id.isEmpty() && !token.isEmpty();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return valid;
    }
}
