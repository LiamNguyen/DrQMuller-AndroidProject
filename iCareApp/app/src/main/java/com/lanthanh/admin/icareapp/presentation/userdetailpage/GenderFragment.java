package com.lanthanh.admin.icareapp.presentation.userdetailpage;

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
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 03-Dec-16.
 */

public class GenderFragment extends DialogFragment {
    @BindView(R.id.ud_gender_txt) TextView genderTitle;
    @BindView(R.id.ud_male) RadioButton maleButton;
    @BindView(R.id.ud_female) RadioButton femaleButton;
    @BindView(R.id.ud_cancel_button) TextView cancelButton;
    @BindView(R.id.ud_confirm_button) TextView confirmButton;
    @BindView(R.id.ud_gender_noti) TextView genderInvalid;

    private Unbinder unbinder;

    public GenderFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userdetails_gender, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
        if (GraphicUtils.getScreenSizeWidth(getActivity()) <= 720 && GraphicUtils.getScreenSizeHeight(getActivity()) <= 1200)
            getDialog().getWindow().setLayout(600, 580);
        else
            getDialog().getWindow().setLayout(600, 680);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Custom font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        Typeface fontSemiBold = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_SEMIBOLD);
        genderTitle.setTypeface(fontSemiBold);
        maleButton.setTypeface(font);
        femaleButton.setTypeface(font);
        cancelButton.setTypeface(font);
        confirmButton.setTypeface(font);

        cancelButton.setOnClickListener(_view -> getDialog().dismiss());
        confirmButton.setOnClickListener(
            _view -> {
                if (maleButton.isChecked() || femaleButton.isChecked()) {
                    if (maleButton.isChecked()) {
                        ((UserDetailsActivity) getActivity()).setGenderValue(getActivity().getString(R.string.male));
                    } else {
                        ((UserDetailsActivity) getActivity()).setGenderValue(getActivity().getString(R.string.female));
                    }
                    getDialog().dismiss();
                } else {
                    genderInvalid.setVisibility(View.VISIBLE);
                }
            }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}