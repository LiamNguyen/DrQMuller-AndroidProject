package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import java.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
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

public class BookingSelectDateFragment extends BaseFragment<BookingActivityPresenterImpl>{
    @BindView(R.id.booking_startdate) TextView startDate;
    @BindView(R.id.booking_expiredate) TextView expireDate;
    @BindView(R.id.booking_startdate_text) TextView startDateText;
    @BindView(R.id.booking_expiredate_text) TextView expireDateText;
    @BindView(R.id.fab) FloatingActionButton nextButton;

    private DatePickerDialog startDatePickerDialog, expireDatePickerDialog;
    private int type, voucher;
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
        resetViews();
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
        nextButton.setOnClickListener(view -> getMainPresenter().navigateFragment(BookingBookFragment.class));
        nextButton.setEnabled(false);
    }

    public void initStartDatePickerDialog() {
        startDatePickerDialog = new DatePickerDialog(
                getActivity(),
                (DatePicker datePicker, int year, int month, int day) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    getMainPresenter().onStartDateSet(calendar, success -> { startDate.setText(success); expireDate.setEnabled(true);}, this::showToast);
                    if (getProvider().getCurrentAppointment().isFirstSelectFilled()) {
                        nextButton.setEnabled(true);
                    } else {
                        nextButton.setEnabled(false);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public void initExpireDatePickerDialog() {
        expireDatePickerDialog = new DatePickerDialog(getActivity(),
                (DatePicker datePicker, int year, int month, int day) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    getMainPresenter().onExpireDateSet(calendar, this.expireDate::setText, this::showToast);
                },
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void resetViews() {
        getMainPresenter().setUpDatePickerView(this::resetDatePickerView);
    }

    @Override
    public BookingActivityPresenterImpl getMainPresenter() {
        return ((BookingActivity) getActivity()).getMainPresenter();
    }

    @Override
    public ApplicationProvider getProvider() {
        return ((BookingActivity) getActivity()).getProvider();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            resetViews();
        }
    }

    public void resetDatePickerView(int typeId) {
        nextButton.setEnabled(false);
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

}
