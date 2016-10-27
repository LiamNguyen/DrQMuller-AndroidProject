package com.example.admin.icareapp.Register;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.icareapp.R;
import com.example.admin.icareapp.Register.RegisterActivity;

/**
 * Created by ADMIN on 18-Oct-16.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button sign_in_button = (Button) view.findViewById(R.id.wel_sign_in_button);
        sign_in_button.setOnClickListener(this);

        Button sign_up_button = (Button) view.findViewById(R.id.wel_sign_up_button);
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
