package com.lanthanh.admin.icareapp.welcomepagetest;

import android.content.Context;

import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.exceptions.UseCaseException;
import com.lanthanh.admin.icareapp.helper.Injection;
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @author longv
 *         Created on 20-Apr-17.
 */
public class PresenterTest {
    @Mock private WelcomeContract.LogInView logInView;
    @Mock private WelcomeContract.SignUpView signUpView;
    @Mock private WelcomeRepository welcomeRepository;
    @Mock private UserRepository userRepository;
    @Mock private Interactor.BuildUseCase useCaseBuilder;

    private WelcomeContract.Presenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new WelcomeActivityPresenter(logInView, signUpView, welcomeRepository, userRepository, Injection.provideUseCaseHandler());
        logInView.setPresenter(mPresenter);
    }

    @Test
    public void testLogin_whenUsernamePasswordNotMatch() {
        Mockito.when(useCaseBuilder.build()).thenReturn(Observable.error(new UseCaseException(RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH)));
        Mockito.when(welcomeRepository.login("abcdQwert123", "xyzsQwee12.")).thenReturn(Observable.error(new UseCaseException(RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH)));
        Mockito.when(userRepository.checkUserInformationValidity()).thenReturn(Observable.error(new UseCaseException(RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH)));

        mPresenter.login("abcdQwert123", "xyzsQwee12.");
        verify(logInView).showLoadingIndicator(true);
        verify(logInView).showLoadingIndicator(false);
        verify(logInView).showUsernamePasswordNotMatchMessage();
    }

}
