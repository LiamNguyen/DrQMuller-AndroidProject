package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment2;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 18-Oct-16.
 */

public class ChooseFragment extends BaseFragment2<WelcomeContract.Presenter> {
    @BindView(R.id.wel_log_in_button) AppCompatButton logInButton;
    @BindView(R.id.wel_sign_up_button) AppCompatButton signUpButton;
    @BindView(R.id.wel_text) TextView welcomeText;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register_choose, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews(){
        ((WelcomeActivity) getActivity()).showToolbar(false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_WELCOME);
        Typeface font_light = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        welcomeText.setTypeface(font);
        signUpButton.setTypeface(font_light);
        logInButton.setTypeface(font_light);

        logInButton.setOnClickListener(view ->  ((WelcomeActivity) getActivity()).showLogInPage());
        signUpButton.setOnClickListener(view -> ((WelcomeActivity) getActivity()).showSignUpPage());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            ((WelcomeActivity) getActivity()).showToolbar(false);
    }

    @Override
    public void refreshViews() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

