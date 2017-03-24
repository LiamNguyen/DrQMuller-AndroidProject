package com.lanthanh.admin.icareapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author longv
 *         Created on 24-Mar-17.
 */

public class BookedTime {
    @SerializedName("dayId") private int dayId;
    @SerializedName("timeId") private int timeId;
    @SerializedName("machineId") private int machineId;

    public BookedTime(int dayId, int timeId, int machineId) {
        this.dayId = dayId;
        this.timeId = timeId;
        this.machineId = machineId;
    }
}
