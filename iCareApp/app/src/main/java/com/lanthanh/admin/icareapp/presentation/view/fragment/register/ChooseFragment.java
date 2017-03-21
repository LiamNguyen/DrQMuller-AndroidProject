package com.lanthanh.admin.icareapp.presentation.view.fragment.register;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.RegisterActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;

/**
 * Created by ADMIN on 18-Oct-16.
 */

public class ChooseFragment extends BaseFragment implements View.OnClickListener{
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
        ((RegisterActivity) getActivity()).showToolbar(false);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_WELCOME);
        Typeface font_light = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        welcomeText.setTypeface(font);
        signUpButton.setTypeface(font_light);
        logInButton.setTypeface(font_light);
        logInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void resetViews() {}

    @Override
    public RegisterActivityPresenterImpl getMainPresenter(){
        return ((RegisterActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            ((RegisterActivity) getActivity()).showToolbar(false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wel_log_in_button:
                getMainPresenter().navigateFragment(LogInFragment.class);
                break;
            case R.id.wel_sign_up_button:
                getMainPresenter().navigateFragment(SignUpFragment.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
