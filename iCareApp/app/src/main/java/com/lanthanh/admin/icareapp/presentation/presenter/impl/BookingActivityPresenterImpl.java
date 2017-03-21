package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.BookingActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.ConfirmBookingActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.booking.BookingBookFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.booking.BookingSelectDateFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.booking.BookingSelectFragment;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class BookingActivityPresenterImpl extends AbstractPresenter implements BookingActivityPresenter{
    public static final String TAG = BookingActivityPresenterImpl.class.getSimpleName();
    private BookingActivityPresenter.View mView;
    private ModelUser mUser;
    private FragmentManager fragmentManager;
    private AppointmentManager appointmentManager;
    private CustomerManager customerManager;
    private SendEmailManager sendEmailManager;
    private SharedPreferences sharedPreferences;
    private DTOAppointment appointment;

    //Fragment
    private BookingSelectFragment bookingSelectFragment;
    private BookingSelectDateFragment bookingSelectDateFragment;
    private BookingBookFragment bookingBookFragment;

    public BookingActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view,
                                        FragmentManager fragmentManager, AppointmentManager appointmentManager,
                                        SendEmailManager sendEmailManager, CustomerManager customerManager) {
        super(executor, mainThread);

        //Init local variable
        init();

        //Init view
        mView = view;

        //Init manager
        this.fragmentManager = fragmentManager;
        this.appointmentManager = appointmentManager;
        this.sendEmailManager = sendEmailManager;
        this.customerManager = customerManager;

        //Init local storage
        this.sharedPreferences = sharedPreferences;
    }

    public void init(){
        appointment = new DTOAppointment();
        //Fragment init
        bookingBookFragment = new BookingBookFragment();
        bookingSelectFragment = new BookingSelectFragment();
        bookingSelectDateFragment = new BookingSelectDateFragment();
    }

    @Override
    public void resume() {

    }

    @Override
    public void navigateTab(int selected) {
        if (selected == BookingActivity.FIRST_SELECT)
            mView.showFragment(fragmentManager, bookingSelectFragment, getVisibleFragments());
        else if (selected == BookingActivity.SECOND_SELECT)
            mView.showFragment(fragmentManager, bookingSelectDateFragment, getVisibleFragments());
        else if (selected == BookingActivity.BOOK)
            mView.showFragment(fragmentManager, bookingBookFragment, getVisibleFragments());
    }

    @Override
    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result IF VISIBLE
        if (bookingSelectFragment.isVisible()) {
            result.add(bookingSelectFragment);
        }
        if (bookingSelectDateFragment.isVisible()) {
            result.add(bookingSelectDateFragment);
        }
        if (bookingBookFragment.isVisible()) {
            result.add(bookingBookFragment);
        }

        return result;
    }

    @Override
    public void addCartItem(){
        int totalItems = appointment.getAppointmentScheduleList().size();
        String latest = appointment.getAppointmentScheduleList().get(totalItems - 1).toString();
        mView.onAddCartItem(latest);
    }

    @Override
    public void removeCartItem(String item)
    {
        //Set responsive color for item being removed
        mView.onRemoveCartItemColor(false);
        //Get appointment schedule
        DTOAppointmentSchedule dtoAppointmentSchedule = getSpecificSchedule(item);
        if (dtoAppointmentSchedule != null){
//            //Remove on DB
//            RemoveTemporaryBookingInteractor removeTemporaryBookingInteractor =
//                    new RemoveTemporaryBookingInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointment.getLocationId(), dtoAppointmentSchedule.getMachineId(), dtoAppointmentSchedule);
//            removeTemporaryBookingInteractor.execute();
        }else
            onError("Item to be removed doesn't exist");
    }
    @Override
    public DTOAppointmentSchedule getSpecificSchedule(String item) {
        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = appointment.getAppointmentScheduleList();
        for (DTOAppointmentSchedule dtoAppointmentSchedule : dtoAppointmentScheduleList){
            if (dtoAppointmentSchedule.toString().equals(item))
                return dtoAppointmentSchedule;
        }
        return null;
    }

    public void onRemoveTempBookingSuccess(List<DTOAppointmentSchedule> list) {
        try {
            for (DTOAppointmentSchedule dtoAppointmentSchedule: list) {
                //Update UI
                mView.onRemoveCartItem(dtoAppointmentSchedule.toString());
                mView.onRemoveCartItemColor(true);
                bookingBookFragment.autoExpandGroup(0);
                //Update DTO
                appointment.getAppointmentScheduleList().remove(dtoAppointmentSchedule);
            }
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    public void onRemoveTempBookingFail() {
        try {
            onError("REMOVE TEMP BOOK FAIL");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void emptyCart() {
        if (!appointment.isMachineFilled())
            return;
        mView.onEmptyCart();
        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = appointment.getAppointmentScheduleList();
        DTOAppointmentSchedule[] array = new DTOAppointmentSchedule[dtoAppointmentScheduleList.size()];
        for (int i = 0; i < dtoAppointmentScheduleList.size(); i++){
            array[i] = dtoAppointmentScheduleList.get(i);
        }
        //Remove on DB
//        RemoveTemporaryBookingInteractor removeTemporaryBookingInteractor =
//                new RemoveTemporaryBookingInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointment.getLocationId(), appointment.getMachineId(), array);
//        removeTemporaryBookingInteractor.execute();
    }

    @Override
    public DTOAppointment getDTOAppointment() {
        return appointment;
    }

    @Override
    public void validateAppointment() {
//        UpdateValidateAppointmentInteractor updateValidateAppointmentInteractor = new UpdateValidateAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager);
//        updateValidateAppointmentInteractor.execute();
    }

    public void onValidateFail() {
        try {
            onError("Validate fail");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    public void onValidateSuccess() {
        try {
            System.out.println("Validate success");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void insertAppointment() {
        mView.showProgress();
        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
        appointment.setCustomer(mUser);
        do{
            appointment.generateVerficationCode();
        }while (checkVerificationCodeExistence(appointment.getVerficationCode()));
        if (appointment.getStartDate() == null)
            appointment.setStartDate(ConverterForDisplay.convertStringToDate("11-11-1111"));
//        InsertAppointmentInteractor insertAppointmentInteractor = new InsertAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointment);
//        insertAppointmentInteractor.execute();
    }

    @Override
    public boolean checkVerificationCodeExistence(String verificationCode) {
        //Get appointment from local shared pref
        List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
        if (appointmentsList == null)
            return false;
        else{
            for (DTOAppointment appointment: appointmentsList){
                if (appointment.getVerficationCode().equals(verificationCode))
                    return true;
            }
        }
        return false;
    }

    public void onInsertAppointmentFail() {
        try {
            mView.hideProgress();
            onError("Insert appointment fail");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    public void onInsertAppointmentSuccess(int appointmentId) {
        try {
            mView.hideProgress();
            //Send mail to staff
//            SendEmailNotifyBookingInteractor sendEmailNotifyBookingInteractor = new SendEmailNotifyBookingInteractorImpl(mExecutor, mMainThread, this, sendEmailManager, appointment);
//            sendEmailNotifyBookingInteractor.execute();

            //Get appointment from local shared pref
            List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
            if (appointmentsList == null)
                appointmentsList = new ArrayList<>();
            //Add appointment to a list of appointments
            appointment.setAppointmentId(appointmentId);
            appointmentsList.add(appointment);
            //Put to shared pref
            appointmentManager.saveLocalAppointmentsToPref(sharedPreferences, appointmentsList, mUser.getID());
            //reset appointment
            appointment = new DTOAppointment();
            //empty cart
            emptyCart();
            //move to confirm activity
            mView.navigateActivity(ConfirmBookingActivity.class);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    public void onEmailNotifyBookingNotSent() {
        try{
            System.out.println("Notify email sent fail");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    public void onEmailNotifyBookingSent() {
        try {
            System.out.println("Notify email sent success");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        if (bookingSelectFragment.isVisible())
            mView.navigateActivity(MainActivity.class);
        else if (bookingSelectDateFragment.isVisible())
            navigateTab(BookingActivity.FIRST_SELECT);
        else if (bookingBookFragment.isVisible())
            navigateTab(BookingActivity.SECOND_SELECT);
    }

    @Override
    public void destroy() {
        emptyCart();
    }
}
