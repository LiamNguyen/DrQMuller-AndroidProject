package com.lanthanh.admin.icareapp.presentation.view.fragment.userdetails;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.UserDetailsActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserDetailsActivity;

/**
 * Created by ADMIN on 03-Dec-16.
 */

public class GenderFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener{
    private UserDetailsActivityPresenter userDetailsActivityPresenter;

    public GenderFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userDetailsActivityPresenter = ((UserDetailsActivity) getActivity()).getMainPresenter();
        return inflater.inflate(R.layout.userdetails_gender, container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Set dialog size and position
        getDialog().getWindow().setLayout(470, 600);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Custom font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Semibold.ttf");
        // Get and set up view
        TextView gender_txt = (TextView) view.findViewById(R.id.ud_gender_txt);
        gender_txt.setTypeface(font2);
        final TextView gender_noti = (TextView) view.findViewById(R.id.ud_gender_noti);
        gender_noti.setTypeface(font);
        final RadioButton male = (RadioButton) view.findViewById(R.id.ud_male);
        male.setTypeface(font);
        final RadioButton female = (RadioButton) view.findViewById(R.id.ud_female);
        female.setTypeface(font);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.ud_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.button_close);
        button.setTypeface(font);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!male.isChecked() && !female.isChecked())
                    gender_noti.setVisibility(View.VISIBLE);
                else{
                    if (male.isChecked()){
                        ((UserDetailsActivity) getActivity()).setGenderView(getActivity().getString(R.string.male));
                    }else{
                        ((UserDetailsActivity) getActivity()).setGenderView(getActivity().getString(R.string.female));
                    }
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId){
            case R.id.ui_male:
                userDetailsActivityPresenter.setEmail("Male");
                break;
            case R.id.ui_female:
                userDetailsActivityPresenter.setGender("Female");
            default:
                break;
        }
    }
}