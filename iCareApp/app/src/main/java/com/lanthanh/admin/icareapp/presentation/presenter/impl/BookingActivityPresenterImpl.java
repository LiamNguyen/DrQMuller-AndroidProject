package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.InsertAppointmentInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.RemoveTemporaryBookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.SendEmailNotifyBookingInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateValidateAppointmentInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.InsertAppointmentInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.RemoveTemporaryBookingInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.SendEmailNotifyBookingInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateValidateAppointmentInteractorImpl;
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

public class BookingActivityPresenterImpl extends AbstractPresenter implements BookingActivityPresenter,
        InsertAppointmentInteractor.Callback, RemoveTemporaryBookingInteractor.Callback,
        SendEmailNotifyBookingInteractor.Callback, UpdateValidateAppointmentInteractor.Callback{
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
    public void removeCartItem(String item){
        //Set responsive color for item being removed
        mView.onRemoveCartItemColor(false);
        //Get appointment schedule
        DTOAppointmentSchedule dtoAppointmentSchedule = getSpecificSchedule(item);
        if (dtoAppointmentSchedule != null){
            //Remove on DB
            RemoveTemporaryBookingInteractor removeTemporaryBookingInteractor =
                    new RemoveTemporaryBookingInteractorImpl(mExecutor, mMainThread, this, appointmentManager, dtoAppointmentSchedule);
            removeTemporaryBookingInteractor.execute();
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

    @Override
    public void onRemoveTempBookingSuccess(DTOAppointmentSchedule dtoAppointmentSchedule) {
        //Update UI
        mView.onRemoveCartItem(dtoAppointmentSchedule.toString());
        mView.onRemoveCartItemColor(true);
        //Update DTO
        appointment.getAppointmentScheduleList().remove(dtoAppointmentSchedule);
    }

    @Override
    public void onRemoveTempBookingFail() {
        onError("REMOVE TEMP BOOK FAIL");
    }

    @Override
    public void emptyCart() {
        mView.onEmptyCart();
        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = appointment.getAppointmentScheduleList();
        for (DTOAppointmentSchedule dtoAppointmentSchedule : dtoAppointmentScheduleList){
            //Remove on DB
            RemoveTemporaryBookingInteractor removeTemporaryBookingInteractor =
                    new RemoveTemporaryBookingInteractorImpl(mExecutor, mMainThread, this, appointmentManager, dtoAppointmentSchedule);
            removeTemporaryBookingInteractor.execute();
        }
    }

    @Override
    public DTOAppointment getDTOAppointment() {
        return appointment;
    }

    @Override
    public void validateAppointment() {
        UpdateValidateAppointmentInteractor updateValidateAppointmentInteractor = new UpdateValidateAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager);
        updateValidateAppointmentInteractor.execute();
    }

    @Override
    public void onValidateFail() {
        onError("Validate fail");
    }

    @Override
    public void onValidateSuccess() {
        System.out.println("Validate success");
    }

    @Override
    public void insertAppointment() {
        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
        appointment.setCustomer(mUser);
        appointment.generateVerficationCode();
        if (appointment.getStartDate() == null)
            appointment.setStartDate(ConverterForDisplay.convertStringToDate("11-11-1111"));
        InsertAppointmentInteractor insertAppointmentInteractor = new InsertAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointment);
        insertAppointmentInteractor.execute();
    }

    @Override
    public void onInsertAppointmentFail() {
        onError("Insert appointment fail");
    }

    @Override
    public void onInsertAppointmentSuccess() {
        //Send mail to staff
        SendEmailNotifyBookingInteractor sendEmailNotifyBookingInteractor = new SendEmailNotifyBookingInteractorImpl(mExecutor, mMainThread, this, sendEmailManager, appointment);
        sendEmailNotifyBookingInteractor.execute();

        //Get appointment from local shared pref
        List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences);
        if (appointmentsList == null)
            appointmentsList = new ArrayList<>();
        //Add appointment to a list of appointments
        appointmentsList.add(appointment);
        //Put to shared pref
        appointmentManager.saveLocalAppointmentsToPref(sharedPreferences, appointmentsList);
        //reset appointment
        appointment = new DTOAppointment();
        //empty cart
        emptyCart();
        //move to confirm activity
        mView.navigateActivity(ConfirmBookingActivity.class);
    }

    @Override
    public void onEmailNotifyBookingNotSent() {
        System.out.println("Notify email sent fail");
    }

    @Override
    public void onEmailNotifyBookingSent() {
        System.out.println("Notify email sent success");
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
}
