package com.lanthanh.admin.icareapp.presentation.view.fragment.register;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.RegisterActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class LogInFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.si_username_input) private TextInputEditText editUsername;
    @BindView(R.id.si_password_input) private TextInputEditText editPassword;
    @BindView(R.id.si_sign_in_button) private AppCompatButton logInButton;
    @BindView(R.id.si_forget_pw) private TextView forgetPwText;
    @BindView(R.id.si_username_container) private TextInputLayout editUsernameContainer;
    @BindView(R.id.si_password_container) private TextInputLayout editPasswordContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register_signin, container, false);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
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
    public RegisterActivityPresenterImpl getMainPresenter() {
        return ((RegisterActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.si_sign_in_button:
                getMainPresenter().login(editUsername.getText().toString().trim(), editPassword.getText().toString());
                break;
            case R.id.si_forget_pw:
                getMainPresenter().navigateToResetPW();
            default:
                break;
        }
    }
}
