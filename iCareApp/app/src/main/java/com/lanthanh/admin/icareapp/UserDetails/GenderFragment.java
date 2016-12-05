package com.lanthanh.admin.icareapp.UserDetails;

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

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.Service.DetailsFragment;

/**
 * Created by ADMIN on 03-Dec-16.
 */

public class GenderFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener{
    private Controller aController;

    public GenderFragment(){
        aController = Controller.getInstance();
    }

    public static GenderFragment newInstance() {
        GenderFragment frag = new GenderFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        getDialog().getWindow().setLayout(470, 600);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Semibold.ttf");//Custom font
        // Get field from view
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
                        ((UserDetailsActivity) getActivity()).gender.setText(getActivity().getString(R.string.male));
                    }else{
                        ((UserDetailsActivity) getActivity()).gender.setText(getActivity().getString(R.string.female));
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
                aController.getUserInfo().addInfo("gender", "Male");
                break;
            case R.id.ui_female:
                aController.getUserInfo().addInfo("gender", "Female");
            default:
                break;
        }
    }
}