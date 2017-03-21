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
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
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

import retrofit2.Response;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class RegisterActivityPresenterImpl implements Presenter{
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

//
//    public void navigateToMainActivity() {
//        Bundle extras = new Bundle();
//        extras.putInt(RegisterActivity.LOGIN_STATUS, RegisterActivity.LOGGED_IN);
//        mView.navigateActivity(MainActivity.class, extras);
//    }
//
//
//    public void navigateToUserInfo(int id, String uiStep) {
//        Bundle extras = new Bundle();
//        extras.putInt(RegisterActivity.EXTRA_ID, id);
//        extras.putString(RegisterActivity.EXTRA_UISTEP, uiStep);
//        mView.navigateActivity(UserInfoActivity.class, extras);
//    }
//
//
//    public void navigateToResetPW() {
//        mView.navigateActivity(ResetPasswordActivity.class);
//    }

    public void login(String username, String password){
        String[] errorCode = new String[1];
        new LogInInteractor(welcomeRepository).execute(
            success -> {
                UserInfo userInfo;
                if (success.has("Select_ToAuthenticate")) {
                    userInfo = ConverterJson.convertGsonToObject(success.getAsJsonArray("Select_ToAuthenticate").getAsJsonObject(), UserInfo.class);
                } else {
                    Log.e(this.getClass().getName(), "onNext in login: Invalid response from server");
                }
            },
            error -> {
                if (error instanceof HttpException){
                    //Get error response from server
                    Response response = ((HttpException) error).response();
                    if (response.code() == 404){
                        //Page not found
                        Log.e(this.getClass().getName(), "onError in login: HTTP 404 Not Found...Please check again the URL and it's component");
                    } else if (response.code() == 400){
                        //Login failed
                        //Get response body
                        JsonObject errorResp = ConverterJson.convertJsonToObject(response.errorBody().string(), JsonObject.class);
                        if (errorResp.has("Select_ToAuthenticate")) {
                            //Get error code from server
                            errorCode[0] = errorResp.getAsJsonArray("Select_ToAuthenticate")
                                    .get(0).getAsJsonObject()
                                    .get("errorCode").getAsString();
                            if (errorCode[0].equals("pattern-fail")) {
                                //Invalid username submitted
                                this.activity.showToast(this.activity.getString(R.string.username_invalid));
                            } else if (errorCode[0].equals("invalid-username-or-password")) {
                                //Username or password does not match
                                this.activity.showToast(this.activity.getString(R.string.login_fail));
                            }
                        } else {
                            Log.e(this.getClass().getName(), "onError in login: Invalid response from server");
                        }
                    }
                }
            },
            () -> {

            },
            LogInInteractor.Params.forLogin(username, password)
        );
    }

    public void signup(String username, String password){
        String[] errorCode = new String[1];
        new SignUpInteractor(welcomeRepository).execute(
            success -> {
                UserInfo userInfo;
                if (success.has("Insert_NewCustomer")) {
                    userInfo = ConverterJson.convertGsonToObject(success.getAsJsonArray("Insert_NewCustomer").getAsJsonObject(), UserInfo.class);
                } else {
                    Log.e(this.getClass().getName(), "onNext in login: Invalid response from server");
                }
            },
            error -> {
                if (error instanceof HttpException){
                    //Get error response from server
                    Response response = ((HttpException) error).response();
                    if (response.code() == 404){
                        //Page not found
                        Log.e(this.getClass().getName(), "onError in login: HTTP 404 Not Found...Please check again the URL and it's component");
                    } else if (response.code() == 400){
                        //Login failed
                        //Get response body
                        JsonObject errorResp = ConverterJson.convertJsonToObject(response.errorBody().string(), JsonObject.class);
                        if (errorResp.has("Insert_NewCustomer")) {
                            //Get error code from server
                            errorCode[0] = errorResp.getAsJsonArray("Insert_NewCustomer")
                                    .get(0).getAsJsonObject()
                                    .get("errorCode").getAsString();
                            if (errorCode[0].equals("pattern-fail")) {
                                //Invalid username submitted
                                this.activity.showToast(this.activity.getString(R.string.username_invalid));
                            } else if (errorCode[0].equals("customer-existed")) {
                                //Username or password does not match
                                this.activity.showToast(this.activity.getString(R.string.username_unavailable));
                            }
                        } else {
                            Log.e(this.getClass().getName(), "onError in login: Invalid response from server");
                        }
                    }
                }
            },
            () -> {

            },
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