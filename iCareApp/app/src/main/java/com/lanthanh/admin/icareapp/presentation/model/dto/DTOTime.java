package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOTime {
    @SerializedName("TIME_ID") private int timeId;
    @SerializedName("TIME") private String time;

    public DTOTime(int timeId, String time) {
        this.timeId = timeId;
        this.time = time;
    }

    public int getTimeId() {
        return timeId;
    }

    public String getTime() {
        return time;
    }
}
