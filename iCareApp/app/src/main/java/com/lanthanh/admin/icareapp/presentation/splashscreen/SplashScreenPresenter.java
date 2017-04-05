package com.lanthanh.admin.icareapp.presentation.splashscreen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.presentation.application.iCareApplication;
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
    private WelcomeRepository welcomeRepository;

    public SplashScreenPresenter(SplashScreenActivity activity) {
        super(activity);
        this.activity = activity;
        init();
    }

    private void init() {
        interactor = new Interactor();
        userRepository = new UserRepositoryImpl(this.activity);
        welcomeRepository = new WelcomeRepositoryImpl(this.activity);
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

    public void checkVersionCode() {
        int versionCode = ((iCareApplication) this.activity.getApplication()).getVersionCode();
        interactor.execute(
            () -> welcomeRepository.checkVersionCode(versionCode),
            success -> {
                if (success == RepositorySimpleStatus.UPDATE_NEEDED) {
                    final String appPackageName = this.activity.getApplication().getPackageName();
                    new AlertDialog.Builder(this.activity)
                        .setMessage(this.activity.getString(R.string.update))
                        .setPositiveButton(
                                this.activity.getString(R.string.update_button),
                                (DialogInterface dialog, int which) -> {
                                    try {
                                        this.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        this.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                        )
                        .setCancelable(false).show();
                } else {
                    checkLoggedIn();
                }
            },
            error -> {}
        );
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        interactor.dispose();
    }
}
