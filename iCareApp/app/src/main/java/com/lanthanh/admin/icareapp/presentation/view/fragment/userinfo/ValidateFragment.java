package com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.UserInfoActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 08-Nov-16.
 */

public class ValidateFragment extends Fragment implements View.OnClickListener{
    private UserInfoActivityPresenter userInfoActivityPresenter;
    private TextView noti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_validate, container, false);

        init();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        ((UserInfoActivity) getActivity()).isToolBarHidden(true);
        AppCompatButton resend = (AppCompatButton) view.findViewById(R.id.ui_resend_email_button);
        resend.setOnClickListener(this);
        resend.setTypeface(font);
        AppCompatButton change = (AppCompatButton) view.findViewById(R.id.ui_change_email_button);
        change.setOnClickListener(this);
        change.setTypeface(font);
        AppCompatButton back = (AppCompatButton) view.findViewById(R.id.ui_back_to_register);
        back.setOnClickListener(this);
        back.setTypeface(font);
        noti = (TextView) view.findViewById(R.id.ui_validate_noti);
        noti.setTypeface(font);

        userInfoActivityPresenter.sendEmailVerifyAcc();

        return view;
    }

    public void init(){
        userInfoActivityPresenter = ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            ((UserInfoActivity) getActivity()).isToolBarHidden(true);
        else
            ((UserInfoActivity) getActivity()).isToolBarHidden(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_resend_email_button:
                userInfoActivityPresenter.sendEmailVerifyAcc();
                break;
            case R.id.ui_change_email_button:
                userInfoActivityPresenter.navigateFragment(UserInfoActivity.CHANGEEMAIL);
                break;
            case R.id.ui_back_to_register:
                userInfoActivityPresenter.navigateToRegisterActivity();
                break;
            default:
                break;
        }
    }

    public void showEmailResult(int string){
        noti.setText(string);
        noti.setVisibility(View.VISIBLE);
    }
}
