package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOLocation {
    @SerializedName("LOCATION_ID") private int locationId;
    @SerializedName("ADDRESS") private String address;

    public DTOLocation(int locationId, String address){
        this.locationId = locationId;
        this.address = address;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }
}
