package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTODistrict {
    @SerializedName("DISTRICT_ID") private int districtId;
    @SerializedName("DISTRICT") private String districtName;

    public DTODistrict(int districtId, String districtName) {
        this.districtId = districtId;
        this.districtName = districtName;
    }

    public int getDistrictId() {
        return districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    @Override
    public String toString() {
        return districtName;
    }
}
