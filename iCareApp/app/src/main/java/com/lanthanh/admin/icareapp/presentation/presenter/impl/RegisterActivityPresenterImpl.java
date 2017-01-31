package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.auth0.jwt.JWTVerifier;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.CheckUserExistenceInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.InsertNewCustomerInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.LogInInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateVerifyAccInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.CheckUserExistenceInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.InsertNewCustomerInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.LogInInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateVerifyAccInteractorImpl;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.ChooseFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.SignInFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.SignUpFragment;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class RegisterActivityPresenterImpl extends AbstractPresenter implements RegisterActivityPresenter,
            LogInInteractor.Callback, CheckUserExistenceInteractor.Callback, InsertNewCustomerInteractor.Callback, UpdateVerifyAccInteractor.Callback{
    public static final String TAG = RegisterActivityPresenterImpl.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private RegisterActivityPresenter.View mView;
    private FragmentManager fragmentManager;
    private CustomerManager customerManager;
    private ChooseFragment chooseFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    public RegisterActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view,
                                         FragmentManager fragmentManager, CustomerManager customerManager){
        super(executor, mainThread);
        mView = view;
        this.sharedPreferences = sharedPreferences;
        this.fragmentManager = fragmentManager;
        this.customerManager = customerManager;
        init();
    }

    public void init(){
        chooseFragment = new ChooseFragment();
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
    }

    @Override
    public void resume() {

    }

    @Override
    public void navigateFragment(int selected) {
        if (selected == RegisterActivity.CHOOSE)
            mView.showFragment(fragmentManager, chooseFragment, getVisibleFragments());
        else if (selected == RegisterActivity.LOG_IN)
            mView.showFragment(fragmentManager, signInFragment, getVisibleFragments());
        else if (selected == RegisterActivity.SIGN_UP)
            mView.showFragment(fragmentManager, signUpFragment, getVisibleFragments());
    }

    @Override
    public void navigateBack() {
        fragmentManager.popBackStack();
        mView.hideSoftKeyboard();
    }

    @Override
    public void onBackPressed() {
        if (chooseFragment.isVisible()){
            mView.backToHomeScreen();
        }else
            navigateBack();
    }

    @Override
    public List<Fragment> getVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result
        if (chooseFragment.isVisible()) {
            result.add(chooseFragment);
        }
        if (signInFragment.isVisible()) {
            result.add(signInFragment);
        }
        if (signUpFragment.isVisible()) {
            result.add(signUpFragment);
        }

        return result;
    }

    @Override
    public void navigateToMainActivity() {
        Bundle extras = new Bundle();
        extras.putInt(RegisterActivity.LOGIN_STATUS, RegisterActivity.LOGGED_IN);
        mView.navigateActivity(MainActivity.class, extras);
    }

    @Override
    public void navigateToUserInfo(String username, int uiStep) {
        Bundle extras = new Bundle();
        extras.putString(RegisterActivity.EXTRA_USERNAME, username);
        extras.putInt(RegisterActivity.EXTRA_UISTEP, uiStep);
        mView.navigateActivity(UserInfoActivity.class, extras);
    }

    @Override
    public void navigateToResetPW() {
        mView.navigateActivity(ResetPasswordActivity.class);
    }

    @Override
    public void logIn(String username, String password) {
        LogInInteractor logInInteractor = new LogInInteractorImpl(mExecutor, mMainThread, this, customerManager, username, password);
        logInInteractor.execute();
        mView.hideSoftKeyboard();
    }

    @Override
    public void onLogInFail() {
        try {
            mView.showError("Tên Đăng Nhập Hoặc Mật Khẩu Sai");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onLogInSuccess(String jwt, String username) {
        try {
            final JWTVerifier verifier = new JWTVerifier("drmuller");
            try {
                final Map<String, String> jwtClaims = (Map<String, String>) verifier.verify(jwt).get("data");
                ModelUser user = new ModelUser(Integer.parseInt(jwtClaims.get("userId")),
                        Integer.parseInt(jwtClaims.get("active")),
                        Integer.parseInt(jwtClaims.get("step")),
                        jwtClaims.get("userName"),
                        jwtClaims.get("userGender"),
                        jwtClaims.get("userDob"),
                        jwtClaims.get("userAddress"),
                        jwtClaims.get("userEmail"),
                        jwtClaims.get("userPhone"));
                customerManager.saveLocalUserToPref(sharedPreferences, user);
                if (user.getActive() != 0)
                    navigateToMainActivity();
                else
                    navigateToUserInfo(username, user.getStep());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void checkUserExistence(String username, String password) {
        CheckUserExistenceInteractor checkUserExistenceInteractor = new CheckUserExistenceInteractorImpl(mExecutor, mMainThread, this, customerManager, username, password);
        checkUserExistenceInteractor.execute();
    }

    @Override
    public void onUserExist() {
        try {
            mView.showError(mView.getStringResource(R.string.username_invalid));
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onUserNotExist(String username, String password) {
        try {
            InsertNewCustomerInteractor insertNewCustomerInteractor = new InsertNewCustomerInteractorImpl(mExecutor, mMainThread, this, customerManager, username, password);
            insertNewCustomerInteractor.execute();
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onInsertCustomerFail() {
        try {
            onError("Insert customer fail");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onInsertCustomerSuccess(String username) {
        try {
            navigateToUserInfo(username, 0);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void updateVerifyAcc(String id) {
        UpdateVerifyAccInteractor updateVerifyAccInteractor = new UpdateVerifyAccInteractorImpl(mExecutor, mMainThread, this, customerManager, id);
        updateVerifyAccInteractor.execute();
    }

    @Override
    public void onUpdateVerifyAccFail() {
        try {
            mView.showAlertDialog(R.string.verify_fail);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onUpdateVerifyAccSuccess() {
        try {
            mView.showAlertDialog(R.string.verify_success);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }
}
