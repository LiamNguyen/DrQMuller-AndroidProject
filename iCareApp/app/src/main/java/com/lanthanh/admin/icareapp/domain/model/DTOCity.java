package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOCity {
    private int CITY_ID;
    private String CITY;

    public int getId(){
        return CITY_ID;
    }

    public String getCityName(){
        return CITY;
    }

    @Override
    public String toString() {
        return CITY;
    }
}
