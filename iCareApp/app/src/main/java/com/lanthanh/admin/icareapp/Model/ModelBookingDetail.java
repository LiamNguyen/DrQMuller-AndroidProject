package com.lanthanh.admin.icareapp.Model;

import com.auth0.jwt.internal.org.apache.commons.lang3.RandomStringUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class ModelBookingDetail {
    private String customer, location, voucher, type, start_date, expire_date, formatted_expire_date, code, address, voucherName;
    private Map<String,String> booking;
    private List<String> fullBooking;

    public ModelBookingDetail(){
        customer = ""; location = ""; voucher = ""; type = ""; start_date = "1111-11-11"; expire_date = ""; code = ""; address = ""; voucherName = "";
        booking = new HashMap<>();
    }

    //Customer ID
    public void setCustomerID(String s){
        customer = s;
    }

    public String getCustomerID(){
        return customer;
    }

    //Location ID
    public void setLocation(String locationID, String locationName, String districtName, String cityName, String countryName){
        StringBuilder result = new StringBuilder();
        location = locationID;
        result.append(locationName).append(", ").append(districtName).append(", ").append(cityName).append(", ").append(countryName);
        address = result.toString();
    }

    public String getLocation(){
        return address;
    }

    //Voucher ID
    public void setVoucher(String id, String name){
        voucher = id;
        voucherName = name;
    }

    public String getVoucherID(){
        return voucher;
    }

    public String getVoucher(){
        return voucherName;
    }

    //Type
    public void setType(String s){
        type = s;
    }

    public String getType(){
        return type;
    }

    //Start Date
    public void setStartDate(String s){
        start_date = s;
    }

    public String getStartDate(){
        return start_date;
    }

    public void clearStartDate(){
        start_date = "";
    }

    //Expire Date
    public void setExpireDate(String s){
        expire_date = s;
    }

    public void clearExpireDate(){
        expire_date = "";
    }

    public String getExpireDate(){
        return expire_date;
    }

    public void setFormattedExpireDate(String s){
        formatted_expire_date = s;
    }

    public String getFormattedExpireDate(){
        return formatted_expire_date;
    }

    //Generate Code
    public void generateCode(){
        code = RandomStringUtils.randomAlphanumeric(10);
    }

    public String getCode(){
        return code;
    }

    public boolean isDataEmpty(boolean isFixed){
        if (isFixed)
            return (location.isEmpty() || voucher.isEmpty() || type.isEmpty() || start_date.isEmpty() || expire_date.isEmpty());
        else
            return (location.isEmpty() || voucher.isEmpty() || type.isEmpty() || expire_date.isEmpty());
    }

    //Booking Day & Time
    public void saveBookingInfo(List l){
        fullBooking = l;
    }

    public void saveBooking(String day, String time){
        booking.put(day, time);
    }

    public void deleteBooking(String dayid){
        booking.remove(dayid);
    }

    public boolean checkDay(String s){
        return booking.containsKey(s);
    }

    public void emptyDay(){
        booking.clear();
    }

    public Set<String> getBookingDays(){
        return booking.keySet();
    }

    public String getBookingTime(String s){
        return booking.get(s);
    }

    public String getPostData() {
        StringBuilder result = new StringBuilder();

        try {
            result.append("customer_id=").append(customer)
                    .append("&location_id=").append(location)
                    .append("&voucher_id=").append(voucher)
                    .append("&type=").append(URLEncoder.encode(type, "UTF-8"))
                    .append("&start_date=").append(URLEncoder.encode(start_date, "UTF-8"))
                    .append("&expire_date=").append(URLEncoder.encode(expire_date, "UTF-8"))
                    .append("&code=").append(code);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return result.toString();
    }

    public String getEmailPostData() {
        StringBuilder result = new StringBuilder();
        Gson gson = new Gson();
        String typeName = "";
        if (type.equals("2"))
            typeName = "Tự Do";
        else
            typeName = "Cố Định";

        try {
            result.append("cus_id=").append(customer)
                    .append("&location=").append(address)
                    .append("&voucher=").append(voucherName)
                    .append("&type=").append(URLEncoder.encode(typeName, "UTF-8"))
                    .append("&start_date=").append(URLEncoder.encode(start_date, "UTF-8"))
                    .append("&expire_date=").append(URLEncoder.encode(expire_date, "UTF-8"))
                    .append("&code=").append(code)
                    .append("&bookings=").append(gson.toJson(fullBooking));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return result.toString();
    }
}
