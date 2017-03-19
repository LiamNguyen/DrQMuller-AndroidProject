package com.lanthanh.admin.icareapp.presentation.view.fragment.register;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
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
import com.lanthanh.admin.icareapp.presentation.presenter.impl.RegisterActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class SignUpFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.su_username_input) private TextInputEditText editUsername;
    @BindView(R.id.su_password_input) private TextInputEditText editPassword;
    @BindView(R.id.su_password_confirm_input) private TextInputEditText editPasswordConfirm;
    @BindView(R.id.su_username_container) private TextInputLayout editUsernameContainer;
    @BindView(R.id.su_password_container) private TextInputLayout editPasswordContainer;
    @BindView(R.id.su_password_confirm_container) private TextInputLayout editPasswordConfirmContainer;
    @BindView(R.id.su_sign_up_button) private AppCompatButton signUpButton;
    private boolean validUN, validPW, validPWConf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register_signup, container, false);

        initViews();
        validUN = false; validPW = false; validPWConf = false;

        return view;
    }

    @Override
    public void initViews() {
        //Custom font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        signUpButton.setTypeface(font);
        editUsername.setTypeface(font);
        editPassword.setTypeface(font);
        editPasswordConfirm.setTypeface(font);
        editUsernameContainer.setTypeface(font);
        editPasswordContainer.setTypeface(font);
        editPasswordConfirmContainer.setTypeface(font);

        signUpButton.setOnClickListener(this);

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String username = editUsername.toString().trim();
                if (!username.equals("")){
                    if (username.matches(ModelInputRequirement.USERNAME)) {
                        editUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_valid_input, 0);
                        editUsernameContainer.setErrorEnabled(false);
                        validUN = true;
                    } else {
                        editUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                        validUN = false;
                    }
                }
                else {
                    editUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, 0, 0);
                    validUN = false;
                }
            }
        });
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable editable) {}

            @Override public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String password = editPassword.toString();
                if (!password.equals("")){
                    if (password.matches(ModelInputRequirement.PASSWORD)) {
                        editPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0, R.drawable.ic_valid_input, 0);
                        editPasswordContainer.setErrorEnabled(false);
                        validPW = true;
                    }
                    else{
                        editPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0 , R.drawable.ic_invalid_input, 0);
                        validPW = false;
                    }
                    if (!editPasswordConfirm.getText().toString().isEmpty()) {
                        if (!password.equals(editPasswordConfirm.getText().toString())) {
                            editPasswordConfirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                            validPWConf = false;
                        } else {
                            editPasswordConfirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_valid_input, 0);
                            validPWConf = true;
                        }
                    }
                }
                else{
                    editPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_white_36dp, 0 , 0, 0);
                    validPW = false;
                }
            }
        });
        editPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String passwordConfirm = editPasswordConfirm.toString();
                if (!passwordConfirm.equals("")){
                    if (passwordConfirm.equals(editPassword.getText().toString())) {
                        editPasswordConfirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0, R.drawable.ic_valid_input, 0);
                        editPasswordConfirmContainer.setErrorEnabled(false);
                        validPWConf = true;
                    }
                    else{
                        editPasswordConfirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0 , R.drawable.ic_invalid_input, 0);
                        validPWConf = false;
                    }
                }
                else{
                    editPasswordConfirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security_white_36dp, 0 , 0, 0);
                    validPWConf = false;
                }
            }
        });
    }

    @Override
    public RegisterActivityPresenterImpl getMainPresenter() {
        return ((RegisterActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onClick(View v) {
        if (validUN && validPW && validPWConf) {
            getMainPresenter().signup(editUsername.getText().toString().trim(), editPassword.getText().toString());
        }
        else {
            if (!validUN){
                if (editUsername.getText().toString().equals(""))
                    editUsernameContainer.setError(getString(R.string.username_null));
                else
                    editUsernameContainer.setError(getString(R.string.username_requirement));
                editUsernameContainer.setErrorEnabled(true);
            }

            if (!validPW){
                if (editPassword.getText().toString().equals(""))
                    editPasswordContainer.setError(getString(R.string.password_null));
                else
                    editPasswordContainer.setError(getString(R.string.password_requirement));
                editPasswordContainer.setErrorEnabled(true);
            }

            if (!validPWConf){
                if (editPasswordConfirm.getText().toString().equals(""))
                    editPasswordConfirmContainer.setError(getString(R.string.password_confirm_null));
                else
                    editPasswordConfirmContainer.setError(getString(R.string.password_confirm_requirement));
                editPasswordConfirmContainer.setErrorEnabled(true);
            }
        }
        ((RegisterActivity) getActivity()).hideSoftKeyboard();
    }
}
