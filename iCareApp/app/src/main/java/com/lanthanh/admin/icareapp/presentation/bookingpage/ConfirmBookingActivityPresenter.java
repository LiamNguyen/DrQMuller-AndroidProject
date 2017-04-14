package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.AppointmentRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public class ConfirmBookingActivityPresenter extends BasePresenter {
    private ConfirmBookingActivity activity;

    private AppointmentRepository appointmentRepository;
    private Interactor interactor;
    private DTOAppointment appointment;

    public ConfirmBookingActivityPresenter(ConfirmBookingActivity activity){
        super(activity);
        this.activity = activity;
        init();
    }

    public void init(){
        appointmentRepository = new AppointmentRepositoryImpl(this.activity);
        interactor = new Interactor();
    }
    
    public void setCurrentAppointment(DTOAppointment appointment) {
        this.appointment = appointment;
    }

    public void confirmAppointment(String verificationCode) {
        this.activity.showProgress();
        if (this.appointment.getVerificationCode().equals(verificationCode)) {
            interactor.execute(
                () -> appointmentRepository.confirmAppointment(this.appointment.getAppointmentId()),
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

    public void sendEmailNotifyBooking() {
        if (!this.appointment.isEmailSent()) {
            interactor.execute(
                    () -> appointmentRepository.sendEmailNotifyBooking(this.appointment.getAppointmentId()),
                    _success -> {
                        if (_success == RepositorySimpleStatus.SUCCESS)
                            Log.i(this.getClass().getName(), "Send email to notify booking successfully");
                        else
                            Log.i(this.getClass().getName(), "Send email to notify booking has already been sent");
                    },
                    error -> Log.e(this.getClass().getName(), "Send email to notify booking fail")
            );
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        interactor.dispose();
    }
}
