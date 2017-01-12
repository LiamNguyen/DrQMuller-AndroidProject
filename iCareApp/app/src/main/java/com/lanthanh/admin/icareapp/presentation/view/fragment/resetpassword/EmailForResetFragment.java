package com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.presentation.presenter.ResetPasswordActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 22-Nov-16.
 */

public class EmailForResetFragment extends Fragment implements TextWatcher, View.OnClickListener, DatabaseObserver{
    private ResetPasswordActivityPresenter resetPasswordActivityPresenter;
    private TextInputEditText email, username;
    private TextInputLayout email_container, username_container;
    private boolean validEmail, validUN;
    private TextView noti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resetpw_email, container, false);

        init();

        //Custom font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");

        AppCompatButton send = (AppCompatButton) view.findViewById(R.id.resetpw_send_button);
        send.setOnClickListener(this);
        send.setTypeface(font);
        email = (TextInputEditText) view.findViewById(R.id.resetpw_email_input);
        email.addTextChangedListener(this);
        email.setTypeface(font);
        email_container = (TextInputLayout) view.findViewById(R.id.resetpw_email_container);
        email_container.setTypeface(font);
        username = (TextInputEditText) view.findViewById(R.id.resetpw_username_input);
        username.addTextChangedListener(this);
        username.setTypeface(font);
        username_container = (TextInputLayout) view.findViewById(R.id.resetpw_username_container);
        username_container.setTypeface(font);
        noti = (TextView) view.findViewById(R.id.resetpw_email_noti);
        noti.setTypeface(font);

        return view;
    }

    public void init(){
        resetPasswordActivityPresenter = ((ResetPasswordActivity) getActivity()).getMainPresenter();
        validEmail = false; validUN = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resetpw_send_button:
                if (validEmail && validUN) {
                    resetPasswordActivityPresenter.sendEmailToResetPassword(email.getText().toString().trim(), username.getText().toString().trim());
                }else {
                    if (!validEmail) {
                        if (email.getText().toString().equals("")) {
                            email_container.setError(getString(R.string.email_null));
                        } else {
                            email_container.setError(getString(R.string.email_requirement));
                        }
                        email_container.setErrorEnabled(true);
                    }
                    if (!validUN){
                        if (username.getText().toString().equals(""))
                            username_container.setError(getString(R.string.username_null));
                        else
                            username_container.setError(getString(R.string.username_requirement));
                        username_container.setErrorEnabled(true);
                    }
                }
                break;
            default:
                break;
        }
        ((ResetPasswordActivity) getActivity()).hideSoftKeyboard();
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
        }else if (username.getText().hashCode() == s.hashCode()) { //Check username
            String get_username = s.toString();
            get_username.trim();
            if (!get_username.equals("")) {
                if (get_username.matches(ModelInputRequirement.USERNAME)) {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    username_container.setErrorEnabled(false);
                    validUN = true;
                } else {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validUN = false;
                }
            } else {
                username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, 0, 0);
                validUN = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void showEmailResult(int string , int color){
        noti.setText(string);
        noti.setTextColor(getResources().getColor(color));
    }
    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        try {
            if (status.has("Send_Email")){
                String result = status.getString("Send_Email");
                System.out.println(result);
                if (result.equals("Message has been sent")){
                    noti.setText(R.string.validate_noti_success);
                    noti.setTextColor(getResources().getColor(R.color.colorGreen));
                }else{
                    System.out.println("Message could not be sent");
                    System.out.println(status.getString("ERROR"));
                    noti.setText(R.string.validate_noti_fail);
                    noti.setTextColor(getResources().getColor(R.color.colorLightRed));
                }
            }
        } catch (JSONException je){
            System.out.println("Problem with JSON API");
        }
    }
}
