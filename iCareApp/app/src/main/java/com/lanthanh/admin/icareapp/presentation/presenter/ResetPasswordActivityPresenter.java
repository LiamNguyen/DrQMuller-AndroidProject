package com.lanthanh.admin.icareapp.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.base.BaseView;

import java.util.List;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public interface ResetPasswordActivityPresenter extends Presenter {
    interface View extends BaseView {
        void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags);
        void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags);
        ResetPasswordActivityPresenter getMainPresenter();
        void navigateToRegisterActivity(int result);
        void navigateToRegisterActivity();
    }

    void navigateTab(int selected);
    List<Fragment> getVisibleFragments();
    void sendEmailToResetPassword(String username);
    void setResetPWFragmentArguments(Bundle b);
    void updateCustomerPassword(String username, String password);
    void onBackPressed();
}
