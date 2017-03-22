package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOTimeEco {
    @SerializedName("TIME_ID") private int ecoTimeId;
    @SerializedName("TIME") private String ecoTime;

    public DTOTimeEco(int ecoTimeId, String ecoTime) {
        this.ecoTimeId = ecoTimeId;
        this.ecoTime = ecoTime;
    }

    public int getEcoTimeId() {
        return ecoTimeId;
    }

    public String getEcoTime() {
        return ecoTime;
    }
}
