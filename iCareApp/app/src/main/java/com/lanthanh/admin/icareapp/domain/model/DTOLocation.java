package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOLocation {
    private int LOCATION_ID;
    private String ADDRESS;

    public int getId(){
        return LOCATION_ID;
    }

    public String getLocationName(){
        return ADDRESS;
    }

    @Override
    public String toString() {
        return ADDRESS;
    }
}
