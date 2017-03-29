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

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.resetpasswordpage.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class LogInFragment extends BaseFragment<WelcomeActivityPresenter>{
    @BindView(R.id.si_username_input)
    TextInputEditText editUsername;
    @BindView(R.id.si_password_input) TextInputEditText editPassword;
    @BindView(R.id.si_sign_in_button)
    AppCompatButton logInButton;
    @BindView(R.id.si_forget_pw)
    TextView forgetPwText;
    @BindView(R.id.si_username_container)
    TextInputLayout editUsernameContainer;
    @BindView(R.id.si_password_container) TextInputLayout editPasswordContainer;
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
        //Custom font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        logInButton.setTypeface(font);
        forgetPwText.setTypeface(font);
        editUsername.setTypeface(font);
        editPassword.setTypeface(font);
        editUsernameContainer.setTypeface(font);
        editPasswordContainer.setTypeface(font);

        logInButton.setOnClickListener(
            view ->  {
                ((WelcomeActivity) getActivity()).hideSoftKeyboard();
                getMainPresenter().login(editUsername.getText().toString().trim(), editPassword.getText().toString());
            }
        );
        forgetPwText.setOnClickListener(
            view -> {
                ((WelcomeActivity) getActivity()).hideSoftKeyboard();
                getMainPresenter().navigateActivity(ResetPasswordActivity.class);
            }
        );
    }

    @Override
    public void resetViews() {
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
            resetViews();
    }

    @Override
    public ApplicationProvider getProvider() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

