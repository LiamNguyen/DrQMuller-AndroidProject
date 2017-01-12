package com.lanthanh.admin.icareapp.presentation.model;

import java.util.Date;

/**
 * Created by ADMIN on 03-Jan-17.
 */

public class ModelUser {
    private int id, active, step;
    private String name, address, gender, email, phone, dob;

    public ModelUser(){};

    public ModelUser(int id, int active, int step, String name, String gender, String dob, String address, String email, String phone){
        this.id = id;
        this.active = active;
        this.step = step;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setDob(String dob){
        this.dob = dob;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public int getID(){
        return id;
    }

    public int getActive(){
        return active;
    }

    public int getStep() {
        return step;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getDOB(){
        return dob;
    }

    public String getGender(){
        return gender;
    }
}
