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

public interface RegisterActivityPresenter extends Presenter {
    interface View extends BaseView {
        void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags);
        void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags);
        void navigateActivity(Class activityClass);
        void navigateActivity(Class activityClass, String extra);
        void backToHomeScreen();
        void hideSoftKeyboard();
        RegisterActivityPresenter getMainPresenter();
        String getStringResource(int id);
    }

    List<Fragment> getVisibleFragments();
    void navigateFragment(int selected);
    void navigateBack();
    void onBackPressed();
    void navigateToUserInfo(String username);
    void navigateToResetPW();
    void navigateToMainActivity();
    void logIn(String username, String password);
    void checkUserExistence(String username, String password);
}
