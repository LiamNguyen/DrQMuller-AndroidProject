package com.lanthanh.admin.icareapp.presentation.view.fragment.register;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.presentation.model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener, TextWatcher {
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText password_confirm;
    private TextInputLayout username_container;
    private TextInputLayout password_container;
    private TextInputLayout password_confirm_container;
    private boolean validUN, validPW, validPWConf;
    private RegisterActivityPresenter registerActivityPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register_signup, container, false);

        init();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font

        AppCompatButton sign_up_button = (AppCompatButton) view.findViewById(R.id.su_sign_up_button);
        sign_up_button.setOnClickListener(this);
        sign_up_button.setTypeface(font);

        username = (TextInputEditText) view.findViewById(R.id.su_username_input);
        username.addTextChangedListener(this);
        username.setTypeface(font);
        password = (TextInputEditText) view.findViewById(R.id.su_password_input);
        password.addTextChangedListener(this);
        password.setTypeface(font);
        password_confirm = (TextInputEditText) view.findViewById(R.id.su_password_confirm_input);
        password_confirm.addTextChangedListener(this);
        password_confirm.setTypeface(font);

        username_container = (TextInputLayout) view.findViewById(R.id.su_username_container);
        username_container.setTypeface(font);
        password_container = (TextInputLayout) view.findViewById(R.id.su_password_container);
        password_container.setTypeface(font);
        password_confirm_container = (TextInputLayout) view.findViewById(R.id.su_password_confirm_container);
        password_confirm_container.setTypeface(font);

        return view;
    }

    public void init(){
        registerActivityPresenter = ((RegisterActivity) getActivity()).getMainPresenter();
        validUN = false;
        validPW = false;
        validPWConf = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.su_sign_up_button:
                if (validUN && validPW && validPWConf) {
                    registerActivityPresenter.checkUserExistence(username.getText().toString().trim(), password.getText().toString());
                }
                else {
                    if (!validUN){
                        if (username.getText().toString().equals(""))
                            username_container.setError(getString(R.string.username_null));
                        else
                            username_container.setError(getString(R.string.username_requirement));
                        username_container.setErrorEnabled(true);
                    }

                    if (!validPW){
                        if (password.getText().toString().equals(""))
                            password_container.setError(getString(R.string.password_null));
                        else
                            password_container.setError(getString(R.string.password_requirement));
                        password_container.setErrorEnabled(true);
                    }

                    if (!validPWConf){
                        if (password_confirm.getText().toString().equals(""))
                            password_confirm_container.setError(getString(R.string.password_confirm_null));
                        else
                            password_confirm_container.setError(getString(R.string.password_confirm_requirement));
                        password_confirm_container.setErrorEnabled(true);
                    }
                }
                ((RegisterActivity) getActivity()).hideSoftKeyboard();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //Check user input by input to see whether it meets the requirements of login id or password
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (username.getText().hashCode() == s.hashCode()){ //Check username
            String get_username = s.toString().trim();
            if (!get_username.equals("")){
                if (get_username.matches(ModelInputRequirement.USERNAME)) {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    username_container.setErrorEnabled(false);
                    validUN = true;
                } else {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validUN = false;
                }
            }
            else {
                username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, 0, 0);
                validUN = false;
            }
        } else if (password.getText().hashCode() == s.hashCode()){ //Check password
            String get_pw = s.toString();
            if (!get_pw.equals("")){
                if (get_pw.matches(ModelInputRequirement.PASSWORD)) {
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    password_container.setErrorEnabled(false);
                    validPW = true;
                }
                else{
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0 , R.drawable.ic_invalid_input, 0);
                    validPW = false;
                }
                if (!password_confirm.getText().toString().isEmpty()) {
                    if (!get_pw.equals(password_confirm.getText().toString())) {
                        password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                        validPWConf = false;
                    } else {
                        password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_valid_input, 0);
                        validPWConf = true;
                    }
                }
            }
            else{
                password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0 , 0, 0);
                validPW = false;
            }
        } else if (password_confirm.getText().hashCode() == s.hashCode()){ //Check password
            String pwconf = s.toString();
            if (!pwconf.equals("")){
                if (pwconf.equals(password.getText().toString())) {
                    password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    password_confirm_container.setErrorEnabled(false);
                    validPWConf = true;
                }
                else{
                    password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0 , R.drawable.ic_invalid_input, 0);
                    validPWConf = false;
                }
            }
            else{
                password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0 , 0, 0);
                validPWConf = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
