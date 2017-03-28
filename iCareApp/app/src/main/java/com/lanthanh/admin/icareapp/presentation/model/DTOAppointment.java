package com.lanthanh.admin.icareapp.presentation.model;

//import com.auth0.jwt.internal.org.apache.commons.lang3.RandomStringUtils;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOAppointment {
    private UserInfo user;
    private int appointmentId;
    private DTOCountry country;
    private DTOCity city;
    private DTODistrict district;
    private DTOLocation location;
    private DTOVoucher voucher;
    private DTOType type;
    private Date startDate, expireDate;
    private List<DTOAppointmentSchedule> appointmentScheduleList;
    private DTOAppointmentSchedule currentSchedule;
    private String verificationCode;
    private boolean status;

    public DTOAppointment(){
        appointmentScheduleList = new ArrayList<>();
        status = false;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
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


    public DTOAppointmentSchedule getCurrentSchedule() {
        if (currentSchedule == null) {
            currentSchedule = new DTOAppointmentSchedule();
        }
        return currentSchedule;
    }

    public void setCurrentSchedule(DTOAppointmentSchedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode() {
       // this.verificationCode = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
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

    public boolean isFirstSelectFilled(){
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

    public boolean isMachineFilled(){
        return currentSchedule != null && currentSchedule.getBookedMachine() != null;
    }
}
