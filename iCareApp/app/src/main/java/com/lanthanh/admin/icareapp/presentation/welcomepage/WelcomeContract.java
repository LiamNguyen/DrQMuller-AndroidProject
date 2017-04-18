package com.lanthanh.admin.icareapp.presentation.welcomepage;

import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.base.BaseView;

/**
 * @author longv
 *         Created on 13-Apr-17.
 */

public interface WelcomeContract {
    interface LogInView extends BaseView<Presenter> {
        void showLoadingIndicator(boolean shouldShow);
        void showUsernamePasswordNotMatchMessage();
        void showPatternFailMessage();
    }

    interface SignUpView extends BaseView<Presenter> {
        void showLoadingIndicator(boolean shouldShow);
        void showUsernameAlreadyExistedMessage();
        void showPatternFailMessage();
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
        void signup(String username, String password);
        void setNavigator(Navigator navigator);
    }

    interface Navigator {
        void goToMainPage();
        void goToUserInfoPage();
    }
}
