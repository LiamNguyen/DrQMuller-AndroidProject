package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

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
import com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword.EmailForResetFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword.ResetPasswordFragment;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public class ResetPasswordActivityPresenterImpl extends AbstractPresenter implements ResetPasswordActivityPresenter,
        SendEmailResetPasswordInteractor.Callback, UpdateCustomerPasswordInteractor.Callback {
    private ResetPasswordActivityPresenter.View mView;
    private SendEmailManager sendEmailManager;
    private CustomerManager customerManager;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private EmailForResetFragment emailForResetFragment;
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
        emailForResetFragment = new EmailForResetFragment();
        resetPasswordFragment = new ResetPasswordFragment();
    }

    @Override
    public void navigateTab(int selected) {
        if (selected == ResetPasswordActivity.EMAIL_FOR_RESET)
            mView.showFragment(fragmentManager, emailForResetFragment, getVisibleFragments());
        else if (selected == ResetPasswordActivity.RESET_PW)
            mView.showFragment(fragmentManager, resetPasswordFragment, getVisibleFragments());
    }

    @Override
    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(2);

        // Add each visible fragment to the result IF VISIBLE
        if (emailForResetFragment.isVisible()) {
            result.add(emailForResetFragment);
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
    public void sendEmailToResetPassword(String email, String username) {
        SendEmailResetPasswordInteractor sendEmailResetPasswordInteractor = new SendEmailResetPasswordInteractorImpl(mExecutor, mMainThread, this, sendEmailManager, email, username);
        sendEmailResetPasswordInteractor.execute();
    }

    @Override
    public void onEmailNotSent() {
        emailForResetFragment.showEmailResult(R.string.validate_noti_fail, R.color.colorLightRed);
    }

    @Override
    public void onEmailSent() {
        emailForResetFragment.showEmailResult(R.string.validate_noti_success, R.color.colorGreen);
    }

    @Override
    public void updateCustomerPassword(String username, String password) {
        UpdateCustomerPasswordInteractor updateCustomerPasswordInteractor = new UpdateCustomerPasswordInteractorImpl(mExecutor, mMainThread, this, customerManager, username, password);
        updateCustomerPasswordInteractor.execute();
    }

    @Override
    public void onResetPasswordFail() {
        mView.navigateToRegisterActivity(0);
    }

    @Override
    public void onResetPasswordSuccess() {
        mView.navigateToRegisterActivity(1);
    }
}
