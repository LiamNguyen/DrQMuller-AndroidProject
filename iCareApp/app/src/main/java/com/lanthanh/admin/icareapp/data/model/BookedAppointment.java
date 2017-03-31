package com.lanthanh.admin.icareapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author longv
 *         Created on 24-Mar-17.
 */

public class BookedAppointment {
    @SerializedName("userId") private String userId;
    @SerializedName("startDate") private Date startDate;
    @SerializedName("expiredDate") private Date expireDate;
    @SerializedName("typeId") private int typeId;
    @SerializedName("locationId") private int locationId;
    @SerializedName("voucherId") private int voucherId;
    @SerializedName("verificationCode") private String verificationCode;
    @SerializedName("time") private List<BookedSchedule.BookedTime> bookedTimes;

    public BookedAppointment(String userId, Date startDate, Date expireDate, int typeId, int locationId, int voucherId, String verificationCode, List<BookedSchedule.BookedTime> bookedTimes) {
        this.userId = userId;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.typeId = typeId;
        this.locationId = locationId;
        this.voucherId = voucherId;
        this.verificationCode = verificationCode;
        this.bookedTimes = bookedTimes;
    }
}
