package com.lanthanh.admin.icareapp.Register;

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
import android.widget.ImageButton;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener, DatabaseObserver, TextWatcher {
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText password_confirm;
    private TextInputLayout username_container;
    private TextInputLayout password_container;
    private TextInputLayout password_confirm_container;
    private boolean validUN, validPW, validPWConf;
    private Controller aController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_sign_up, container, false);

        validUN = false;
        validPW = false;
        validPWConf = false;
        aController = Controller.getInstance();

        ImageButton back_button = (ImageButton) view.findViewById(R.id.su_back_button);
        back_button.setOnClickListener(this);

        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.su_sign_up_button);
        sign_in_button.setOnClickListener(this);

        username = (TextInputEditText) view.findViewById(R.id.su_username_input);
        username.addTextChangedListener(this);

        password = (TextInputEditText) view.findViewById(R.id.su_password_input);
        password.addTextChangedListener(this);

        password_confirm = (TextInputEditText) view.findViewById(R.id.su_password_confirm_input);
        password_confirm.addTextChangedListener(this);

        username_container = (TextInputLayout) view.findViewById(R.id.su_username_container);
        password_container = (TextInputLayout) view.findViewById(R.id.su_password_container);
        password_confirm_container = (TextInputLayout) view.findViewById(R.id.su_password_confirm_container);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.su_back_button:
                ((RegisterActivity) getActivity()).navigateBack();
                break;
            case R.id.su_sign_up_button:
                if (validUN && validPW && validPWConf) {
                    aController.getAccount().setLoginID(username.getText().toString());
                    aController.getAccount().setPassword(password.getText().toString());
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKUSEREXISTENCE.getUrl(), aController.getAccount().getPostUsername());
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
                break;
            default:
                break;
        }
    }

    /*
     *Update fragment with data from database
     */
    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        try {
            if (status.has("Select_CheckUserExistence")){
                String result = status.getString("Select_CheckUserExistence");
                if (result.equals("Not Exist")){
                    System.out.println("Not Exist");
                    aController.setRequestData(getActivity(), this, ModelURL.INSERT_NEWCUSTOMER.getUrl(), aController.getAccount().getPostData());
                }else {
                    System.out.println("Exist");
                    username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_invalid_input, 0);
                    username_container.setError(getString(R.string.username_invalid));
                    username_container.setErrorEnabled(true);
                    validUN = false;
                }
            }else if (status.has("Insert_NewCustomer")){
                String result = status.getString("Insert_NewCustomer");
                if (result.equals("Inserted")){
                    System.out.println("Insert successfully");
                    ((RegisterActivity) getActivity()).navigateToUserInfo();
                }else {
                    System.out.println("Insert failed");
                }
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //Check user input by input to see whether it meets the requirements of login id or password
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (username.getText().hashCode() == s.hashCode()){ //Check username
            String get_username = s.toString();
            get_username.trim();
            if (!get_username.equals("")){
                if (get_username.matches(ModelInputRequirement.USERNAME)) {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_valid_input, 0);
                    username_container.setErrorEnabled(false);
                    validUN = true;
                } else {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_invalid_input, 0);
                    validUN = false;
                }
            }
            else {
                username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_user, 0, 0, 0);
                validUN = false;
            }
        } else if (password.getText().hashCode() == s.hashCode()){ //Check password
            String get_pw = s.toString();
            get_pw.trim();
            if (!get_pw.equals("")){
                if (get_pw.matches(ModelInputRequirement.PASSWORD)) {
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_valid_input, 0);
                    password_container.setErrorEnabled(false);
                    validPW = true;
                }
                else{
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_password, 0 , R.drawable.ic_invalid_input, 0);
                    validPW = false;
                }
            }
            else{
                password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_password, 0 , 0, 0);
                validPW = false;
            }
        } else if (password_confirm.getText().hashCode() == s.hashCode()){ //Check password
            String pwconf = s.toString();
            pwconf.trim();
            if (!pwconf.equals("")){
                if (pwconf.equals(password.getText().toString())) {
                    password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pw_confirm, 0, R.drawable.ic_valid_input, 0);
                    password_confirm_container.setErrorEnabled(false);
                    validPWConf = true;
                }
                else{
                    password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pw_confirm, 0 , R.drawable.ic_invalid_input, 0);
                    validPWConf = false;
                }
            }
            else{
                password_confirm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pw_confirm, 0 , 0, 0);
                validPWConf = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
