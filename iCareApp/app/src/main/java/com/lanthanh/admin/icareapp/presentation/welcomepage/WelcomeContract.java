package com.lanthanh.admin.icareapp.presentation.welcomepage;

import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.base.BaseView;

/**
 * @author longv
 *         Created on 13-Apr-17.
 */

public interface WelcomeContract {
    interface LogInView extends BaseView<Presenter> {
        void showUsernamePasswordNotMatchMessage();
        void showPatternFailMessage();
    }

    interface SignUpView extends BaseView<Presenter> {
        void showUsernameAlreadyExistedMessage();
        void showPatternFailMessage();
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
        void signup(String username, String password);
        void setOnViewChangeListener(OnViewChangeListener listener);
    }

    interface OnViewChangeListener {
        void onViewChange(BaseView view);
    }
}
