package com.lanthanh.admin.icareapp.presentation.welcomepage;

import com.lanthanh.admin.icareapp.presentation.base.BasePresenter2;
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

    interface Presenter extends BasePresenter2 {
        void login(String username, String password);
        void signup(String username, String password);
        void setNavigator(Navigator navigator);
        void performNavigate(Navigator.Page page);
    }

    interface Navigator {
        enum Page {
            MAIN_PAGE,
            USERINFO_PAGE
        }
        void goToMainPage();
        void goToUserInfoPage();
    }
}
