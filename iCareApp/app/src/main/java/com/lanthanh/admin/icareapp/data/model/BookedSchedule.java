package com.lanthanh.admin.icareapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author longv
 *         Created on 24-Mar-17.
 */

public class BookedSchedule {
    @SerializedName("locationId") private int locationId;
    @SerializedName("time") private List<BookedTime> bookedTimeList;

    public BookedSchedule(int locationId, List<BookedTime> bookedTimeList) {
        this.locationId = locationId;
        this.bookedTimeList = bookedTimeList;
    }

    public static class BookedTime {
        @SerializedName("dayId") private int dayId;
        @SerializedName("timeId") private int timeId;
        @SerializedName("machineId") private int machineId;

        public BookedTime(int dayId, int timeId, int machineId) {
            this.dayId = dayId;
            this.timeId = timeId;
            this.machineId = machineId;
        }
    }
}
