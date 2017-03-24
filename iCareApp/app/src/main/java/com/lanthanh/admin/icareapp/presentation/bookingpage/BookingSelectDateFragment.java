package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import java.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.presentation.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingSelectDatePresenter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 25-Jan-17.
 */

public class BookingSelectDateFragment extends BaseFragment<BookingActivityPresenterImpl> implements DatePickerDialog.OnDateSetListener{
    @BindView(R.id.booking_startdate_text) TextInputEditText startDate;
    @BindView(R.id.booking_expiredate_text) TextInputEditText expireDate;
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
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        startDate.setTypeface(font);
        expireDate.setTypeface(font);

        startDatePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        expireDatePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        startDate.setOnClickListener(
            view -> {

            }
        );
        expireDate.setOnClickListener(view ->  bookingSelectDatePresenter.onExpireDatePickerClick());
        nextButton.setOnClickListener(view -> getMainPresenter().navigateFragment(BookingBookFragment.class));

        nextButton.setEnabled(false);
    }

    @Override
    public void resetViews() {

    }

    @Override
    public BookingActivityPresenterImpl getMainPresenter() {
        return null;
    }

    @Override
    public ApplicationProvider getProvider() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, day);
        if (startDatePickerDialog.getDatePicker().hashCode() == datePicker.hashCode()) {
            bookingSelectDatePresenter.onStartDateSet(calendar);
        }
        else
            bookingSelectDatePresenter.onExpireDateSet(calendar);
    }

    @Override
    public void displayStartDate(String startDate) {
//        if (startDate == null)
//            this.startDate.setText(getActivity().getString(R.string.booking_start_date));
//        else
        this.startDate.setText(startDate);
    }

    @Override
    public void displayExpireDate(String expireDate) {
//        if (expireDate == null)
//            this.expireDate.setText(getActivity().getString(R.string.booking_expire_date));
//        else
        this.expireDate.setText(expireDate);
    }

    @Override
    public void enableExpireDate() {
        expireDate.setEnabled(true);
    }


    public void showStartDatePicker(Calendar calendar) {
        startDatePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        startDatePickerDialog.show();
    }

    public void showExpireDatePicker(Calendar calendar) {
        expireDatePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        expireDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        expireDatePickerDialog.show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            dateDisplayOnTypeOrVoucherChange();
            bookingSelectDatePresenter.resume();
        }
    }

    @Override
    public void dateDisplayOnTypeOrVoucherChange() {
        if (bookingActivityPresenter.getDTOAppointment().getTypeId() == type && bookingActivityPresenter.getDTOAppointment().getVoucherId() == voucher)
            return;

        bookingSelectDatePresenter.resetStartDate();
        bookingSelectDatePresenter.resetExpireDate();
        type = bookingActivityPresenter.getDTOAppointment().getTypeId();
        voucher = bookingActivityPresenter.getDTOAppointment().getVoucherId();

        if (type == 1) {
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
