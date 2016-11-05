package com.example.admin.icareapp.Register;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.admin.icareapp.Controller.Controller;
import com.example.admin.icareapp.Model.DatabaseObserver;
import com.example.admin.icareapp.Model.ModelURL;
import com.example.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class SignInFragment extends Fragment implements View.OnClickListener, DatabaseObserver {
    private TextInputEditText login_id;
    private TextInputEditText password;
    private Controller aController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_sign_in, container, false);

        ImageButton back_button = (ImageButton) view.findViewById(R.id.si_back_button);
        back_button.setOnClickListener(this);

        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.si_sign_in_button);
        sign_in_button.setOnClickListener(this);

        login_id = (TextInputEditText) view.findViewById(R.id.si_username_input);
        password = (TextInputEditText) view.findViewById(R.id.si_password_input);

        aController = Controller.getInstance();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.si_back_button:
                ((RegisterActivity) getActivity()).navigateBack();
                break;
            case R.id.si_sign_in_button:
                aController.getAccount().setLoginID(login_id.getText().toString());
                aController.getAccount().setPassword(password.getText().toString());
                aController.sendQuery(getActivity(), this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(), aController.getAccount().getPostData());
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        try{
            if (status.has("Select_ToAuthenticate")){
                String result = status.getString("Select_ToAuthenticate");
                if (result.equals("Success")){
                    ((RegisterActivity) getActivity()).navigateToBookingActivity();
                }else{
                    System.out.println("Login fail");
                }
            }
        }catch (JSONException je){
            je.printStackTrace();
        }

    }
}
