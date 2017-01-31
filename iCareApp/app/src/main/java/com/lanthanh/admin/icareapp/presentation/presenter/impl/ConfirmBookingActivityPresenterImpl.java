package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateAppointmentInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateAppointmentInteractorImpl;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.presenter.ConfirmBookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.ConfirmBookingActivity;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.List;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public class ConfirmBookingActivityPresenterImpl extends AbstractPresenter implements ConfirmBookingActivityPresenter,
             UpdateAppointmentInteractor.Callback {
    public static final String TAG = ConfirmBookingActivityPresenterImpl.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private AppointmentManager appointmentManager;
    private CustomerManager customerManager;
    private ConfirmBookingActivityPresenter.View mView;
    private ModelUser mUser;

    public ConfirmBookingActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view, CustomerManager customerManager, AppointmentManager appointmentManager){
        super(executor, mainThread);
        mView = view;
        this.sharedPreferences = sharedPreferences;
        this.appointmentManager = appointmentManager;
        this.customerManager = customerManager;
        init();
    }

    public void init(){
        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
    }

    @Override
    public void updateAppointment(String verificationCode) {
        UpdateAppointmentInteractor updateAppointmentInteractor = new UpdateAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager, mUser.getID(), verificationCode);
        updateAppointmentInteractor.execute();
    }

    @Override
    public void onUpdateAppointmentFail() {
        try {
            mView.showError(mView.getStringResource(R.string.wrong_code));
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onUpdateAppointmentSuccess(String verificationCode) {
        try {
            //Get local appointment list
            List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences);
            if (appointmentsList == null) {
                onError("No appointment found while in ConfirmActivity");
                return;
            }

            //Update local appointment that have the same verification code
            for (int i = 0; i < appointmentsList.size(); i++) {
                DTOAppointment appointment = appointmentsList.get(i);
                if (appointment.getVerficationCode().equals(verificationCode)) {
                    appointment.setStatus(true);
                    break;
                }
            }
            //Put appointment list to shared pref
            appointmentManager.saveLocalAppointmentsToPref(sharedPreferences, appointmentsList);
            //Navigate to booking details
            mView.navigateToMainActivity(ConfirmBookingActivity.CONFIRMED);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void resume() {

    }
}
