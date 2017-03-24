package com.lanthanh.admin.icareapp.presentation.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.presentation.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.base.BaseView;

import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public interface BookingActivityPresenter extends Presenter {
    interface View extends BaseView{
        void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags);
        void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags);
        void navigateActivity(Class activityClass);
        BookingActivityPresenter getMainPresenter();
        void onAddCartItem(String item);
        void onRemoveCartItem(String item);
        void onEmptyCart();
        void onRemoveCartItemColor(boolean isDone);
        void refreshCartIcon();
    }

    DTOAppointment getDTOAppointment();
    void navigateTab(int selected);
    List<Fragment> getVisibleFragments();
    void addCartItem();
    void removeCartItem(String item);
    void emptyCart();
    DTOAppointmentSchedule getSpecificSchedule(String item);
    void insertAppointment();
    void validateAppointment();
    void onBackPressed();
    boolean checkVerificationCodeExistence(String verificationCode);

}
