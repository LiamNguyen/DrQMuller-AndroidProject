package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.LogInInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.SignUpInteractor;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.signupinfopage.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class WelcomeActivityPresenter extends BasePresenter {
    public static final String TAG = WelcomeActivityPresenter.class.getSimpleName();
    private ChooseFragment chooseFragment;
    private LogInFragment logInFragment;
    private SignUpFragment signUpFragment;
    private WelcomeActivity activity;

    private WelcomeRepository welcomeRepository;

    public WelcomeActivityPresenter(WelcomeActivity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        //Init fragment
        chooseFragment = new ChooseFragment();
        logInFragment = new LogInFragment();
        signUpFragment = new SignUpFragment();

        welcomeRepository = new WelcomeRepositoryImpl(this.activity);
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
        this.activity.showProgress();
        new LogInInteractor(welcomeRepository).execute(
            success -> {
                this.activity.hideProgress();
                if (success == RepositorySimpleStatus.SUCCESS){
                    navigateActivity(MainActivity.class);
                } else if (success == RepositorySimpleStatus.PATTERN_FAIL) {
                    this.activity.showToast(this.activity.getString(R.string.username_invalid));
                } else if (success == RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH) {
                    this.activity.showToast(this.activity.getString(R.string.login_fail));
                }
            },
            error -> {
                this.activity.hideProgress();
            },
            () -> {},
            LogInInteractor.Params.forLogin(username, password)
        );
    }

    public void signup(String username, String password){
        this.activity.showProgress();
        new SignUpInteractor(welcomeRepository).execute(
            success -> {
                this.activity.hideProgress();
                if (success == RepositorySimpleStatus.SUCCESS){
                    navigateActivity(UserInfoActivity.class);
                } else if (success == RepositorySimpleStatus.PATTERN_FAIL) {
                    this.activity.showToast(this.activity.getString(R.string.username_invalid));
                } else if (success == RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH) {
                    this.activity.showToast(this.activity.getString(R.string.username_unavailable));
                }
            },
            error -> {
                this.activity.hideProgress();
            },
            () -> {},
            SignUpInteractor.Params.forSignUp(username, password)
        );
    }

    public void navigateActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this.activity, activityClass);
        this.activity.startActivity(intent);
        this.activity.finish();
    }

    public void navigateActivity(Class<? extends Activity> activityClass, Bundle b) {
        Intent intent = new Intent(this.activity, activityClass);
        intent.putExtra(this.getClass().getName(), b); //TODO check this put extra
        this.activity.startActivity(intent);
        this.activity.finish();
    }

    @Override
    public void resume() {

    }
}