package com.lanthanh.admin.icareapp.presentation.presenter;

import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.BaseView;

import java.util.List;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public interface BookingDetailsActivityPresenter extends Presenter {
    interface View extends BaseView{
        void updateList(List<DTOAppointment> list);
        void navigateToMainActivity();
        BookingDetailsActivityPresenter getMainPresenter();
    }

    void updateList();
    int getNumberOfAppointments();
}
