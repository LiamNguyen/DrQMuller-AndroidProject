package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.SendEmailResetPasswordInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateCustomerPasswordInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.SendEmailResetPasswordInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateCustomerPasswordInteractorImpl;
import com.lanthanh.admin.icareapp.presentation.presenter.ResetPasswordActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword.UsernameForResetFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword.ResetPasswordFragment;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public class ResetPasswordActivityPresenterImpl extends AbstractPresenter implements ResetPasswordActivityPresenter,
        SendEmailResetPasswordInteractor.Callback, UpdateCustomerPasswordInteractor.Callback {
    public static final String TAG = ResetPasswordActivityPresenterImpl.class.getSimpleName();
    private ResetPasswordActivityPresenter.View mView;
    private SendEmailManager sendEmailManager;
    private CustomerManager customerManager;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private UsernameForResetFragment usernameForResetFragment;
    private ResetPasswordFragment resetPasswordFragment;

    public ResetPasswordActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view,
                                              FragmentManager fragmentManager, SendEmailManager sendEmailManager, CustomerManager customerManager){
        super(executor, mainThread);
        mView = view;
        this.sendEmailManager = sendEmailManager;
        this.fragmentManager = fragmentManager;
        this.customerManager = customerManager;
        this.sharedPreferences = sharedPreferences;
        init();
    }

    @Override
    public void resume() {

    }

    public void init(){
        usernameForResetFragment = new UsernameForResetFragment();
        resetPasswordFragment = new ResetPasswordFragment();
    }

    @Override
    public void navigateTab(int selected) {
        if (selected == ResetPasswordActivity.USERNAME_FOR_RESET)
            mView.showFragment(fragmentManager, usernameForResetFragment, getVisibleFragments());
        else if (selected == ResetPasswordActivity.RESET_PW)
            mView.showFragment(fragmentManager, resetPasswordFragment, getVisibleFragments());
    }

    @Override
    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(2);

        // Add each visible fragment to the result IF VISIBLE
        if (usernameForResetFragment.isVisible()) {
            result.add(usernameForResetFragment);
        }
        if (resetPasswordFragment.isVisible()) {
            result.add(resetPasswordFragment);
        }
        return result;
    }

    @Override
    public void setResetPWFragmentArguments(Bundle b) {
        resetPasswordFragment.setArguments(b);
    }

    @Override
    public void sendEmailToResetPassword(String username) {
        SendEmailResetPasswordInteractor sendEmailResetPasswordInteractor = new SendEmailResetPasswordInteractorImpl(mExecutor, mMainThread, this, sendEmailManager, username);
        sendEmailResetPasswordInteractor.execute();
    }

    @Override
    public void onEmailResetPasswordNotSent() {
        try {
            System.out.println("Email sent fail");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onUsernameOrEmailNotFound() {
        try {
            usernameForResetFragment.showEmailResult(R.string.email_reset_fail);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onEmailResetPasswordSent() {
        try {
            usernameForResetFragment.showEmailResult(R.string.email_reset_success);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void updateCustomerPassword(String username, String password) {
        UpdateCustomerPasswordInteractor updateCustomerPasswordInteractor = new UpdateCustomerPasswordInteractorImpl(mExecutor, mMainThread, this, customerManager, username, password);
        updateCustomerPasswordInteractor.execute();
    }

    @Override
    public void onResetPasswordFail() {
        try {
            mView.navigateToRegisterActivity(ResetPasswordActivity.FAIL);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onResetPasswordSuccess() {
        try {
            mView.navigateToRegisterActivity(ResetPasswordActivity.SUCCESS);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        if (usernameForResetFragment.isVisible()) {
            mView.navigateToRegisterActivity();
        }else if (resetPasswordFragment.isVisible()) {
            navigateTab(ResetPasswordActivity.USERNAME_FOR_RESET);
        }
    }
}
