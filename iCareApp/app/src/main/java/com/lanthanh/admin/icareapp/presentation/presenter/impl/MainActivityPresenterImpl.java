package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.view.activity.BookingActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserDetailsActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.appointmenttab.AppointmentFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.appointmenttab.DefaultAppointmentFragment;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.usertab.UserFragment;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 31-Dec-16.
 */

public class MainActivityPresenterImpl extends AbstractPresenter implements MainActivityPresenter{
    private MainActivityPresenter.View mView;
    private ModelUser mUser;
    private FragmentManager fragmentManager;
    private AppointmentManager appointmentManager;
    private CustomerManager customerManager;
    private SharedPreferences sharedPreferences;
    //Fragments
    private AppointmentFragment appointmentFragment;
    private DefaultAppointmentFragment defaultAppointmentFragment;
    private UserFragment userFragment;

    public MainActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view,
                                     FragmentManager fragmentManager, AppointmentManager appointmentManager, CustomerManager customerManager){
        super(executor, mainThread);
        this.mView = view;
        this.fragmentManager = fragmentManager;
        this.appointmentManager = appointmentManager;
        this.customerManager = customerManager;
        this.sharedPreferences = sharedPreferences;
        init();
    }

    public void init(){
        defaultAppointmentFragment = new DefaultAppointmentFragment();
        appointmentFragment = new AppointmentFragment();
        userFragment = new UserFragment();
    }

    @Override
    public void resume() {

    }

    @Override
    public void navigateTab(int selected) {
        if (selected == MainActivity.APPOINTMENTTAB){
            List<DTOAppointment> dtoAppointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences);
            if ( dtoAppointmentsList != null && dtoAppointmentsList.size() != 0 ) {
                mView.showFragment(fragmentManager, appointmentFragment, getVisibleFragments());
            }else{
                mView.showFragment(fragmentManager, defaultAppointmentFragment, getVisibleFragments());
            }
        }else if (selected == MainActivity.USERTAB)
            mView.showFragment(fragmentManager, userFragment, getVisibleFragments());
    }

    @Override
    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result IF VISIBLE
        if (defaultAppointmentFragment.isVisible()) {
            result.add(defaultAppointmentFragment);
        }
        if (appointmentFragment.isVisible()) {
            result.add(appointmentFragment);
        }
        if (userFragment.isVisible()) {
            result.add(userFragment);
        }

        return result;
    }

    @Override
    public AppointmentChildView getAppointmentView() {
        return appointmentFragment;
    }

    @Override
    public boolean checkPrivilege() {
        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
        return mUser != null && mUser.getID() != 0 && mUser.getActive() != 0;
    }

    @Override
    public void navigateToBookingActivity() {
        mView.navigateActivity(BookingActivity.class);
    }

    @Override
    public void navigateToRegisterActivity() {
        mView.navigateActivity(RegisterActivity.class);
    }

    @Override
    public void navigateToUserDetailsActivity() {
        mView.navigateActivity(UserDetailsActivity.class);
    }

    @Override
    public void clearLocalStorage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    @Override
    public SharedPreferences getLocalStorage() {
        return sharedPreferences;
    }

    @Override
    public void updateAppointmentList() {
        //Get local appointment list
        List<DTOAppointment> dtoAppointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences);
        System.out.println("TESTTTTTT " + dtoAppointmentsList.size());
        if ( dtoAppointmentsList != null) {
            appointmentFragment.updateList(dtoAppointmentsList);
        }
    }
}
