package com.lanthanh.admin.icareapp.Register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class SignInFragment extends Fragment implements View.OnClickListener, DatabaseObserver {
    private TextInputEditText username;
    private TextInputEditText password;
    private Controller aController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_sign_in, container, false);

        ImageButton back_button = (ImageButton) view.findViewById(R.id.si_back_button);
        back_button.setOnClickListener(this);

        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.si_sign_in_button);
        sign_in_button.setOnClickListener(this);

        TextView forget_pw = (TextView) view.findViewById(R.id.si_forget_pw);
        forget_pw.setOnClickListener(this);

        username = (TextInputEditText) view.findViewById(R.id.si_username_input);
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
                aController.getAccount().setLoginID(username.getText().toString());
                aController.getAccount().setPassword(password.getText().toString());
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(), aController.getAccount().getPostData());
                break;
            case R.id.si_forget_pw:
                ((RegisterActivity) getActivity()).navigateToResetPW();
            default:
                break;
        }
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        if (status == null) {
            System.out.println("ERROR IN PHP FILE");
            return;
        }

        try{
            if (status.has("Select_ToAuthenticate")){
                //String result = status.getString("Select_ToAuthenticate");
                String response = status.getString("Select_ToAuthenticate");//Get the array of days' JSONObject
                System.out.println(response);
                if (!response.isEmpty()){
                    if (response.equals("LoginFail") || response.equals("Fail")){
                        Toast.makeText(getActivity(), "Tên Đăng Nhập Hoặc Mật Khẩu Sai", Toast.LENGTH_LONG).show();
                    }else{
                        String tokenID, tokenName;
                        tokenID = response.substring(response.indexOf("+")+1, response.indexOf("-"));
                        tokenName = response.substring(response.indexOf("-")+1, response.length());
                        putTokenToPref(tokenID, tokenName);
                        Intent toMain = new Intent(getActivity(), MainActivity.class);
                        toMain.putExtra("isSignedIn", 1);
                        startActivity(toMain);
                        getActivity().finish();
                    }
                }
            }
        }catch (JSONException je){
            je.printStackTrace();
        }

    }

    public void putTokenToPref(String id, String name){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("tokenID", id);
        editor.putString("tokenName", name);
        editor.apply();
        editor.commit();
    }
}
