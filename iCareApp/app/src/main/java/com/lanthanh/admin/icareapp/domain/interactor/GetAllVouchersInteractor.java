package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface GetAllVouchersInteractor extends Interactor {
    interface Callback{
        void onNoVoucherFound();
        void onAllVouchersReceive(List<DTOVoucher> voucherList);
    }
}
