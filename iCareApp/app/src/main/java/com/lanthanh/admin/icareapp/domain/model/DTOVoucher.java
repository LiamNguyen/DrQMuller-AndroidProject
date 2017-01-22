package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOVoucher {
    private int VOUCHER_ID;
    private String VOUCHER;
    private String PRICE;

    public int getId(){
        return VOUCHER_ID;
    }

    public String getVoucherName(){
        return VOUCHER;
    }

    public String getPrice(){
        return PRICE;
    }

    @Override
    public String toString() {
        return VOUCHER + " - " + PRICE + " VND";
    }
}
