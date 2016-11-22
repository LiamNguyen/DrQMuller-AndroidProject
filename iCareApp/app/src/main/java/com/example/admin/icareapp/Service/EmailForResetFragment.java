package com.example.admin.icareapp.Service;

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

import com.example.admin.icareapp.Controller.Controller;
import com.example.admin.icareapp.Model.DatabaseObserver;
import com.example.admin.icareapp.Model.ModelInputRequirement;
import com.example.admin.icareapp.Model.ModelURL;
import com.example.admin.icareapp.R;
import com.example.admin.icareapp.UserInfo.UserInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 22-Nov-16.
 */

public class EmailForResetFragment extends Fragment implements TextWatcher, View.OnClickListener, DatabaseObserver{
    private Controller aController = Controller.getInstance();
    private TextInputEditText email;
    private TextInputLayout email_container;
    private boolean validEmail;
    private TextView noti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.resetpw_email, container, false);

        //ImageButton back = (ImageButton) view.findViewById(R.id.back_button);
        //back.setOnClickListener(this);
        AppCompatButton send = (AppCompatButton) view.findViewById(R.id.resetpw_send_button);
        send.setOnClickListener(this);
        email = (TextInputEditText) view.findViewById(R.id.resetpw_email_input);
        email.addTextChangedListener(this);
        email_container = (TextInputLayout) view.findViewById(R.id.resetpw_email_container);
        noti = (TextView) view.findViewById(R.id.resetpw_email_noti);

        validEmail = false;

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resetpw_send_button:
                if (validEmail) {
                    aController.setRequestData(getActivity(), this, ModelURL.SENDEMAIL_RESETPW.getUrl(), "email=" + email.getText().toString());
                    System.out.println("email=" + email.getText().toString());
                }else {
                    if (email.getText().toString().equals("")) {
                        email_container.setError(getString(R.string.email_null));
                    } else {
                        email_container.setError(getString(R.string.email_requirement));
                    }
                    email_container.setErrorEnabled(true);
                }
                break;
            case R.id.resetpw_back_button:
                ((ResetPasswordActivity) getActivity()).navigateBack();
                break;
            default:
                break;
        }
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
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_valid_input, 0);
                    email_container.setErrorEnabled(false);
                    validEmail = true;
                }else{
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_invalid_input, 0);
                    validEmail = false;
                }
            }else {
                email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, 0, 0);
                validEmail = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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
