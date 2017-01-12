package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTODistrict {
    private int DISTRICT_ID;
    private String DISTRICT;

    public int getId(){
        return DISTRICT_ID;
    }

    public String getDistrictName(){
        return DISTRICT;
    }

    @Override
    public String toString() {
        return DISTRICT;
    }
}
