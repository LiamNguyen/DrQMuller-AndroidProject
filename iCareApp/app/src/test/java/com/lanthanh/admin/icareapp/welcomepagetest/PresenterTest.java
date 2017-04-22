package com.lanthanh.admin.icareapp.welcomepagetest;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.exceptions.UseCaseException;
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeContract;
import com.lanthanh.admin.icareapp.utils.scheduler.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author longv
 *         Created on 20-Apr-17.
 */
public class PresenterTest {
    @Mock private WelcomeContract.LogInView mLogInView;
    @Mock private WelcomeContract.SignUpView mSignUpView;
    @Mock private WelcomeContract.Navigator mNavigator;
    @Mock private WelcomeRepository mWelcomeRepository;
    @Mock private UserRepository mUserRepository;

    private final String DEFAULT_USERNAME = "testUser";
    private final String DEFAULT_PASSWORD = "testPassword";
    private WelcomeContract.Presenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new WelcomeActivityPresenter(mLogInView, mSignUpView, mWelcomeRepository, mUserRepository, new ImmediateSchedulerProvider());
        mPresenter.setNavigator(mNavigator);
    }

    @Test
    public void testLogin_SuccessAndUserValid() {
        when(mWelcomeRepository.login(DEFAULT_USERNAME, DEFAULT_PASSWORD)).thenReturn(Observable.just(RepositorySimpleStatus.SUCCESS));
        when(mUserRepository.checkUserInformationValidity()).thenReturn(Observable.just(RepositorySimpleStatus.VALID_USER));

        //Presenter does login
        mPresenter.login(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        //Show loading indicator
        verify(mLogInView).showLoadingIndicator(true);
        //Log in successfully -> check user validity
        verify(mUserRepository).checkUserInformationValidity();
        //User is valid
        verify(mNavigator).goToMainPage();
        //Hide loading indicator after finished
        verify(mLogInView).showLoadingIndicator(false);
    }

    @Test
    public void testLogin_SuccessAndUserNotValid() {
        when(mWelcomeRepository.login(DEFAULT_USERNAME, DEFAULT_PASSWORD)).thenReturn(Observable.just(RepositorySimpleStatus.SUCCESS));
        when(mUserRepository.checkUserInformationValidity()).thenReturn(Observable.just(RepositorySimpleStatus.MISSING_USER));

        //Presenter does login
        mPresenter.login(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        //Show loading indicator
        verify(mLogInView).showLoadingIndicator(true);
        //Log in successfully -> check user validity
        verify(mUserRepository).checkUserInformationValidity();
        //User is valid
        verify(mNavigator).goToUserInfoPage();
        //Hide loading indicator after finished
        verify(mLogInView).showLoadingIndicator(false);
    }

    @Test
    public void testLogin_WhenUsernamePasswordNotMatch() {
        when(mWelcomeRepository.login(DEFAULT_USERNAME, DEFAULT_PASSWORD)).thenReturn(Observable.error(new UseCaseException(RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH)));

        //Presenter does login
        mPresenter.login(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        //Show loading indicator
        verify(mLogInView).showLoadingIndicator(true);
        //Exception raised, hide loading indicator and show appropriate message
        verify(mLogInView).showLoadingIndicator(false);
        verify(mLogInView).showUsernamePasswordNotMatchMessage();
    }

    @Test
    public void testLogin_WhenPatternFail() {
        when(mWelcomeRepository.login(DEFAULT_USERNAME, DEFAULT_PASSWORD)).thenReturn(Observable.error(new UseCaseException(RepositorySimpleStatus.PATTERN_FAIL)));

        //Presenter does login
        mPresenter.login(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        //Show loading indicator
        verify(mLogInView).showLoadingIndicator(true);
        //Exception raised, hide loading indicator and show appropriate message
        verify(mLogInView).showLoadingIndicator(false);
        verify(mLogInView).showPatternFailMessage();
    }

    @Test
    public void testSignup_Success() {
        when(mWelcomeRepository.signup(DEFAULT_USERNAME, DEFAULT_PASSWORD)).thenReturn(Observable.just(RepositorySimpleStatus.SUCCESS));

        //Presenter does signup
        mPresenter.signup(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        //Show loading indicator
        verify(mSignUpView).showLoadingIndicator(true);
        //Sign up successfully -> go to user info page
        verify(mNavigator).goToUserInfoPage();
        //Hide loading indicator after finish
        verify(mSignUpView).showLoadingIndicator(false);
    }

    @Test
    public void testSignup_WhenUsernameExisted() {
        when(mWelcomeRepository.signup(DEFAULT_USERNAME, DEFAULT_PASSWORD)).thenReturn(Observable.error(new UseCaseException(RepositorySimpleStatus.USERNAME_EXISTED)));

        //Presenter does signup
        mPresenter.signup(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        //Show loading indicator
        verify(mSignUpView).showLoadingIndicator(true);
        //Exception raised, hide loading indicator and show appropriate message
        verify(mSignUpView).showLoadingIndicator(false);
        verify(mSignUpView).showUsernameAlreadyExistedMessage();
    }

    @Test
    public void testSignup_WhenPatternFail() {
        when(mWelcomeRepository.signup(DEFAULT_USERNAME, DEFAULT_PASSWORD)).thenReturn(Observable.error(new UseCaseException(RepositorySimpleStatus.PATTERN_FAIL)));

        //Presenter does signup
        mPresenter.signup(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        //Show loading indicator
        verify(mSignUpView).showLoadingIndicator(true);
        //Exception raised, hide loading indicator and show appropriate message
        verify(mSignUpView).showLoadingIndicator(false);
        verify(mSignUpView).showPatternFailMessage();
    }
}
