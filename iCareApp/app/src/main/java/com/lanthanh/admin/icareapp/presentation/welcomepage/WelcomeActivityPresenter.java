package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.exceptions.UseCaseException;
import com.lanthanh.admin.icareapp.presentation.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.signupinfopage.UserInfoActivity;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class WelcomeActivityPresenter implements WelcomeContract.Presenter{
    public static final String TAG = WelcomeActivityPresenter.class.getSimpleName();

    private WelcomeContract.LogInView logInView;
    private WelcomeContract.SignUpView signUpView;
    private WelcomeContract.Navigator navigator;
    private WelcomeRepository welcomeRepository;
    private UserRepository userRepository;
    private Interactor interactor;

    public WelcomeActivityPresenter(WelcomeContract.LogInView logInView, WelcomeContract.SignUpView signUpView, WelcomeRepository welcomeRepository, UserRepository userRepository, Interactor interactor) {
        this.logInView = logInView;
        this.signUpView = signUpView;
        this.welcomeRepository = welcomeRepository;
        this.userRepository = userRepository;
        this.interactor = interactor;

        logInView.setPresenter(this);
        signUpView.setPresenter(this);
    }

    @Override
    public void setNavigator(WelcomeContract.Navigator navigator) {
        this.navigator = navigator;
    }

    public void login(String username, String password){
        logInView.showLoadingIndicator(true);
        interactor.execute(
            () -> welcomeRepository.login(username, password),
            success -> {
                logInView.showLoadingIndicator(false);
                interactor.execute(
                    () -> userRepository.checkUserInformationValidity(),
                    check -> {
                        if (check == RepositorySimpleStatus.VALID_USER)
                            navigator.goToMainPage();
                        else //Default
                            navigator.goToUserInfoPage();
                    },
                    error -> {}
                );
            },
            error -> {
                logInView.showLoadingIndicator(false);
                if (error instanceof UseCaseException) {
                    switch (((UseCaseException) error).getStatus()) {
                        case PATTERN_FAIL:
                            logInView.showPatternFailMessage();
                            break;
                        case USERNAME_PASSWORD_NOT_MATCH:
                            logInView.showUsernamePasswordNotMatchMessage();
                            break;
                    }
                }
            }
        );
    }

    public void signup(String username, String password){
        signUpView.showLoadingIndicator(true);
        interactor.execute(
            () -> welcomeRepository.signup(username, password),
            success -> {
                signUpView.showLoadingIndicator(false);
                navigator.goToUserInfoPage();
            },
            error -> {
                signUpView.showLoadingIndicator(false);
                if (error instanceof UseCaseException) {
                    switch (((UseCaseException) error).getStatus()) {
                        case PATTERN_FAIL:
                            signUpView.showPatternFailMessage();
                            break;
                        case USERNAME_EXISTED:
                            signUpView.showUsernameAlreadyExistedMessage();
                            break;
                    }
                }
            }
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
        interactor.dispose();
    }
}