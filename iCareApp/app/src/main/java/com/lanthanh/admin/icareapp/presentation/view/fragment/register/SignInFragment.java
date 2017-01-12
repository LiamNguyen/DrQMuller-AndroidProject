package com.lanthanh.admin.icareapp.presentation.view.fragment.register;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class SignInFragment extends Fragment implements View.OnClickListener{
    private TextInputEditText username;
    private TextInputEditText password;
    private RegisterActivityPresenter registerActivityPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_sign_in, container, false);

        init();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        
        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.si_sign_in_button);
        sign_in_button.setOnClickListener(this);
        sign_in_button.setTypeface(font);
        
        TextView forget_pw = (TextView) view.findViewById(R.id.si_forget_pw);
        forget_pw.setOnClickListener(this);
        forget_pw.setTypeface(font);
        
        username = (TextInputEditText) view.findViewById(R.id.si_username_input);
        username.setTypeface(font);
        password = (TextInputEditText) view.findViewById(R.id.si_password_input);
        password.setTypeface(font);

        TextInputLayout username_container = (TextInputLayout) view.findViewById(R.id.si_username_container);
        username_container.setTypeface(font);
        TextInputLayout password_container = (TextInputLayout) view.findViewById(R.id.si_password_container);
        password_container.setTypeface(font);

        return view;
    }

    public void init(){
        registerActivityPresenter = ((RegisterActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.si_sign_in_button:
                registerActivityPresenter.logIn(username.getText().toString(), password.getText().toString());
                break;
            case R.id.si_forget_pw:
                registerActivityPresenter.navigateToResetPW();
            default:
                break;
        }
    }
}
