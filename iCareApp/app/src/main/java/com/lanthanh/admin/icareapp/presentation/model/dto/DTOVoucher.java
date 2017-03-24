package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOVoucher {
    @SerializedName("VOUCHER_ID") private int voucherId;
    @SerializedName("VOUCHER") private String voucherName;
    @SerializedName("PRICE") private int voucherPrice;

    public DTOVoucher(int voucherId, String voucherName, int voucherPrice){
        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.voucherPrice = voucherPrice;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public int getVoucherPrice() {
        return voucherPrice;
    }

    @Override
    public String toString() {
        return voucherName + " - " + voucherPrice + " VND";
    }
}
