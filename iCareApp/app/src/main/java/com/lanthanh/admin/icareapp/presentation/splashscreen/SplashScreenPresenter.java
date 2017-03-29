package com.lanthanh.admin.icareapp.presentation.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivity;

/**
 * @author longv
 *         Created on 28-Mar-17.
 */

public class SplashScreenPresenter extends BasePresenter {
    private SplashScreenActivity activity;
    private Interactor interactor;
    private UserRepository userRepository;

    public SplashScreenPresenter(SplashScreenActivity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        interactor = new Interactor();
        userRepository = new UserRepositoryImpl(this.activity);
    }

    public void checkLoggedIn() {
        interactor.execute(
            () -> userRepository.checkUserLoggedIn(),
            isLoggedIn -> {
                if (isLoggedIn) {
                    navigateActivity(MainActivity.class);
                } else {
                    navigateActivity(WelcomeActivity.class);
                }
            },
            error -> {}
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

    @Override
    public void destroy() {
        interactor.dispose();
    }
}
