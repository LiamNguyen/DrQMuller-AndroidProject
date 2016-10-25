package com.example.admin.icareapp;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.admin.icareapp.UserInfo.UserInfoActivity;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, DatabaseObserver, TextWatcher {
    private final String login_in_requirement = "^[^.\\-@](?!.*\\s).{4,62}[^.\\-@]$";
    private final String password_requirement = "^[^\\s](?=.*[\\d\\W])(?=.*[a-zA-Z]).{6,253}[^\\s]$";
    private EditText login_id;
    private EditText password;
    private EditText password_confirm;
    private TextInputLayout login_id_container;
    private TextInputLayout password_container;
    private TextInputLayout password_confirm_container;
    private boolean validID, validPW, validPWConf;
    private String get_login_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_sign_up, container, false);

        validID = false;
        validPW = false;
        validPWConf = false;

        ImageButton back_button = (ImageButton) view.findViewById(R.id.su_back_button);
        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.su_sign_up_button);
        login_id = (EditText) view.findViewById(R.id.su_login_id_input);
        password = (EditText) view.findViewById(R.id.su_password_input);
        password_confirm = (EditText) view.findViewById(R.id.su_password_confirm_input);
        login_id_container = (TextInputLayout) view.findViewById(R.id.su_login_container);
        password_container = (TextInputLayout) view.findViewById(R.id.su_password_container);
        password_confirm_container = (TextInputLayout) view.findViewById(R.id.su_password_confirm_container);

        back_button.setOnClickListener(this);
        sign_in_button.setOnClickListener(this);

        login_id.addTextChangedListener(this);
        password.addTextChangedListener(this);
        password_confirm.addTextChangedListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.su_back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.su_sign_up_button:
                //if (validID && validPW)
                    //new QueryDatabase(getActivity(), this).execute("insert_user", "http://192.168.0.102/Insert_NewCustomer.php", login_id.getText().toString(), password.getText().toString());
                if (validID && validPW && validPWConf)
                    new QueryDatabase(getActivity(), this).execute("checkuser", "http://192.168.2.5/Select_CheckUserExistence.php", get_login_id);
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

    @Override
    public void update(Object o) {
        String status = (String) o;

        if (status.equals("NotExist")) {
            //login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, R.drawable.ic_verified_user_black_24dp, 0);
            //validID = true;
            new QueryDatabase(getActivity(), this).execute("insert_user", "http://192.168.0.105/Insert_NewCustomer.php", login_id.getText().toString(), password.getText().toString());
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        } else if (status.equals("Exist")){
            login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_invalid_input, 0);
            login_id_container.setError(getString(R.string.username_invalid));
            login_id_container.setErrorEnabled(true);
            validID = false;
        } else if (status.equals("Inserted")){
            System.out.println("Instert successfully");
        } else if (status.equals("Failed")){
            System.out.println("Insert failed");
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //Check user input by input to see whether it meets the requirements of login id or password
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (login_id.getText().hashCode() == s.hashCode()){ //Check username
            get_login_id = s.toString();
            get_login_id.trim();
            if (!get_login_id.equals("")){
                if (get_login_id.matches(login_in_requirement)) {
                    //new QueryDatabase(getActivity(), this).execute("checkuser", "http://192.168.0.102/Select_CheckUserExistence.php", username);
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
            String pw = s.toString();
            pw.trim();
            if (!pw.equals("")){
                if (pw.matches(password_requirement)) {
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
