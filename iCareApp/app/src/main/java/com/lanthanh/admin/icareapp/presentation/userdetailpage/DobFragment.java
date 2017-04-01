package com.lanthanh.admin.icareapp.presentation.userdetailpage;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.utils.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 03-Dec-16.
 */

public class DobFragment extends DialogFragment {
    @BindView(R.id.ud_dob_txt) TextView dobTitle;
    @BindView(R.id.ud_cancel_button) TextView cancelButton;
    @BindView(R.id.ud_confirm_button) TextView confirmButton;
    @BindView(R.id.datePicker) DatePicker datePicker;

    private String date;
    private Unbinder unbinder;

    public DobFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        //Set up dialog size and position
        getDialog().getWindow().setLayout(850, 950);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userdetails_dob, null);
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

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        Typeface fontSemiBold = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_SEMIBOLD);
        cancelButton.setTypeface(font);
        confirmButton.setTypeface(font);
        dobTitle.setTypeface(fontSemiBold);

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
            (_view, year, monthOfYear, dayOfMonth) -> {
                date = ConverterForDisplay.convertDateToDisplay(year, monthOfYear, dayOfMonth);
            }
        );

        cancelButton.setOnClickListener(_view -> getDialog().dismiss());
        confirmButton.setOnClickListener(
            _view -> {
                ((UserDetailsActivity) getActivity()).setDobValue(date);
                getDialog().dismiss();
            }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
