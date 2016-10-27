package com.example.admin.icareapp.Register;

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

import com.example.admin.icareapp.Controller.Controller;
import com.example.admin.icareapp.Model.DatabaseObserver;
import com.example.admin.icareapp.Model.ModelURL;
import com.example.admin.icareapp.Model.BackgroundTask;
import com.example.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener, DatabaseObserver, TextWatcher {
    private final String login_in_requirement = "^[^.\\-@](?!.*\\s).{4,62}[^.\\-@]$";
    private final String password_requirement = "^[^\\s](?=.*[\\d\\W])(?=.*[a-zA-Z]).{6,253}[^\\s]$";
    private TextInputEditText login_id;
    private TextInputEditText password;
    private TextInputEditText password_confirm;
    private TextInputLayout login_id_container;
    private TextInputLayout password_container;
    private TextInputLayout password_confirm_container;
    private boolean validID, validPW, validPWConf;
    private Controller aController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_sign_up, container, false);

        validID = false;
        validPW = false;
        validPWConf = false;
        aController = Controller.getInstance();

        ImageButton back_button = (ImageButton) view.findViewById(R.id.su_back_button);
        back_button.setOnClickListener(this);

        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.su_sign_up_button);
        sign_in_button.setOnClickListener(this);

        login_id = (TextInputEditText) view.findViewById(R.id.su_login_id_input);
        login_id.addTextChangedListener(this);

        password = (TextInputEditText) view.findViewById(R.id.su_password_input);
        password.addTextChangedListener(this);

        password_confirm = (TextInputEditText) view.findViewById(R.id.su_password_confirm_input);
        password_confirm.addTextChangedListener(this);

        login_id_container = (TextInputLayout) view.findViewById(R.id.su_login_container);
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
                if (validID && validPW && validPWConf) {
                    aController.getAccount().setLoginID(login_id.getText().toString());
                    aController.getAccount().setPassword(password.getText().toString());
                    new BackgroundTask(getActivity(), this).execute("check_user", ModelURL.SELECT_CHECKUSEREXISTENCE.getUrl(), aController.getAccount().getPostLoginID());
                }
                else {
                    if (!validID){
                        if (login_id.getText().toString().equals(""))
                            login_id_container.setError(getString(R.string.username_null));
                        else
                            login_id_container.setError(getString(R.string.username_requirement));
                        login_id_container.setErrorEnabled(true);
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
                    new BackgroundTask(getActivity(), this).execute("insert_user", ModelURL.INSERT_NEWCUSTOMER.getUrl(), aController.getAccount().getPostData());
                }else {
                    login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_invalid_input, 0);
                    login_id_container.setError(getString(R.string.username_invalid));
                    login_id_container.setErrorEnabled(true);
                    validID = false;
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
        if (login_id.getText().hashCode() == s.hashCode()){ //Check username
            String get_login_id = s.toString();
            get_login_id.trim();
            if (!get_login_id.equals("")){
                if (get_login_id.matches(login_in_requirement)) {
                    login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_valid_input, 0);
                    login_id_container.setErrorEnabled(false);
                    validID = true;
                } else {
                    login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_invalid_input, 0);
                    validID = false;
                }
            }
            else {
                login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, 0, 0);
                validID = false;
            }
        } else if (password.getText().hashCode() == s.hashCode()){ //Check password
            String get_pw = s.toString();
            get_pw.trim();
            if (!get_pw.equals("")){
                if (get_pw.matches(password_requirement)) {
                    password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_valid_input, 0);
                    password_container.setErrorEnabled(false);
                    validPW = true;
                }
                else{
                    password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0 , R.drawable.ic_invalid_input, 0);
                    validPW = false;
                }
            }
            else{
                password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0 , 0, 0);
                validPW = false;
            }
        } else if (password_confirm.getText().hashCode() == s.hashCode()){ //Check password
            String pwconf = s.toString();
            pwconf.trim();
            if (!pwconf.equals("")){
                if (pwconf.equals(password.getText().toString())) {
                    password_confirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pw_confirm, 0, R.drawable.ic_valid_input, 0);
                    password_confirm_container.setErrorEnabled(false);
                    validPWConf = true;
                }
                else{
                    password_confirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pw_confirm, 0 , R.drawable.ic_invalid_input, 0);
                    validPWConf = false;
                }
            }
            else{
                password_confirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pw_confirm, 0 , 0, 0);
                validPWConf = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
