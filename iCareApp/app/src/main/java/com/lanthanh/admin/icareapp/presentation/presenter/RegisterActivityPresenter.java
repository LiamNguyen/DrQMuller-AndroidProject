package com.lanthanh.admin.icareapp.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.base.BaseView;

import java.util.List;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public interface RegisterActivityPresenter extends Presenter {
    interface View extends BaseView {
        void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags);
        void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags);
        void navigateActivity(Class activityClass, Bundle extras);
        void navigateActivity(Class activityClass);
        void backToHomeScreen();
        void hideSoftKeyboard();
        RegisterActivityPresenter getMainPresenter();
        String getStringResource(int id);
        void showAlertDialog(int id);
    }

    List<Fragment> getVisibleFragments();
    void navigateFragment(int selected);
    void onBackPressed();
    void navigateToUserInfo(int id, String uistep);
    void navigateToResetPW();
    void navigateToMainActivity();
    void logIn(String username, String password);
    void insertCustomer(String username, String password);
    void updateVerifyAcc(String id);
}
