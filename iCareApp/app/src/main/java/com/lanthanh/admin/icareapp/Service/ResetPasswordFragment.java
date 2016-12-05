package com.lanthanh.admin.icareapp.Service;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 22-Nov-16.
 */

public class ResetPasswordFragment extends Fragment implements TextWatcher, View.OnClickListener, DatabaseObserver{
    private TextInputEditText password;
    private TextInputEditText password_confirm;
    private TextInputLayout password_container;
    private TextInputLayout password_confirm_container;
    private boolean validPW, validPWConf;
    private String username;
    private Controller aController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resetpw_reset, container, false);

        validPW = false;
        validPWConf = false;
        aController = Controller.getInstance();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font

        password = (TextInputEditText) view.findViewById(R.id.resetpw_password_input);
        password.addTextChangedListener(this);
        password.setTypeface(font);
        password_confirm = (TextInputEditText) view.findViewById(R.id.resetpw_password_confirm_input);
        password_confirm.addTextChangedListener(this);
        password_confirm.setTypeface(font);
        password_container = (TextInputLayout) view.findViewById(R.id.resetpw_password_container);
        password_container.setTypeface(font);
        password_confirm_container = (TextInputLayout) view.findViewById(R.id.resetpw_password_confirm_container);
        password_confirm_container.setTypeface(font);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.resetpw_reset_button);
        button.setOnClickListener(this);
        button.setTypeface(font);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle b = getArguments();
        if (b!=null){
            if (!b.getString("login_id","").isEmpty()){
                username = b.getString("login_id","");
            }else{
                ((ResetPasswordActivity) getActivity()).navigateToEmail();
            }
        }else{
            ((ResetPasswordActivity) getActivity()).navigateToEmail();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //Check user input by input to see whether it meets the requirements of login id or password
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (password.getText().hashCode() == s.hashCode()){ //Check password
            String get_pw = s.toString();
            get_pw.trim();
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
            pwconf.trim();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resetpw_reset_button:
                if (validPW && validPWConf) {
                    aController.setRequestData(getActivity(), this, ModelURL.UPDATE_RESETPW.getUrl(MainActivity.isUAT), "login_id=" + username + "&password=" + password.getText().toString().trim());
                }
                else {
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
                break;
            default:
                break;
        }
        ((ResetPasswordActivity) getActivity()).hideSoftKeyboard();
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        if (status == null) {
            System.out.println("ERROR IN PHP FILE");
            return;
        }

        try{
            if (status.has("Update_ResetPw")) {
                String response = status.getString("Update_ResetPw");
                if (!response.isEmpty()){
                    if (response.equals("QueryFailed") || response.equals("Failed")) {
                        ((ResetPasswordActivity) getActivity()).navigateToRegisterAfterReset(0);
                    }else if (response.equals("Updated")){
                        ((ResetPasswordActivity) getActivity()).navigateToRegisterAfterReset(1);
                    }
                }
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }
}
