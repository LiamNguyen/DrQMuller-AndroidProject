package com.lanthanh.admin.icareapp.presentation.homepage;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.bookingpage.BookingActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab.AppointmentFragment;
import com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab.DefaultAppointmentFragment;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.homepage.usertab.UserFragment;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 31-Dec-16.
 */

public class MainActivityPresenterImpl extends BasePresenter {
    public static final String TAG = MainActivityPresenterImpl.class.getSimpleName();
    private MainActivityPresenter.View mView;
    private ModelUser mUser;
    private MainActivity activity;
    //Fragments
    private AppointmentFragment appointmentFragment;
    private DefaultAppointmentFragment defaultAppointmentFragment;
    private UserFragment userFragment;

    public MainActivityPresenterImpl(MainActivity activity){
        this.activity = activity;
        init();
    }

    public void init(){
        defaultAppointmentFragment = new DefaultAppointmentFragment();
        appointmentFragment = new AppointmentFragment();
        userFragment = new UserFragment();
    }

    @Override
    public void resume() {
//        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
    }

    public void navigateFragment(Class<? extends Fragment> fragmentClass) {
        if (fragmentClass == AppointmentFragment.class || fragmentClass == DefaultAppointmentFragment.class)
            showFragment(defaultAppointmentFragment);
        else if (fragmentClass == UserFragment.class)
            showFragment(userFragment);
    }


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

    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    public void showFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        //Hide all current visible fragment
        hideFragments(fragmentTransaction, getVisibleFragments());

        if (!f.isAdded()){
            fragmentTransaction.add(R.id.wel_fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

//
//    @Override
//    public AppointmentChildView getAppointmentView() {
//        return appointmentFragment;
//    }


    public boolean checkPrivilege() {
        //mUser = customerManager.getLocalUserFromPref(sharedPreferences);
        return mUser != null && mUser.getID() != 0 && mUser.getActive() != 0;
    }


    public void navigateToBookingActivity() {
        mView.navigateActivity(BookingActivity.class);
    }

//    @Override
//    public void navigateToRegisterActivity() {
//        mView.navigateActivity(RegisterActivity.class);
//    }

//    @Override
//    public void navigateToUserDetailsActivity() {
//        mView.navigateActivity(UserDetailsActivity.class);
//    }
//
//    @Override
//    public void clearLocalStorage() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("user");
//        editor.apply();
//        editor.commit();
//    }
//
//    @Override
//    public SharedPreferences getLocalStorage() {
//        return sharedPreferences;
//    }
//
//    @Override
//    public void updateAppointmentList() {
//        //Get local appointment list
//        List<DTOAppointment> dtoAppointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
//
//        if ( dtoAppointmentsList != null) {
//            appointmentFragment.updateList(dtoAppointmentsList);
//        }else{
//            mView.showFragment(fragmentManager, defaultAppointmentFragment, getVisibleFragments());
//        }
//    }
//
//    @Override
//    public void cancelAppointment(int appointmentId) {
//        mView.showProgress();
//        CancelAppointmentInteractor cancelAppointmentInteractor = new CancelAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointmentId);
//        cancelAppointmentInteractor.execute();

//
//    public void onCancelAppointmentFail() {
//        try {
//            mView.hideProgress();
//            Log.e(TAG, "Cancel appointment fail");
//        }catch (Exception e){
//            Log.w(TAG, e.toString());
//        }
//    }
//
//    public void onCancelAppointmentSuccess(int appointmentId) {
//        try {
//            mView.hideProgress();
//            //Get appointment from local shared pref
//            List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
//            if (appointmentsList == null)
//                return;
//            //Remove appointment
//            int index = -1;
//            for (int i = 0; i < appointmentsList.size(); i++){
//                if (appointmentsList.get(i).getAppointmentId() == appointmentId){
//                    index = i;
//                    break;
//                }
//            }
//            if (index != -1)
//                appointmentsList.remove(index);
//            //Put to shared pref
//            appointmentManager.saveLocalAppointmentsToPref(sharedPreferences, appointmentsList, mUser.getID());
//            //Update list
//            //updateAppointmentList();
//        }catch (Exception e){
//            Log.w(TAG, e.toString());
//        }
//    }
}
