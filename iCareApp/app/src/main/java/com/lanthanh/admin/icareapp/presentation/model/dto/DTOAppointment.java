package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.lanthanh.admin.icareapp.presentation.model.UserInfo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOAppointment {
    private UserInfo user;
    private String appointmentId;
    private DTOCountry country;
    private DTOCity city;
    private DTODistrict district;
    private DTOLocation location;
    private DTOVoucher voucher;
    private DTOType type;
    private Date startDate, expireDate;
    private List<DTOAppointmentSchedule> appointmentScheduleList;
    private String verificationCode;
    private boolean status;

    private boolean isEmailSent;

    public DTOAppointment(){
        appointmentScheduleList = new ArrayList<>();
        status = false;
        isEmailSent = false;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public DTOCountry getCountry() {
        return country;
    }

    public void setCountry(DTOCountry country) {
        this.country = country;
    }

    public DTOCity getCity() {
        return city;
    }

    public void setCity(DTOCity city) {
        this.city = city;
    }

    public DTODistrict getDistrict() {
        return district;
    }

    public void setDistrict(DTODistrict district) {
        this.district = district;
    }

    public DTOLocation getLocation() {
        return location;
    }

    public void setLocation(DTOLocation location) {
        this.location = location;
    }

    public DTOVoucher getVoucher() {
        return voucher;
    }

    public void setVoucher(DTOVoucher voucher) {
        this.voucher = voucher;
    }

    public DTOType getType() {
        return type;
    }

    public void setType(DTOType type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(boolean emailSent) {
        isEmailSent = emailSent;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String code) {
        this.verificationCode = code;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DTOAppointmentSchedule> getAppointmentScheduleList(){
        if (appointmentScheduleList == null) {
            appointmentScheduleList = new ArrayList<>();
        }
        return appointmentScheduleList;
    }

    public void setAppointmentScheduleList (List<DTOAppointmentSchedule> appointmentScheduleList) {
        this.appointmentScheduleList = appointmentScheduleList;
    }

    public boolean isBasicSelectFilled(){
        return country != null && city != null && district != null && location != null && voucher != null && type != null;
    }

    public boolean isDateSelectFilled(){
        if (type != null) {
            if (type.getTypeId() == 1)
                return startDate != null && expireDate != null;
            else
                return expireDate != null;
        }else
            return false;
    }

    public boolean isScheduleSelectFilled(){
        return appointmentScheduleList != null && appointmentScheduleList.size() > 0;
    }
}
