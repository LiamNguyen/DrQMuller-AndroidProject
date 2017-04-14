package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.utils.ConverterUtils;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class ConfirmBookingActivity extends BaseActivity {
    @BindView(R.id.booking_confirm_input) TextInputEditText editCode;
    @BindView(R.id.booking_confirm_container) TextInputLayout editCodeContainer;
    @BindView(R.id.booking_instruction) TextView instruction;
    @BindView(R.id.confirm_button) AppCompatButton confirmButton;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolBar;

    private ConfirmBookingActivityPresenter confirmBookingActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);

        init();

        //Set up Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        confirmButton.setTypeface(font);
        editCode.setTypeface(font);
        editCodeContainer.setTypeface(font);
        instruction.setTypeface(font);

        editCode.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editCode.getText().toString().isEmpty())
                    confirmButton.setEnabled(false);
                else
                    confirmButton.setEnabled(true);
            }
        });

        confirmButton.setOnClickListener(
                view -> {
                    hideSoftKeyboard();
                    confirmBookingActivityPresenter.confirmAppointment(editCode.getText().toString().trim());
                }
        );
        confirmButton.setEnabled(false);
    }

    public void init(){
        confirmBookingActivityPresenter = new ConfirmBookingActivityPresenter(this);
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("intentkey");
        DTOAppointment appointment = ConverterUtils.json.convertJsonToObject(data.getString("appointment", ""), DTOAppointment.class);
        confirmBookingActivityPresenter.setCurrentAppointment(appointment);
        confirmBookingActivityPresenter.sendEmailNotifyBooking();
    }

    @Override
    protected void onResume() {
        super.onResume();
        confirmBookingActivityPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        confirmBookingActivityPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        confirmBookingActivityPresenter.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {
        hideProgress();
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.booking_fail))
                .setPositiveButton(
                    getString(R.string.agree_button),
                    (DialogInterface dialog, int which) -> {
                        dialog.dismiss();
                        confirmBookingActivityPresenter.navigateActivity(MainActivity.class);
                    }
                )
                .setNegativeButton(
                    getString(R.string.close_dialog),
                    (DialogInterface dialog, int which) -> dialog.dismiss()
                )
                .setCancelable(false).show();
    }

    @Override
    public void refreshAfterLosingNetwork() {
        confirmBookingActivityPresenter.sendEmailNotifyBooking();
    }
}