package com.example.admin.icareapp.UserInfo;

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

import com.example.admin.icareapp.Controller.Controller;
import com.example.admin.icareapp.Model.DatabaseObserver;
import com.example.admin.icareapp.Model.ModelURL;
import com.example.admin.icareapp.Model.BackgroundTask;
import com.example.admin.icareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class ContactFragment extends Fragment implements View.OnClickListener, TextWatcher, DatabaseObserver{
    private final String email_requirement = "[\\w]+@[^@]*[^@]$";
    private final String phone_requirement = "\\d{1,16}";
    private TextInputEditText email;
    private TextInputEditText phone;
    private TextInputLayout email_container;
    private TextInputLayout phone_container;
    private Controller aController;
    private boolean validEmail, validPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_contacts, container, false);

        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p3) ;
        button.setOnClickListener(this);
        email = (TextInputEditText) view.findViewById(R.id.ui_email_input);
        email.addTextChangedListener(this);
        phone = (TextInputEditText) view.findViewById(R.id.ui_phone_input);
        phone.addTextChangedListener(this);
        email_container = (TextInputLayout) view.findViewById(R.id.ui_email_container);
        phone_container = (TextInputLayout) view.findViewById(R.id.ui_phone_container);

        validEmail = true;
        validPhone = false;
        aController = Controller.getInstance();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_next_button_p3:
                if (validEmail && validPhone){
                    aController.getUserInfo().addInfo("email", email.getText().toString());
                    aController.getUserInfo().addInfo("phone", phone.getText().toString());
                    aController.getUserInfo().addInfo("update_date", getCurrentDate());
                    new BackgroundTask(getActivity(), this).execute("num_users", ModelURL.SELECT_NoOFCUSTOMERS.getUrl());
                }
                break;
            case R.id.back_button:
                ((UserInfoActivity) getActivity()).navigateBack();
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
                if (get_email.matches(email_requirement)){
                    email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_valid_input, 0);
                    email_container.setErrorEnabled(false);
                    validEmail = true;
                }else{
                    email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_invalid_input, 0);
                    validEmail = false;
                }
            }else {
                email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email, 0, 0, 0);
                validEmail = true;
            }
        }else if (phone.getText().hashCode() == s.hashCode()){
            String get_phone = phone.getText().toString();
            get_phone.toString();
            if (!get_phone.equals("")){
                if (get_phone.matches(phone_requirement)){
                    phone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.drawable.ic_valid_input, 0);
                    phone_container.setErrorEnabled(false);
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

    //nothing
    @Override
    public void afterTextChanged(Editable s) {

    }

    /*
     *Update fragment with data from database
     */
    @Override
    public void update(Object o) {
         JSONObject status = (JSONObject) o;

        try {
            if (status.has("Update_CustomerInfo")){
                String result = status.getString("Update_CustomerInfo");
                if (result.equals("Updated")){
                    ((UserInfoActivity) getActivity()).navigateToBooking();
                }else{
                    System.out.println("Update fail");
                }
            }else if (status.has("Select_NumberOfCustomers")){
                int result = status.getInt("Select_NumberOfCustomers");
                if (result == -1){
                    System.out.println("Fail to get number of customers");
                }else{
                    aController.getUserInfo().addInfo("cus_id", Integer.toString(result));
                    new BackgroundTask(getActivity(), this).execute("update_user", ModelURL.UPDATE_CUSTOMERINFO.getUrl(), aController.getUserInfo().getPostData());
                }
            }

                /*if (status.equals("Updated")) {
                    System.out.println("Updated");
                    ((UserInfoActivity) getActivity()).navigateToBooking();
                } else if (!status.equals("Get no of customers failed")) {
                    aController.getUserInfo().addInfo("cus_id", status);
                    System.out.println(aController.getUserInfo().getInfoMap().toString());
                    new BackgroundTask(getActivity(), this).execute("update_user", ModelURL.UPDATE_CUSTOMERINFO.getUrl(), aController.getUserInfo().getPostData());
                } else {

                }*/
        } catch (JSONException je){
            System.out.println("Problem with JSON API");
        }
    }

    public String getCurrentDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(Calendar.getInstance().getTime());
    }
}
