package com.lanthanh.admin.icareapp.Service;

/**
 * Created by ADMIN on 21-Nov-16.
 */

public class BookingItem {
    private String pref, name, location, voucher, startddate, enddate, status, code, app1, app2, app3, type;

    // p
    public BookingItem(String pref, String name, String voucher, String location, String startdate, String enddate, String status, String code, String app1, String app2, String app3, String type){
        this.pref = pref;
        this.name = name;
        this.location = location;
        this.voucher = voucher;
        this.startddate = startdate;
        this.enddate = enddate;
        this.status = status;
        this.code = code;
        this.app1 = app1;
        this.app2 = app2;
        this.app3 = app3;
        this.type = type;
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

    public String getCode(){
        return code;
    }

    public String getType() {
        return type;
    }
    
    public String getApp1(){
        return app1;
    }

    public String getApp2(){
        return app2;
    }

    public String getApp3(){
        return app3;
    }
}
