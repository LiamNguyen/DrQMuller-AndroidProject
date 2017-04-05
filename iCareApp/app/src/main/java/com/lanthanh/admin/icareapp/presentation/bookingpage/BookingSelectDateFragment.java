package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import java.util.Calendar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 25-Jan-17.
 */

public class BookingSelectDateFragment extends BaseFragment<BookingActivityPresenter>{
    @BindView(R.id.booking_startdate) TextView startDate;
    @BindView(R.id.booking_expiredate) TextView expireDate;
    @BindView(R.id.booking_startdate_text) TextView startDateText;
    @BindView(R.id.booking_expiredate_text) TextView expireDateText;
    @BindView(R.id.fab) FloatingActionButton nextButton;

    private DatePickerDialog startDatePickerDialog, expireDatePickerDialog;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_selectdate, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        refreshViews();
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        startDate.setTypeface(font);
        expireDate.setTypeface(font);

        //Create date picker dialog
        initStartDatePickerDialog();
        initExpireDatePickerDialog();

        //Response to click
        startDate.setOnClickListener(view -> getMainPresenter().onStartDatePickerClick(
            calendar -> {
                initStartDatePickerDialog();
                startDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                startDatePickerDialog.show();
            }
        ));
        expireDate.setOnClickListener(view ->  getMainPresenter().onExpireDatePickerClick(
            calendar -> {
                initExpireDatePickerDialog();
                expireDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                expireDatePickerDialog.show();
            }
        ));
        nextButton.setOnClickListener(view -> {
                getMainPresenter().validateAppointment();
                getMainPresenter().navigateFragment(BookingBookFragment.class);
            }
        );
        nextButton.setEnabled(false);
    }

    public void initStartDatePickerDialog() {
        startDatePickerDialog = new DatePickerDialog(
                getActivity(),
                (DatePicker datePicker, int year, int month, int day) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    getMainPresenter().onStartDateSet(calendar, success -> { startDate.setText(success); expireDate.setEnabled(true);}, ((BookingActivity) getActivity())::showToast);
                },
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
    }

    public void initExpireDatePickerDialog() {
        expireDatePickerDialog = new DatePickerDialog(
                getActivity(),
                (DatePicker datePicker, int year, int month, int day) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    getMainPresenter().onExpireDateSet(calendar, this.expireDate::setText, ((BookingActivity) getActivity())::showToast);
                },
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
    }

    public void enableNextButton(boolean shouldEnable) {
        nextButton.setEnabled(shouldEnable);
        setFabTint(nextButton, shouldEnable);
    }

    @Override
    public void refreshViews() {
        getMainPresenter().resetPickerView(this::resetDatePickerView);
    }

    @Override
    public BookingActivityPresenter getMainPresenter() {
        return ((BookingActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            refreshViews();
        }
    }

    public void resetDatePickerView(int typeId) {
        nextButton.setEnabled(false);
        setFabTint(nextButton, false);
        if (typeId == 1) {
            //If type = Co dinh, show start date. Set expire date to Ngay Ket Thuc
            startDate.setVisibility(View.VISIBLE);
            startDate.setText(getString(R.string.booking_date_hint));
            startDateText.setVisibility(View.VISIBLE);
            expireDateText.setText(getString(R.string.booking_expire_date));
            expireDate.setEnabled(false);
            expireDate.setText(getString(R.string.booking_date_hint));
        } else {
            //If type = Tu do, hide start date. Set expire date to Ngay Thuc Hien
            startDate.setVisibility(View.GONE);
            startDateText.setVisibility(View.GONE);
            expireDateText.setText(getString(R.string.booking_do_date));
            expireDate.setEnabled(true);
            expireDate.setText(getString(R.string.booking_date_hint));
        }
    }

    public void setFabTint(FloatingActionButton fab, boolean isEnabled) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //for M and above (API >= 23)
            if (isEnabled)
                fab.setColorFilter(getResources().getColor(R.color.colorWhite, null), PorterDuff.Mode.SRC_ATOP);
            else
                fab.setColorFilter(getResources().getColor(R.color.colorPrimaryDark, null), PorterDuff.Mode.SRC_ATOP);
        } else{
            //below M (API <23)
            if (isEnabled)
                fab.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            else
                fab.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
