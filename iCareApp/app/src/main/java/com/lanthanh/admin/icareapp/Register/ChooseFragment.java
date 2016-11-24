package com.lanthanh.admin.icareapp.Register;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;

/**
 * Created by ADMIN on 18-Oct-16.
 */

public class ChooseFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_choose, container, false);

        //Log In Button
        AppCompatButton log_in_button = (AppCompatButton) view.findViewById(R.id.wel_log_in_button);
        log_in_button.setOnClickListener(this);

        //Sign Up Button
        AppCompatButton sign_up_button = (AppCompatButton) view.findViewById(R.id.wel_sign_up_button);
        sign_up_button.setOnClickListener(this);

        //Welcome text
        TextView wel_txt = (TextView) view.findViewById(R.id.wel_text);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SourceSansPro-Light.ttf");
        wel_txt.setTypeface(font);
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        log_in_button.setTypeface(font2);
        sign_up_button.setTypeface(font2);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wel_log_in_button:
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
