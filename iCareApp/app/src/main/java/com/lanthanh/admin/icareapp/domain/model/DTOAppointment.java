package com.lanthanh.admin.icareapp.domain.model;

import com.auth0.jwt.internal.org.apache.commons.lang3.RandomStringUtils;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOAppointment {
    private ModelUser user;
    private DTOCountry country;
    private DTOCity city;
    private DTODistrict district;
    private DTOLocation location;
    private DTOVoucher voucher;
    private DTOType type;
    private Date startDate, expireDate;
    private List<DTOAppointmentSchedule> appointmentScheduleList;
    private String VERIFICATION_CODE;
    private boolean status;

    public DTOAppointment(){
        appointmentScheduleList = new ArrayList<>();
        status = false;
    }

    public void setCustomer(ModelUser user){
        this.user = user;
    }

    public void setCountry(DTOCountry country){
        this.country = country;
    }

    public void setCity(DTOCity city){
        this.city = city;
    }

    public void setDistrict(DTODistrict district){
        this.district = district;
    }

    public void setLocation(DTOLocation location){
        this.location = location;
    }

    public void setVoucher(DTOVoucher voucher){
        this.voucher = voucher;
    }

    public void setType(DTOType type){
        this.type = type;
    }

    public void setStartDate(Date date){
        startDate = date;
    }

    public void setExpireDate(Date date){
        expireDate = date;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void generateVerficationCode(){
        VERIFICATION_CODE = RandomStringUtils.randomAlphanumeric(10);
    }

    public void addAppointment(DTOAppointmentSchedule appointment){
        appointmentScheduleList.add(appointment);
    }

    public int getCustomerId(){
        return user.getID();
    }

    public int getCountryId(){
        return country.getId();
    }

    public int getCityId(){
        return city.getId();
    }

    public int getDistrictId(){
        return district.getId();
    }

    public int getLocationId(){
        return location.getId();
    }

    public int getVoucherId(){
        return voucher.getId();
    }

    public int getTypeId(){
        return type.getId();
    }

    public Date getStartDate(){
        return startDate;
    }

    public Date getExpireDate(){
        return expireDate;
    }

    public String getVerficationCode(){
        return VERIFICATION_CODE;
    }

    public List<DTOAppointmentSchedule> getAppointmentScheduleList(){
        return appointmentScheduleList;
    }

    public String getCustomerName(){
        return user.getName();
    }

    public String getCountryName(){
        return country.getCountryName();
    }

    public String getCityName(){
        return city.getCityName();
    }

    public String getDistrictName(){
        return district.getDistrictName();
    }

    public String getLocationName(){
        return location.getLocationName();
    }

    public String getVoucherName(){
        return voucher.getVoucherName();
    }

    public String getTypeName(){
        return type.getTypeName();
    }

    public boolean getStatus(){
        return status;
    }

    public boolean isSelectFilled(){
        if (type.getId() == 1)
            return user.getID() !=0 && country.getId() != 0 && city.getId() !=0 && district.getId() != 0 && location.getId() != 0 && voucher.getId() != 0 && startDate != null && expireDate != null;
        else if (type.getId() == 2)
            return user.getID() !=0 && country.getId() != 0 && city.getId() !=0 && district.getId() != 0 && location.getId() != 0 && voucher.getId() != 0 && expireDate != null;
        else
            return false;
    }
}
