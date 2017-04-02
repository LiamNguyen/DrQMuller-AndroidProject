package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.AppointmentRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public class ConfirmBookingActivityPresenter extends BasePresenter {
    private ConfirmBookingActivity activity;

    private AppointmentRepository appointmentRepository;
    private Interactor interactor;

    public ConfirmBookingActivityPresenter(ConfirmBookingActivity activity){
        this.activity = activity;
        init();
    }

    public void init(){
        appointmentRepository = new AppointmentRepositoryImpl(this.activity);
        interactor = new Interactor();
    }

    public void navigateActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this.activity, activityClass);
        this.activity.startActivity(intent);
        this.activity.finish();
    }

    public void navigateActivity(Class<? extends Activity> activityClass, Bundle b) {
        Intent intent = new Intent(this.activity, activityClass);
        intent.putExtra(this.getClass().getName(), b); //TODO check this put extra
        this.activity.startActivity(intent);
        this.activity.finish();
    }

    public void confirmAppointment(String verificationCode) {
        this.activity.showProgress();
        if (this.activity.getProvider().getCurrentAppointment().getVerificationCode().equals(verificationCode)) {
            interactor.execute(
                () -> appointmentRepository.confirmAppointment(),
                success -> {
                    this.activity.hideProgress();
                    if (success == RepositorySimpleStatus.SUCCESS) {
                        new AlertDialog.Builder(this.activity)
                                .setMessage(this.activity.getString(R.string.booking_success))
                                .setPositiveButton(
                                        this.activity.getString(R.string.close_dialog),
                                        (DialogInterface dialog, int which) -> {
                                            dialog.dismiss();
                                            navigateActivity(MainActivity.class);
                                        })
                                .setCancelable(false).show();
                    }
                },
                error -> this.activity.hideProgress()
            );
        } else {
            this.activity.hideProgress();
            this.activity.showToast(this.activity.getString(R.string.wrong_code));
        }
    }

    @Override
    public void resume() {
        interactor.execute(
                () -> appointmentRepository.sendEmailNotifyBooking(),
                _success -> Log.i(this.getClass().getName(), "Send email to notify booking successfully"),
                error -> Log.e(this.getClass().getName(), "Send email to notify booking fail")
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        interactor.dispose();
        this.activity.getProvider().setCurrentAppointment(null);
    }
}
