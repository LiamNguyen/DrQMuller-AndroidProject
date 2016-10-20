package com.example.admin.icareapp;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener, DatabaseObserver, TextWatcher {
    private final String login_in_requirement = "^[^.-@].{4,62}[^.-@]$";
    private final String password_requirement = "^[^\\s](?=.*\\d)(?=.*[a-zA-Z])(?=.*[^\\w]).{6,253}[^\\s]$";
    private EditText login_id;
    private EditText password;
    private boolean validID, validPW;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        validID = false;
        validPW = false;

        ImageButton back_button = (ImageButton) view.findViewById(R.id.su_back_button);
        Button sign_in_button = (Button) view.findViewById(R.id.su_sign_up_button);
        login_id = (EditText) view.findViewById(R.id.su_login_id_input);
        password = (EditText) view.findViewById(R.id.su_password_input);

        back_button.setOnClickListener(this);
        sign_in_button.setOnClickListener(this);
        //login_id.setOnFocusChangeListener(this);
        login_id.addTextChangedListener(this);
        password.addTextChangedListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.su_back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.su_sign_up_button:
                if (validID && validPW)
                    new QueryDatabase(getActivity(), this).execute("insert_user", "http://192.168.0.102/Insert_NewCustomer.php", login_id.getText().toString(), password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Object o) {
        String status = (String) o;

        if (status.equals("0")) {
            login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, R.drawable.ic_verified_user_black_24dp, 0);
            validID = true;
        } else if (status.equals("1")){
            login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, R.drawable.ic_error_outline_black_24dp, 0);
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
        if (login_id.getText().hashCode() == s.hashCode()){
            String username = s.toString();
            username.trim();
            if (!username.equals("")){
                if (username.matches(login_in_requirement))
                    new QueryDatabase(getActivity(), this).execute("checkuser", "http://192.168.0.102/Select_CheckUserExistence.php", username);
                else {
                    login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, R.drawable.ic_error_outline_black_24dp, 0);
                    validID = false;
                }
            }
            else {
                login_id.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, 0, 0);
                validID = false;
            }
        } else if (password.getText().hashCode() == s.hashCode()){
            String pw = s.toString();
            pw.trim();
            if (!pw.equals("")){
                if (pw.matches(password_requirement)) {
                    password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vpn_key_black_24dp, 0, R.drawable.ic_verified_user_black_24dp, 0);
                    validPW = true;
                }
                else{
                    password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vpn_key_black_24dp, 0 , R.drawable.ic_error_outline_black_24dp, 0);
                    validPW = false;
                }
            }
            else{
                password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_vpn_key_black_24dp, 0 , 0, 0);
                validPW = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
