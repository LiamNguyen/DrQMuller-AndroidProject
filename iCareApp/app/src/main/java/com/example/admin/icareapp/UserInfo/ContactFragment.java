package com.example.admin.icareapp.UserInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.icareapp.DatabaseObserver;
import com.example.admin.icareapp.QueryDatabase;
import com.example.admin.icareapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class ContactFragment extends Fragment implements View.OnClickListener, TextWatcher, DatabaseObserver{
    private final String email_requirement = "[\\w]+@[^@]*[^@]$";
    private final String phone_requirement = "\\d{1,16}";
    private EditText email;
    private EditText phone;
    private UserInfoListener uiListener;
    private boolean validEmail, validPhone;
    private FragmentTransaction frag_transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_contacts, container, false);

        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p3) ;
        email = (EditText) view.findViewById(R.id.ui_email_input);
        phone = (EditText) view.findViewById(R.id.ui_phone_input);

        email.addTextChangedListener(this);
        phone.addTextChangedListener(this);
        button.setOnClickListener(this);

        validEmail = false;
        validPhone = false;

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            uiListener = (UserInfoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UserInfoListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_next_button_p3:
                if (validEmail && validPhone){
                    uiListener.addUserInfo("email", email.getText().toString());
                    uiListener.addUserInfo("phone", phone.getText().toString());
                    uiListener.addUserInfo("update_date", getCurrentDate());
                    new QueryDatabase(getActivity(), this).execute("num_users", "http://192.168.0.105/Select_NumberOfCustomer.php");
                }
                break;
            case R.id.back_button:

                break;
            default:
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (email.getText().hashCode() == s.hashCode()){
            String get_email = email.getText().toString();
            get_email.trim();
            if (!get_email.equals("")){
                if (get_email.matches(email_requirement)){
                    email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_valid_input, 0);
                    //login_id_container.setErrorEnabled(false);
                    validEmail = true;
                }else{
                    email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_invalid_input, 0);
                    validEmail = false;
                }
            }else {
                email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email, 0, 0, 0);
                validEmail = false;
            }
        }else if (phone.getText().hashCode() == s.hashCode()){
            String get_phone = phone.getText().toString();
            get_phone.toString();
            if (!get_phone.equals("")){
                if (get_phone.matches(phone_requirement)){
                    phone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.drawable.ic_valid_input, 0);
                    //login_id_container.setErrorEnabled(false);
                    validPhone = true;
                }else{
                    phone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.drawable.ic_invalid_input, 0);
                    validPhone = false;
                }
            }else {
                phone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, 0, 0);
                validPhone = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void update(Object o) {
        String status = (String) o;
         if (status.equals("Updated")){
             System.out.println("Updated");

         }else if (!status.equals("Get no of customers failed")){
             System.out.println(getCurrentDate());
             new QueryDatabase(getActivity(), this).execute("update_user", "http://192.168.0.105/Update_CustomerInfo.php", status, uiListener.getUserInfo("name"), uiListener.getUserInfo("year") + "-" + uiListener.getUserInfo("month") + "-" + uiListener.getUserInfo("day"), uiListener.getUserInfo("gender"), uiListener.getUserInfo("phone"), uiListener.getUserInfo("address"), uiListener.getUserInfo("email"), uiListener.getUserInfo("update_date"));
         }
         else{
             System.out.println("Update fail");
         }

    }

    public String getCurrentDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        return df.format(Calendar.getInstance().getTime());
    }
}
