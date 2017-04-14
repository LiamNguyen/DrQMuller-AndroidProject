package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOWeekDay {
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

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

    @Override
    public String toString() {
        return dayName;
    }
}
