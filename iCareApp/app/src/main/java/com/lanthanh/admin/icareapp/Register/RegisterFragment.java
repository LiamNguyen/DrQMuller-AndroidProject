package com.lanthanh.admin.icareapp.Register;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;

/**
 * Created by ADMIN on 18-Oct-16.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_choose, container, false);

        AppCompatButton sign_in_button = (AppCompatButton) view.findViewById(R.id.wel_sign_in_button);
        sign_in_button.setOnClickListener(this);

        AppCompatButton sign_up_button = (AppCompatButton) view.findViewById(R.id.wel_sign_up_button);
        sign_up_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wel_sign_in_button:
                ((RegisterActivity) getActivity()).navigateToSignIn();
                break;
            case R.id.wel_sign_up_button:
                ((RegisterActivity) getActivity()).navigateToSignUp();
                break;
            default:
                break;
        }
    }
}