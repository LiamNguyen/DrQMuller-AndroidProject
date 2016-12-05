package com.lanthanh.admin.icareapp.Register;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.jwt.JWTVerifier;
import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

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
        
        aController = Controller.getInstance();
        
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        
        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.si_sign_in_button);
        sign_in_button.setOnClickListener(this);
        sign_in_button.setTypeface(font);
        
        TextView forget_pw = (TextView) view.findViewById(R.id.si_forget_pw);
        forget_pw.setOnClickListener(this);
        forget_pw.setTypeface(font);
        
        username = (TextInputEditText) view.findViewById(R.id.si_username_input);
        username.setTypeface(font);
        password = (TextInputEditText) view.findViewById(R.id.si_password_input);
        password.setTypeface(font);

        TextInputLayout username_container = (TextInputLayout) view.findViewById(R.id.si_username_container);
        username_container.setTypeface(font);
        TextInputLayout password_container = (TextInputLayout) view.findViewById(R.id.si_password_container);
        password_container.setTypeface(font);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.si_back_button:
//                ((RegisterActivity) getActivity()).navigateBack();
//                break;
            case R.id.si_sign_in_button:
                aController.getAccount().setLoginID(username.getText().toString());
                aController.getAccount().setPassword(password.getText().toString());
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_TOAUTHENTICATE.getUrl(MainActivity.isUAT), aController.getAccount().getPostData());
                ((RegisterActivity) getActivity()).hideSoftKeyboard();
                break;
            case R.id.si_forget_pw:
                ((RegisterActivity) getActivity()).navigateToResetPW();
                getActivity().finish();
                ((RegisterActivity) getActivity()).hideSoftKeyboard();
            default:
                break;
        }
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;
        System.out.println(status);
        if (status == null) {
            System.out.println("ERROR IN PHP FILE");
            return;
        }

        try{
            if (status.has("Select_ToAuthenticate")) {
                //String result = status.getString("Select_ToAuthenticate");
                if (status.get("Select_ToAuthenticate") instanceof String) {
                    String response = status.getString("Select_ToAuthenticate");
                    if (!response.isEmpty()) {
                        if (response.equals("LoginFail") || response.equals("Fail")) {
                            Toast.makeText(getActivity(), "Tên Đăng Nhập Hoặc Mật Khẩu Sai", Toast.LENGTH_SHORT).show();
                        }
                    }
//                    String tokenID, tokenName;
//                    tokenID = response.substring(response.indexOf("+") + 1, response.indexOf("-"));
//                    tokenName = response.substring(response.indexOf("-") + 1, response.length());
//                    putTokenToPref(tokenID, tokenName);
                }else {
                    JSONArray jArray = status.getJSONArray("Select_ToAuthenticate");//Get the array of time
                    JSONObject jObJWT = jArray.getJSONObject(0);
                    boolean check = parseJWT(jObJWT.getString("jwt"));
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);
                    if (check) {
                        if (sharedPref.getString("active", "0").equals("0")) {
                            ((RegisterActivity) getActivity()).navigateToUserInfo();
                        } else {
                            ((RegisterActivity) getActivity()).navigateToBookingActivity();
                        }
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

    public boolean parseJWT(String jwt){
        try {
            final JWTVerifier verifier = new JWTVerifier("drmuller");
            final Map<String, String> jwtClaims= (Map<String,String>) verifier.verify(jwt).get("data");

            SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("tokenID", jwtClaims.get("userId"));
            editor.putString("tokenName", jwtClaims.get("userName"));
            editor.putString("tokenAddress", jwtClaims.get("userAddress"));
            if (jwtClaims.get("userDob") != null) {
                editor.putString("tokenDob", formatDate(jwtClaims.get("userDob")));
            }else{
                editor.putString("tokenDob", jwtClaims.get("userDob"));
            }
            if (jwtClaims.get("userGender") != null) {
                if (jwtClaims.get("userGender").equals("Male")) {
                    editor.putString("tokenGender", getActivity().getString(R.string.male));
                } else {
                    editor.putString("tokenGender", getActivity().getString(R.string.female));
                }
            }else {
                editor.putString("tokenGender", jwtClaims.get("userGender"));
            }
            editor.putString("tokenEmail", jwtClaims.get("userEmail"));
            editor.putString("tokenPhone", jwtClaims.get("userPhone"));
            editor.putString("step", jwtClaims.get("step"));
            editor.putString("active", jwtClaims.get("active"));
            editor.apply();
            editor.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String formatDate(String s){
        StringBuilder builder = new StringBuilder();
        builder.append(s.substring(s.lastIndexOf("-")+1, s.length()));
        builder.append("-");
        builder.append(s.substring(s.indexOf("-")+1, s.lastIndexOf("-")));
        builder.append("-");
        builder.append(s.substring(0, s.indexOf("-")));
        return builder.toString();
    }
}
