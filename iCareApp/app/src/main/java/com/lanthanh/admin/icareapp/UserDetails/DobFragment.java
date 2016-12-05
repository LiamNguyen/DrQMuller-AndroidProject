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
import android.widget.DatePicker;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.R;

/**
 * Created by ADMIN on 03-Dec-16.
 */

public class DobFragment extends DialogFragment implements DatePicker.OnDateChangedListener{
    private Controller aController;
    private TextView dob_noti;
    private boolean isChecked;
    private String date;

    public DobFragment(){
        aController = Controller.getInstance();
        isChecked = false;
    }

    public static DobFragment newInstance() {
        DobFragment frag = new DobFragment();
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(600, 800);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Semibold.ttf");//Custom font
        // Get field from view
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
                    ((UserDetailsActivity) getActivity()).dob.setText(date);
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        isChecked = true;
        aController.getUserInfo().addInfo("dob", Integer.toString(year) + "-" + Integer.toString(monthOfYear+1) + "-" + Integer.toString(dayOfMonth));
        date = ((Integer.toString(dayOfMonth).length() == 1)?("0" + Integer.toString(dayOfMonth)) : Integer.toString(dayOfMonth))
                + "-" + ((Integer.toString(monthOfYear + 1).length() == 1)?("0" + Integer.toString(monthOfYear + 1)) : Integer.toString(monthOfYear + 1))
                + "-" + Integer.toString(year);
    }
}
