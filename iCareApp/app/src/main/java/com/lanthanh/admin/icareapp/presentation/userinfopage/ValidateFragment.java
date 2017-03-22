package com.lanthanh.admin.icareapp.presentation.userinfopage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.view.fragment.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;

/**
 * Created by ADMIN on 08-Nov-16.
 */

public class ValidateFragment extends BaseFragment<UserInfoActivityPresenterImpl>{
    @BindView(R.id.ui_resend_email_button) AppCompatButton resendEmailButton;
    @BindView(R.id.ui_change_email_button) AppCompatButton changeEmailButton;
    @BindView(R.id.ui_back_to_register) AppCompatButton toRegisterButton;
    @BindView(R.id.ui_validate_noti) TextView validateMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_validate, container, false);


        return view;
    }

    @Override
    public void initViews() {
        validateMessage.setVisibility(View.INVISIBLE);
        ((UserInfoActivity) getActivity()).showToolbar(false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        resendEmailButton.setTypeface(font);
        changeEmailButton.setTypeface(font);
        toRegisterButton.setTypeface(font);
        validateMessage.setTypeface(font);

        resendEmailButton.setOnClickListener(view -> System.out.println("hihi"));//TODO check send email to verify account
        changeEmailButton.setOnClickListener(view -> getMainPresenter().navigateFragment(ChangeEmailFragment.class));
        toRegisterButton.setOnClickListener(view -> getMainPresenter().navigateActivity(WelcomeActivity.class));
    }

    @Override
    public void resetViews() {

    }

    @Override
    public UserInfoActivityPresenterImpl getMainPresenter() {
        return ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            ((UserInfoActivity) getActivity()).showToolbar(false);
    }

    public void showEmailResult(int string){
        validateMessage.setText(string);
        validateMessage.setVisibility(View.VISIBLE);
    }
}
