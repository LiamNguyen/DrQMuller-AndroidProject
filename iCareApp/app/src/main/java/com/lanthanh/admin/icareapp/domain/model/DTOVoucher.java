package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOVoucher {
    private int VOUCHER_ID;
    private String VOUCHER;

    public int getId(){
        return VOUCHER_ID;
    }

    public String getVoucherName(){
        return VOUCHER;
    }

    @Override
    public String toString() {
        return VOUCHER;
    }
}
