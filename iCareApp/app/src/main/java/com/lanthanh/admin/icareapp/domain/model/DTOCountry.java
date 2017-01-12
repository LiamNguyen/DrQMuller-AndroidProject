package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public class DTOCountry {
    private int COUNTRY_ID;
    private String COUNTRY;

    public int getId(){
        return COUNTRY_ID;
    }

    public String getCountryName(){
        return COUNTRY;
    }

    @Override
    public String toString() {
        return COUNTRY;
    }
}
