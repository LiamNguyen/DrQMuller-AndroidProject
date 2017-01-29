package com.lanthanh.admin.icareapp.presentation.presenter;

import android.app.Activity;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.BaseView;

import java.util.List;

/**
 * Created by ADMIN on 02-Jan-17.
 */

public interface MainActivityPresenter extends Presenter{
    interface View extends BaseView {
        void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags);
        void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags);
        void navigateActivity(Class activityClass);
        MainActivityPresenter getMainPresenter();
        int getSelectedTab();
    }

    interface AppointmentChildView extends BaseView{
        void updateList(List<DTOAppointment> list);
    }

    void navigateTab(int selected);
    List<Fragment> getVisibleFragments();
    AppointmentChildView getAppointmentView();
    boolean checkPrivilege();
    void navigateToRegisterActivity();
    void navigateToUserDetailsActivity();
    void navigateToBookingActivity();
    void clearLocalStorage();
    SharedPreferences getLocalStorage();

    void updateAppointmentList();
}
