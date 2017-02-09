package com.lanthanh.admin.icareapp.presentation.view.fragment.booking;

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
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingSelectDatePresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.BookingSelectDatePresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.activity.BookingActivity;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

/**
 * Created by ADMIN on 25-Jan-17.
 */

public class BookingSelectDateFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener, BookingSelectDatePresenter.View{
    public static final String TAG = BookingSelectDateFragment.class.getSimpleName();
    private BookingSelectDatePresenter bookingSelectDatePresenter;
    private BookingActivityPresenter bookingActivityPresenter;
    private TextInputEditText startDate, expireDate;
    private TextView startDateText, expireDateText;
    private DatePickerDialog startDatePickerDialog, expireDatePickerDialog;
    private int type, voucher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_selectdate, container, false);

        init();

        /* =============================== PICK DATE =============================== */
        //Initialize TextView
        startDateText = (TextView) view.findViewById(R.id.booking_startdate_text);
        expireDateText = (TextView) view.findViewById(R.id.booking_expiredate_text);

        //Initialize TextInputEditText
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        startDate = (TextInputEditText) view.findViewById(R.id.booking_startdate);
        startDate.setOnClickListener(this);
        startDate.setTypeface(font);
        expireDate = (TextInputEditText) view.findViewById(R.id.booking_expiredate);
        expireDate.setOnClickListener(this);
        expireDate.setTypeface(font);

        //Initialize DatePickerDialog
        startDatePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        expireDatePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        //Set up floating button
        FloatingActionButton nextBut = (FloatingActionButton) view.findViewById(R.id.fab);
        nextBut.setOnClickListener(this);

        bookingSelectDatePresenter.resume();

        return view;
    }

    public void init(){
        //Init presenter
        bookingActivityPresenter = ((BookingActivity) getActivity()).getMainPresenter();
        bookingSelectDatePresenter = new BookingSelectDatePresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, bookingActivityPresenter.getDTOAppointment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.fab:
                if (bookingSelectDatePresenter.isAllInfoFilled())
                    bookingActivityPresenter.navigateTab(BookingActivity.BOOK);
                else
                    showError(getString(R.string.missing_detail));
                break;
            case R.id.booking_startdate:
                bookingSelectDatePresenter.onStartDatePickerClick();
                break;
            case R.id.booking_expiredate:
                bookingSelectDatePresenter.onExpireDatePickerClick();
                break;
        }
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

    @Override
    public void showStartDatePicker(Calendar calendar) {
        startDatePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        startDatePickerDialog.show();
    }

    @Override
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

    @Override
    public void hideProgress() {
        ((BookingActivity) getActivity()).hideProgress();
    }

    @Override
    public void showProgress() {
        ((BookingActivity) getActivity()).showProgress();
    }

    @Override
    public void showError(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 110);
        toast.show();
    }

    @Override
    public String getStringResource(int id) {
        return getString(id);
    }
}
