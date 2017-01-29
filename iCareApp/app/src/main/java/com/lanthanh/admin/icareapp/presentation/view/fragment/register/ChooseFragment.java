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
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;

/**
 * Created by ADMIN on 18-Oct-16.
 */

public class ChooseFragment extends Fragment implements View.OnClickListener{
    private RegisterActivityPresenter registerActivityPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register_choose, container, false);

        init();

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

    public void init() {
        registerActivityPresenter = ((RegisterActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            ((RegisterActivity) getActivity()).isToolBarHidden(true);
        else
            ((RegisterActivity) getActivity()).isToolBarHidden(false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wel_log_in_button:
                registerActivityPresenter.navigateFragment(RegisterActivity.LOG_IN);
                break;
            case R.id.wel_sign_up_button:
                registerActivityPresenter.navigateFragment(RegisterActivity.SIGN_UP);
                break;
            default:
                break;
        }
    }
}
