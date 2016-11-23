package com.lanthanh.admin.icareapp.Service;

/**
 * Created by ADMIN on 21-Nov-16.
 */

public class BookingItem {
    private String pref, name, location, voucher, startddate, enddate, status, code;

    public BookingItem(String pref, String name, String voucher, String location, String startdate, String enddate, String status, String code){
        this.pref = pref;
        this.name = name;
        this.location = location;
        this.voucher = voucher;
        this.startddate = startdate;
        this.enddate = enddate;
        this.status = status;
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public String getLocation(){
        return location;
    }

    public String getVoucher(){
        return voucher;
    }

    public String getStartDate(){
        return startddate;
    }

    public String getEndDate(){
        return enddate;
    }

    public String getStatus(){
        return status;
    }
}
