package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.model.InputRequirement;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;
import com.lanthanh.admin.icareapp.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class SignUpFragment extends BaseFragment<WelcomeActivityPresenter> {
    @BindView(R.id.su_username_input) TextInputEditText editUsername;
    @BindView(R.id.su_password_input) TextInputEditText editPassword;
    @BindView(R.id.su_password_confirm_input) TextInputEditText editPasswordConfirm;
    @BindView(R.id.su_username_container) TextInputLayout editUsernameContainer;
    @BindView(R.id.su_password_container) TextInputLayout editPasswordContainer;
    @BindView(R.id.su_password_confirm_container) TextInputLayout editPasswordConfirmContainer;
    @BindView(R.id.su_sign_up_button) AppCompatButton signUpButton;

    private Disposable editTextDisposable;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register_signup, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        ((WelcomeActivity) getActivity()).showToolbar(true);

        //Apply custom font for UI elements
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        signUpButton.setTypeface(font);
        editUsername.setTypeface(font);
        editPassword.setTypeface(font);
        editPasswordConfirm.setTypeface(font);
        editUsernameContainer.setTypeface(font);
        editPasswordContainer.setTypeface(font);
        editPasswordConfirmContainer.setTypeface(font);

        //Show requirement as hint for user
        editUsernameContainer.setError(getString(R.string.username_requirement));
        editUsernameContainer.setErrorEnabled(true);
        editPasswordContainer.setError(getString(R.string.password_requirement));
        editPasswordContainer.setErrorEnabled(true);

        //Set up listener for button
        signUpButton.setOnClickListener(view -> getMainPresenter().signup(editUsername.getText().toString().trim(), editPassword.getText().toString()));

        //Observe edit texts' value
        Observable<Boolean> usernameObservable = RxTextView.textChanges(editUsername)
            .map(username -> {
                if (StringUtils.isNotEmpty(username)){
                    if (StringUtils.validatePattern(username, InputRequirement.USERNAME)) {
                        editUsername.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        return true;
                    } else {
                        editUsername.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        return false;
                    }
                } else {
                    editUsername.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, 0, 0);
                    return false;
                }
            });
        Observable<CharSequence> passwordObservable = RxTextView.textChanges(editPassword);
        Observable<CharSequence> confirmPasswordObservable = RxTextView.textChanges(editPasswordConfirm);
        Observable<Boolean> passwordMatcherObservable = Observable.combineLatest(
                passwordObservable, confirmPasswordObservable,
                (password, confirmPassword) -> {
                    if (StringUtils.isNotEmpty(password)){
                        if (StringUtils.validatePattern(password, InputRequirement.PASSWORD)) {
                            editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        }
                        else{
                            editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0 , R.drawable.ic_error_white_24dp, 0);
                        }
                    } else {
                        editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0, 0, 0);
                    }
                    if (StringUtils.isNotEmpty(password, confirmPassword) && StringUtils.isEqual(password, confirmPassword)) {
                        editPasswordConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        return true;
                    } else {
                        if (StringUtils.isNotEmpty(confirmPassword)) {
                            editPasswordConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        } else {
                            editPasswordConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, 0, 0);
                        }
                        return false;
                    }

        });
        editTextDisposable = Observable.combineLatest(passwordMatcherObservable, usernameObservable, (validPassword, validUsername) -> validPassword && validUsername)
                                        .distinctUntilChanged()
                                        .subscribe(valid -> signUpButton.setEnabled(valid));
    }

    @Override
    public void refreshViews() {
        editUsername.setText("");
        editPassword.setText("");
        editPasswordConfirm.setText("");
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
    public WelcomeActivityPresenter getMainPresenter() {
        return ((WelcomeActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        editTextDisposable.dispose();
    }
}

