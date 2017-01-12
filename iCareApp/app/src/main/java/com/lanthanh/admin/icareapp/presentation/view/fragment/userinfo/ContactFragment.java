package com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.presentation.model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.UserInfoActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class ContactFragment extends Fragment implements View.OnClickListener, TextWatcher{
    private TextInputEditText email;
    private TextInputEditText phone;
    private TextInputLayout email_container;
    private TextInputLayout phone_container;
    private UserInfoActivityPresenter userInfoActivityPresenter;
    private boolean validEmail, validPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_contacts, container, false);

        init();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font

        TextView contact_noti = (TextView) view.findViewById(R.id.ui_contacts_noti);
        contact_noti.setTypeface(font);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p3) ;
        button.setOnClickListener(this);
        button.setTypeface(font);
        email = (TextInputEditText) view.findViewById(R.id.ui_email_input);
        email.addTextChangedListener(this);
        email.setTypeface(font);
        phone = (TextInputEditText) view.findViewById(R.id.ui_phone_input);
        phone.addTextChangedListener(this);
        phone.setTypeface(font);
        email_container = (TextInputLayout) view.findViewById(R.id.ui_email_container);
        email_container.setTypeface(font);
        phone_container = (TextInputLayout) view.findViewById(R.id.ui_phone_container);
        phone_container.setTypeface(font);

        return view;
    }

    public void init(){
        userInfoActivityPresenter = ((UserInfoActivity) getActivity()).getMainPresenter();
        validEmail = false;
        validPhone = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_next_button_p3:
                if (validEmail && validPhone){
                    userInfoActivityPresenter.setEmail(email.getText().toString().trim());
                    userInfoActivityPresenter.setPhone(phone.getText().toString().trim());
                    userInfoActivityPresenter.updateCustomer();
                }else{
                    if (!validEmail){
                        if (email.getText().toString().equals("")){
                            email_container.setError(getString(R.string.email_null));
                        }else {
                            email_container.setError(getString(R.string.email_requirement));
                        }
                        email_container.setErrorEnabled(true);
                    }
                    if (!validPhone){
                        if (phone.getText().toString().equals("")){
                            phone_container.setError(getString(R.string.phone_null));
                        }else {
                            phone_container.setError(getString(R.string.phone_requirement));
                        }
                        phone_container.setErrorEnabled(true);
                    }
                }
                break;
            default:
                break;
        }
    }

    //nothing
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /*
     *Check user input by input to see whether input is valid
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (email.getText().hashCode() == s.hashCode()){
            String get_email = email.getText().toString();
            get_email.trim();
            if (!get_email.equals("")){
                if (get_email.matches(ModelInputRequirement.EMAIL)){
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    email_container.setErrorEnabled(false);
                    validEmail = true;
                }else{
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validEmail = false;
                }
            }else {
                email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, 0, 0);
                validEmail = false;
            }
        }else if (phone.getText().hashCode() == s.hashCode()){
            String get_phone = phone.getText().toString();
            get_phone.trim();
            if (!get_phone.equals("")){
                if (get_phone.matches(ModelInputRequirement.PHONE)){
                    phone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    phone_container.setErrorEnabled(false);
                    validPhone = true;
                }else{
                    phone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validPhone = false;
                }
            }else {
                phone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, 0, 0);
                validPhone = false;
            }
        }
    }

    //nothing
    @Override
    public void afterTextChanged(Editable s) {
    }
}
