package com.lanthanh.admin.icareapp.presentation.view.fragment.appointmenttab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.ArrayList;

/**
 * Created by ADMIN on 26-Nov-16.
 */

public class AppointmentDialogFragment extends DialogFragment {

    public AppointmentDialogFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_diaglog_fragment, container);
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

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        // Get field from view
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setTypeface(font);

        TextView address = (TextView) view.findViewById(R.id.address);
        address.setTypeface(font);

        TextView voucher = (TextView) view.findViewById(R.id.voucher);
        voucher.setTypeface(font);

        TextView type = (TextView) view.findViewById(R.id.type);
        type.setTypeface(font);

        TextView start_date = (TextView) view.findViewById(R.id.startdate);
        start_date.setTypeface(font);
        TextView start_date_title = (TextView) view.findViewById(R.id.startdatetitle);

        TextView expire_date = (TextView) view.findViewById(R.id.expiredate);
        expire_date.setTypeface(font);
        TextView expire_date_title = (TextView) view.findViewById(R.id.expiredatetitle);

        TextView app1 = (TextView) view.findViewById(R.id.appointment_1);
        app1.setTypeface(font);

        TextView app2 = (TextView) view.findViewById(R.id.appointment_2);
        app2.setTypeface(font);

        TextView app3 = (TextView) view.findViewById(R.id.appointment_3);
        app3.setTypeface(font);

        TextView code = (TextView) view.findViewById(R.id.confirm_code);
        code.setTypeface(font);

        TextView cancel = (TextView) view.findViewById(R.id.cancel_appointment);
        cancel.setTypeface(font);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.cancel_confirm))
                        .setPositiveButton(getString(R.string.agree_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getDialog().dismiss();
                                ((MainActivity) getActivity()).getMainPresenter().cancelAppointment(getArguments().getInt("appointmentId"));
                            }
                        })
                        .setNegativeButton(getString(R.string.abort_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true).show();
            }
        });

        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.button_close);
        button.setTypeface(font);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        // Fetch arguments from bundle
        name.setText(getArguments().getString("name", "Không Có"));
        address.setText(getArguments().getString("address", "Không Có"));
        voucher.setText(getArguments().getString("voucher", "Không Có"));
        type.setText(getArguments().getString("type", "Không Có"));

        if (getArguments().getString("start_date") != null) {
            if (getArguments().getString("start_date").equals("11/11/1111")) {
                start_date.setVisibility(View.GONE);
                start_date_title.setVisibility(View.GONE);
                //If start date = null => one day booking => while hiding start date, move expire date to the left
                //rename to Ngay thuc hien
                expire_date_title.setText(getString(R.string.booking_do_date));

                //move to left
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expire_date.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                RelativeLayout.LayoutParams params_title = (RelativeLayout.LayoutParams) expire_date_title.getLayoutParams();
                params_title.addRule(RelativeLayout.ALIGN_PARENT_START);
                expire_date.setLayoutParams(params);
                expire_date_title.setLayoutParams(params_title);
            } else
                start_date.setText(getArguments().getString("start_date"));
        }

        if (getArguments().getString("end_date") == null)
            System.out.println("Severe: No expire date from AppointmentCVAdapter class");
        else {
            expire_date.setText(getArguments().getString("end_date"));
        }

        ArrayList<String> schedules = getArguments().getStringArrayList("schedules");
        if (schedules != null){
            for (int i = 0; i < schedules.size(); i++){
                if (i == 0)
                    app1.setText(schedules.get(i));
                else if (i == 2)
                    app2.setText(schedules.get(i));
                else
                    app3.setText(schedules.get(i));
            }
        }

        code.setText(getArguments().getString("code", "Không Có"));

        if (!app2.getText().toString().isEmpty())
            app2.setVisibility(View.VISIBLE);
        if (!app3.getText().toString().isEmpty())
            app3.setVisibility(View.VISIBLE);
    }

}
