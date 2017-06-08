package com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab;

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
import com.lanthanh.admin.icareapp.presentation.adapter.AppointmentCVAdapter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 26-Nov-16.
 */

public class AppointmentDialogFragment extends DialogFragment {
    @BindView(R.id.name) TextView name;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.voucher) TextView voucher;
    @BindView(R.id.type) TextView type;
    @BindView(R.id.startdate) TextView startDate;
    @BindView(R.id.startdatetitle) TextView startDateTitle;
    @BindView(R.id.expiredate) TextView expireDate;
    @BindView(R.id.expiredatetitle) TextView expireDateTitle;
    @BindView(R.id.appointment_1) TextView appointment1;
    @BindView(R.id.appointment_2) TextView appointment2;
    @BindView(R.id.appointment_3) TextView appointment3;
    @BindView(R.id.cancel_appointment) TextView cancelText;
    @BindView(R.id.button_close) AppCompatButton closeButton;
    private Unbinder unbinder;


    public AppointmentDialogFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_diaglog_fragment, container);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        name.setTypeface(font);
        address.setTypeface(font);
        voucher.setTypeface(font);
        type.setTypeface(font);
        startDate.setTypeface(font);
        expireDate.setTypeface(font);
        appointment1.setTypeface(font);
        appointment2.setTypeface(font);
        appointment3.setTypeface(font);
        cancelText.setTypeface(font);
        closeButton.setTypeface(font);

        cancelText.setOnClickListener(
                clickedView -> {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.cancel_confirm))
                            .setPositiveButton(
                                getString(R.string.agree_button),
                                (DialogInterface dialog, int which) -> {
                                    getDialog().dismiss();
                                    ((MainActivity) getActivity()).getMainPresenter().cancelAppointment(getArguments().getString("appointmentId", ""));
                                }
                            )
                            .setNegativeButton(
                                    getString(R.string.no_button),
                                    (DialogInterface dialog, int which) -> dialog.dismiss()
                            )
                            .setCancelable(true).show();
                }
        );
        closeButton.setOnClickListener(clickedView -> getDialog().dismiss());

        // Fetch arguments from bundle
        name.setText(getArguments().getString(AppointmentCVAdapter.APPOINTMENT_CUSTOMER_NAME, getContext().getString(R.string.none)));
        address.setText(getArguments().getString(AppointmentCVAdapter.APPOINTMENT_ADDRESS, getContext().getString(R.string.none)));
        voucher.setText(getArguments().getString(AppointmentCVAdapter.APPOINTMENT_VOUCHER, getContext().getString(R.string.none)));
        type.setText(getArguments().getString(AppointmentCVAdapter.APPOINTMENT_TYPE, getContext().getString(R.string.none)));

        if (getArguments().getString(AppointmentCVAdapter.APPOINTMENT_START_DATE) != null) {
            if (getArguments().getString(AppointmentCVAdapter.APPOINTMENT_START_DATE).equals("11/11/1111")) {
                startDateTitle.setVisibility(View.GONE);
                startDate.setVisibility(View.GONE);
                //If start date = null => one day booking => while hiding start date, move expire date to the left
                //rename to Ngay thuc hien
                expireDateTitle.setText(getString(R.string.booking_do_date));

                //move to left
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expireDate.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                RelativeLayout.LayoutParams params_title = (RelativeLayout.LayoutParams) expireDateTitle.getLayoutParams();
                params_title.addRule(RelativeLayout.ALIGN_PARENT_START);
                expireDate.setLayoutParams(params);
                expireDateTitle.setLayoutParams(params_title);
            } else
                startDate.setText(getArguments().getString(AppointmentCVAdapter.APPOINTMENT_START_DATE));
        }

        if (getArguments().getString(AppointmentCVAdapter.APPOINTMENT_EXPIRE_DATE) != null) {
            expireDate.setText(getArguments().getString(AppointmentCVAdapter.APPOINTMENT_EXPIRE_DATE));
        }

        ArrayList<String> schedules = getArguments().getStringArrayList(AppointmentCVAdapter.APPOINTMENT_SCHEDULES);
        if (schedules != null){
            for (int i = 0; i < schedules.size(); i++){
                if (i == 0)
                    appointment1.setText(schedules.get(i));
                else if (i == 2)
                    appointment2.setText(schedules.get(i));
                else
                    appointment3.setText(schedules.get(i));
            }
        }

        if (!appointment2.getText().toString().isEmpty())
            appointment2.setVisibility(View.VISIBLE);
        if (!appointment3.getText().toString().isEmpty())
            appointment3.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
