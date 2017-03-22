package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOWeekDay {
    @SerializedName("DAY_ID") private int dayId;
    @SerializedName("DAY") private String dayName;

    public DTOWeekDay(int dayId, String dayName) {
        this.dayId = dayId;
        this.dayName = dayName;
    }

    public int getDayId() {
        return dayId;
    }

    public String getDayName() {
        return dayName;
    }
}
