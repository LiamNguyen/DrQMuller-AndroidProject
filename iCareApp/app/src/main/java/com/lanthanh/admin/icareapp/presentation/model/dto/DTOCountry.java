package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOCountry {
    @SerializedName("COUNTRY_ID") private int countryId;
    @SerializedName("COUNTRY") private int countryName;

    public DTOCountry(int countryId, int countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getCountryName() {
        return countryName;
    }
}
