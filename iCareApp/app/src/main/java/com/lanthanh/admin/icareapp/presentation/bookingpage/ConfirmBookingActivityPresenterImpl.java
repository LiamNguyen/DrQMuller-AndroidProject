package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.content.SharedPreferences;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.presenter.ConfirmBookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.List;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public class ConfirmBookingActivityPresenterImpl{
    public static final String TAG = ConfirmBookingActivityPresenterImpl.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private ConfirmBookingActivityPresenter.View mView;
    private ModelUser mUser;

    public ConfirmBookingActivityPresenterImpl(){
        //mView = view;
        this.sharedPreferences = sharedPreferences;
        //init();
    }

//    public void init(){
//        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
//    }
//
//    @Override
//    public void updateAppointment(String verificationCode) {
//        mView.showProgress();
//        int appointmentId = 0;
//        //Get local appointment list
//        List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
//
//        if (appointmentsList == null) {
//            onError("No appointment found while in ConfirmActivity");
//            return;
//        }
//
//        //Update local appointment that have the same verification code
//        for (int i = 0; i < appointmentsList.size(); i++) {
//            DTOAppointment appointment = appointmentsList.get(i);
//            if (appointment.getVerficationCode().equals(verificationCode)) {
//                appointmentId = appointment.getAppointmentId();
//                break;
//            }
//        }
//
//        if (appointmentId == 0) {
//            mView.hideProgress();
//            mView.showError(mView.getStringResource(R.string.wrong_code));
//        }else {
////            UpdateAppointmentInteractor updateAppointmentInteractor = new UpdateAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointmentId);
////            updateAppointmentInteractor.execute();
//        }
//    }
//
//    public void onUpdateAppointmentFail() {
//        try {
//            mView.hideProgress();
//            Log.e(TAG, "Update appointment fail");
//        }catch (Exception e){
//            Log.w(TAG, e.toString());
//        }
//    }
//
//    public void onUpdateAppointmentSuccess(int appointmentId) {
//        try {
//            mView.hideProgress();
//            //Get local appointment list
//            List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
//
//            if (appointmentsList == null) {
//                onError("No appointment found while in ConfirmActivity");
//                return;
//            }
//
//            //Update local appointment that have the same verification code
//            for (int i = 0; i < appointmentsList.size(); i++) {
//                DTOAppointment appointment = appointmentsList.get(i);
//                if (appointment.getAppointmentId() == appointmentId) {
//                    appointment.setStatus(true);
//                    break;
//                }
//            }
//            //Put appointment list to shared pref
//            appointmentManager.saveLocalAppointmentsToPref(sharedPreferences, appointmentsList, mUser.getID());
//            //Navigate to booking details
//            mView.navigateToMainActivity(ConfirmBookingActivity.CONFIRMED);
//        }catch (Exception e){
//            Log.w(TAG, e.toString());
//        }
//    }
//
//    @Override
//    public void resume() {
//
//    }
}
