package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.model.InputRequirement;
import com.lanthanh.admin.icareapp.presentation.resetpasswordpage.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;
import com.lanthanh.admin.icareapp.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class LogInFragment extends BaseFragment<WelcomeActivityPresenter>{
    @BindView(R.id.si_username_input) TextInputEditText editUsername;
    @BindView(R.id.si_password_input) TextInputEditText editPassword;
    @BindView(R.id.si_sign_in_button) AppCompatButton logInButton;
    @BindView(R.id.si_forget_pw) TextView forgetPwText;
    @BindView(R.id.si_username_container) TextInputLayout editUsernameContainer;
    @BindView(R.id.si_password_container) TextInputLayout editPasswordContainer;

    private Disposable editTextDisposable;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register_signin, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        ((WelcomeActivity) getActivity()).showToolbar(true);

        //Apply custom font for UI elements
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        logInButton.setTypeface(font);
        editUsername.setTypeface(font);
        editPassword.setTypeface(font);
        editUsernameContainer.setTypeface(font);
        editPasswordContainer.setTypeface(font);

        //Set up listener for button
        logInButton.setOnClickListener(
                view ->  {
                    ((WelcomeActivity) getActivity()).hideSoftKeyboard();
                    getMainPresenter().login(editUsername.getText().toString().trim(), editPassword.getText().toString());
                }
        );

        //Observe edit texts' value
        Observable<Boolean> usernameObservable = RxTextView.textChanges(editUsername).map(StringUtils::isNotEmpty);
        Observable<Boolean> passwordObservable = RxTextView.textChanges(editPassword).map(StringUtils::isNotEmpty);
        editTextDisposable =  Observable.combineLatest(usernameObservable, passwordObservable, (validUsername, validPassword) -> validUsername && validPassword)
                                        .subscribe(valid -> logInButton.setEnabled(valid));
    }

    @Override
    public void refreshViews() {
        editUsername.setText("");
        editPassword.setText("");
    }

    @Override
    public WelcomeActivityPresenter getMainPresenter() {
        return ((WelcomeActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((WelcomeActivity) getActivity()).showSoftKeyboard(editUsername);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            ((WelcomeActivity) getActivity()).showToolbar(true);
            ((WelcomeActivity) getActivity()).showSoftKeyboard(editUsername);
        }
        else
            refreshViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        editTextDisposable.dispose();
    }
}

