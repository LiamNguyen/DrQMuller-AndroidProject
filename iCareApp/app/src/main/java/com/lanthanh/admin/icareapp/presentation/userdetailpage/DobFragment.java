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
import android.widget.DatePicker;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.presenter.UserDetailsActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.userdetailpage.UserDetailsActivity;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

/**
 * Created by ADMIN on 03-Dec-16.
 */

public class DobFragment extends DialogFragment implements DatePicker.OnDateChangedListener{
    private UserDetailsActivityPresenter userDetailsActivityPresenter;
    private TextView dob_noti;
    private boolean isChecked;
    private String date;

    public DobFragment(){
        isChecked = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Set up dialog size and position
        getDialog().getWindow().setLayout(600, 800);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userDetailsActivityPresenter = ((UserDetailsActivity) getActivity()).getMainPresenter();
        return inflater.inflate(R.layout.userdetails_dob, container);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Custom font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_SEMIBOLD);
        //Get and set up view
        TextView dob_txt = (TextView) view.findViewById(R.id.ud_dob_txt);
        dob_txt.setTypeface(font2);
        dob_noti = (TextView) view.findViewById(R.id.ud_dob_noti);
        dob_noti.setTypeface(font);
        final DatePicker dPicker = (DatePicker) view.findViewById(R.id.datePicker);
        dPicker.init(1998, 0, 1, this);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.button_close);
        button.setTypeface(font);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isChecked)
                    dob_noti.setVisibility(View.VISIBLE);
                else {
                    ((UserDetailsActivity) getActivity()).setDobView(date);
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        isChecked = true;
        userDetailsActivityPresenter.setDob(
                    Integer.toString(year) + "-"
                +   ((Integer.toString(monthOfYear + 1).length() == 1)?("0" + Integer.toString(monthOfYear + 1)) : Integer.toString(monthOfYear + 1)) + "-"
                +   ((Integer.toString(dayOfMonth).length() == 1)?("0" + Integer.toString(dayOfMonth)) : Integer.toString(dayOfMonth)));
        //Set up date string for display
        date = ConverterForDisplay.convertDateToDisplay(year, monthOfYear, dayOfMonth);
    }
}
