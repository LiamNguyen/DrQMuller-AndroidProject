package com.lanthanh.admin.icareapp.presentation.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.BaseView;

import java.util.List;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public interface UserInfoActivityPresenter extends Presenter {
    interface View extends BaseView {
        void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags);
        void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags);
        void navigateActivity(Class activityClass);
        void hideSoftKeyboard();
        UserInfoActivityPresenter getMainPresenter();
        String getStringResource(int id);
    }

    List<Fragment> getVisibleFragments();
    void navigateFragment(int selected);
    void onBackPressed();
    void navigateToMainActivity();
    void navigateToRegisterActivity();
    void getCustomerId();
    void setUsername(String username);
    void setName(String name);
    void setAddress(String address);
    void setDob(String dob);
    void setGender(String gender);
    void setEmail(String email);
    void setPhone(String phone);
    boolean isDobSet();
    boolean isGenderSet();
    void updateCustomer();
    void sendEmailVerifyAcc();
    void onEmailChange(int string);
}
