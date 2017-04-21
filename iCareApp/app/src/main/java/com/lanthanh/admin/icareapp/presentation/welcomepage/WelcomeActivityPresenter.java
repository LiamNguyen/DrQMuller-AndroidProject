package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.support.annotation.NonNull;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.exceptions.UseCaseException;
import com.lanthanh.admin.icareapp.presentation.base.AbstractPresenter2;
import com.lanthanh.admin.icareapp.utils.scheduler.BaseSchedulerProvider;

import io.reactivex.disposables.Disposable;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class WelcomeActivityPresenter extends AbstractPresenter2 implements WelcomeContract.Presenter{
    @NonNull private WelcomeContract.LogInView mLogInView;
    @NonNull private WelcomeContract.SignUpView mSignUpView;
    @NonNull private WelcomeContract.Navigator mNavigator;
    @NonNull private WelcomeRepository mWelcomeRepository;
    @NonNull private UserRepository mUserRepository;
    @NonNull private BaseSchedulerProvider mSchedulerProvider;

    public WelcomeActivityPresenter(@NonNull WelcomeContract.LogInView logInView, @NonNull WelcomeContract.SignUpView signUpView,
                                    @NonNull WelcomeRepository welcomeRepository, @NonNull UserRepository userRepository,
                                    @NonNull BaseSchedulerProvider schedulerProvider) {
        super();

        mLogInView = logInView;
        mSignUpView = signUpView;
        mWelcomeRepository = welcomeRepository;
        mUserRepository = userRepository;
        mSchedulerProvider = schedulerProvider;

        mLogInView.setPresenter(this);
        mSignUpView.setPresenter(this);
    }

    @Override
    public void setNavigator(@NonNull WelcomeContract.Navigator navigator) {
        mNavigator = navigator;
    }

    public void login(String username, String password){
        mLogInView.showLoadingIndicator(true);
        Disposable disposable =
        mWelcomeRepository.login(username, password)
                         .concatMap(success -> mUserRepository.checkUserInformationValidity())
                         .subscribeOn(mSchedulerProvider.io())
                         .observeOn(mSchedulerProvider.ui())
                         .subscribe(
                             //onNext
                             success -> {
                                 if (success == RepositorySimpleStatus.VALID_USER) mNavigator.goToMainPage();
                                 else mNavigator.goToUserInfoPage();
                             },
                             //onError
                             error -> {
                                 mLogInView.showLoadingIndicator(false);
                                 if (error instanceof UseCaseException) {
                                     switch (((UseCaseException) error).getStatus()) {
                                         case PATTERN_FAIL:
                                             mLogInView.showPatternFailMessage();
                                             break;
                                         case USERNAME_PASSWORD_NOT_MATCH:
                                             mLogInView.showUsernamePasswordNotMatchMessage();
                                             break;
                                     }
                                 }
                             },
                             //onComplete
                             () -> mLogInView.showLoadingIndicator(false)
                         );
        mDisposables.add(disposable);
    }

    public void signup(String username, String password){
        mSignUpView.showLoadingIndicator(true);
        Disposable disposable =
        mWelcomeRepository.signup(username, password)
                         .subscribeOn(mSchedulerProvider.io())
                         .observeOn(mSchedulerProvider.ui())
                         .subscribe(
                             //onNext
                             success -> mNavigator.goToUserInfoPage(),
                             //onError
                             error -> {
                                 mSignUpView.showLoadingIndicator(false);
                                 if (error instanceof UseCaseException) {
                                     switch (((UseCaseException) error).getStatus()) {
                                         case PATTERN_FAIL:
                                             mSignUpView.showPatternFailMessage();
                                             break;
                                         case USERNAME_EXISTED:
                                             mSignUpView.showUsernameAlreadyExistedMessage();
                                             break;
                                     }
                                 }
                             },
                             //onComplete
                             () -> mSignUpView.showLoadingIndicator(false)
                         );
        mDisposables.add(disposable);
    }
}