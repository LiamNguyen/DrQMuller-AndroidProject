package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.auth0.jwt.JWTVerifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.data.restapi.service.iCareService;
import com.lanthanh.admin.icareapp.data.service.Service;
import com.lanthanh.admin.icareapp.domain.interactor.LogInInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.SignUpInteractor;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
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
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

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
        new LogInInteractor(welcomeRepository).execute(
                new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject jsonObject) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                },
                LogInInteractor.Params.forLogin(username, password)
        );
    }

    public void signup(String username, String password){
        new SignUpInteractor(welcomeRepository).execute(
                new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject jsonObject) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
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