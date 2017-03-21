package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.LogInInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.SignUpInteractor;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.ChooseFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.LogInFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.SignUpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class RegisterActivityPresenterImpl extends Presenter{
    public static final String TAG = RegisterActivityPresenterImpl.class.getSimpleName();
    private ChooseFragment chooseFragment;
    private LogInFragment logInFragment;
    private SignUpFragment signUpFragment;
    private RegisterActivity activity;

    private WelcomeRepository welcomeRepository;

    public RegisterActivityPresenterImpl(RegisterActivity activity) {
        this.activity = activity;
        init();
    }

    public void init() {
        //Init fragment
        chooseFragment = new ChooseFragment();
        logInFragment = new LogInFragment();
        signUpFragment = new SignUpFragment();

        welcomeRepository = new WelcomeRepositoryImpl();
    }

    public void navigateFragment(Class<? extends Fragment> fragmentClass) {
        if (fragmentClass == ChooseFragment.class)
            showFragment(chooseFragment);
        else if (fragmentClass == LogInFragment.class)
            showFragment(logInFragment);
        else if (fragmentClass == SignUpFragment.class)
            showFragment(signUpFragment);
    }

    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result
        if (chooseFragment.isVisible()) {
            result.add(chooseFragment);
        }
        if (logInFragment.isVisible()) {
            result.add(logInFragment);
        }
        if (signUpFragment.isVisible()) {
            result.add(signUpFragment);
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

    @Override
    public void navigateToMainActivity() {
        Bundle extras = new Bundle();
        extras.putInt(RegisterActivity.LOGIN_STATUS, RegisterActivity.LOGGED_IN);
        mView.navigateActivity(MainActivity.class, extras);
    }

    @Override
    public void navigateToUserInfo(int id, String uiStep) {
        Bundle extras = new Bundle();
        extras.putInt(RegisterActivity.EXTRA_ID, id);
        extras.putString(RegisterActivity.EXTRA_UISTEP, uiStep);
        mView.navigateActivity(UserInfoActivity.class, extras);
    }

    @Override
    public void navigateToResetPW() {
        mView.navigateActivity(ResetPasswordActivity.class);
    }

    public void login(String username, String password){
        String[] errorCode = new String[1];
        new LogInInteractor(welcomeRepository).execute(
            resp -> {
                UserInfo userInfo;
                if (resp.has("Select_ToAuthenticate")) {
                    userInfo = ConverterJson.convertGsonToObject(resp.getAsJsonArray("Select_ToAuthenticate").getAsJsonObject(), UserInfo.class);
                } else {
                    Log.e(this.getClass().getName(), "onNext in login: Invalid response from server");
                }
            },
            error -> {
                if (error instanceof HttpException){
                    JsonObject errorResp = ConverterJson.convertJsonToObject(((HttpException) error).message(), JsonObject.class);
                    if (errorResp.has("Select_ToAuthenticate")) {
                        errorCode[0] = errorResp.getAsJsonArray("Select_ToAuthenticate")
                                             .getAsJsonObject()
                                             .get("errorCode").getAsString();
                    } else {
                        Log.e(this.getClass().getName(), "onError in login: Invalid response from server");
                    }
                }
            },
            () -> {
                if (errorCode[0] == null){
                    //Login succeeded-> navigate to main activity
                } else {
                    //Login failed
                    if (errorCode[0].equals("pattern-fail")) {
                        //Invalid username submitted
                        Log.e(this.getClass().getName(), "Invalid username submitted...Check user's input requirement again");
                    } else if (errorCode[0].equals("invalid-username-or-password")) {
                        //Invalid username or password
                    }
                }
            },
            LogInInteractor.Params.forLogin(username, password)
        );
    }

    public void signup(String username, String password){
        new SignUpInteractor(welcomeRepository).execute(
            resp -> {},
            error -> {},
            () -> {},
            SignUpInteractor.Params.forSignUp(username, password)
        );
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}