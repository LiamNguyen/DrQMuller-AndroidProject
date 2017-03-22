package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOTimeSelected {
    @SerializedName("TIME_ID") private int selectedTimeId;
    @SerializedName("TIME") private String selectedTime;

    public DTOTimeSelected(int selectedTimeId, String selectedTime){
        this.selectedTimeId = selectedTimeId;
        this.selectedTime = selectedTime;
    }

    public int getSelectedTimeId() {
        return selectedTimeId;
    }

    public String getSelectedTime() {
        return selectedTime;
    }
}
