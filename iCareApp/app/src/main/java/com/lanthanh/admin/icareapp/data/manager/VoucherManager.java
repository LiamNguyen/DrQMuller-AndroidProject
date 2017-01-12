package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public interface VoucherManager extends Manager{
    String VOUCHER_ID_KEY = "voucher_id";
    String VOUCHER_NAME_KEY = "voucher";
    List<DTOVoucher> getAllVouchers();
}
