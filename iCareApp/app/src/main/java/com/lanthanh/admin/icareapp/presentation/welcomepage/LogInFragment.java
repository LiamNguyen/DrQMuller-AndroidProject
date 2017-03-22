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
import com.lanthanh.admin.icareapp.presentation.view.activity.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class LogInFragment extends BaseFragment<WelcomeActivityPresenter> implements View.OnClickListener{
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

        logInButton.setOnClickListener(this);
        forgetPwText.setOnClickListener(this);
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
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            ((WelcomeActivity) getActivity()).showToolbar(true);
        else
            resetViews();
    }

    @Override
    public void onClick(View v) {
        ((WelcomeActivity) getActivity()).hideSoftKeyboard();
        switch (v.getId()){
            case R.id.si_sign_in_button:
                getMainPresenter().login(editUsername.getText().toString().trim(), editPassword.getText().toString());
                break;
            case R.id.si_forget_pw:
                getMainPresenter().navigateActivity(ResetPasswordActivity.class);
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

