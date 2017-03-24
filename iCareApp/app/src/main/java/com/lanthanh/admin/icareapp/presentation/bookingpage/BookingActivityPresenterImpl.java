package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.AppointmentRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class BookingActivityPresenterImpl extends BasePresenter{
    public static final String TAG = BookingActivityPresenterImpl.class.getSimpleName();
    private BookingActivity activity;
    private Calendar startDate, expireDate;

    //Fragment
    private BookingSelectFragment bookingSelectFragment;
    private BookingSelectDateFragment bookingSelectDateFragment;
    private BookingBookFragment bookingBookFragment;

    private AppointmentRepository appointmentRepository;
    private Interactor interactor;

    public BookingActivityPresenterImpl(BookingActivity activity) {
        this.activity = activity;
        init();
    }

    public void init(){
        //Fragment init
        bookingBookFragment = new BookingBookFragment();
        bookingSelectFragment = new BookingSelectFragment();
        bookingSelectDateFragment = new BookingSelectDateFragment();
        appointmentRepository = new AppointmentRepositoryImpl();
        interactor = new Interactor();
    }

    @Override
    public void resume() {

    }

    public void navigateFragment(Class<? extends Fragment> fragmentClass) {
        if (fragmentClass == BookingSelectFragment.class)
            showFragment(bookingSelectFragment);
        else if (fragmentClass == BookingSelectFragment.class)
            showFragment(bookingSelectDateFragment);
        else if (fragmentClass == BookingBookFragment.class)
            showFragment(bookingBookFragment);
    }

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

    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    public void showFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        //Hide all current visible fragment
        hideFragments(fragmentTransaction, getVisibleFragments());

        if (!f.isAdded()){
            fragmentTransaction.add(R.id.wel_fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }
    
    public void addCartItem(){
//        int totalItems = appointment.getAppointmentScheduleList().size();
//        String latest = appointment.getAppointmentScheduleList().get(totalItems - 1).toString();
        //this.activity.onAddCartItem(latest);
    }
    
    public void removeCartItem(String item)
    {
        //Set responsive color for item being removed
        this.activity.onRemoveCartItemColor(false);
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

    
    public DTOAppointmentSchedule getSpecificSchedule(String item) {
//        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = appointment.getAppointmentScheduleList();
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
                this.activity.onRemoveCartItem(dtoAppointmentSchedule.toString());
                this.activity.onRemoveCartItemColor(true);
                bookingBookFragment.autoExpandGroup(0);
                //Update DTO
//                appointment.getAppointmentScheduleList().remove(dtoAppointmentSchedule);
            }
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
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


    public void emptyCart() {
//        if (!appointment.isMachineFilled())
//            return;
        this.activity.onEmptyCart();
//        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = appointment.getAppointmentScheduleList();
        DTOAppointmentSchedule[] array = new DTOAppointmentSchedule[dtoAppointmentScheduleList.size()];
        for (int i = 0; i < dtoAppointmentScheduleList.size(); i++){
            array[i] = dtoAppointmentScheduleList.get(i);
        }
        //Remove on DB
//        RemoveTemporaryBookingInteractor removeTemporaryBookingInteractor =
//                new RemoveTemporaryBookingInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointment.getLocationId(), appointment.getMachineId(), array);
//        removeTemporaryBookingInteractor.execute();
    }


    public DTOAppointment getDTOAppointment() {
//        return appointment;
    }

    //TODO remember to impl validate appointment

    public void insertAppointment() {
        this.activity.showProgress();
//        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
//        appointment.setCustomer(mUser);
//        do{
//            appointment.generateVerficationCode();
//        }while (checkVerificationCodeExistence(appointment.getVerficationCode()));
//        if (appointment.getStartDate() == null)
//            appointment.setStartDate(ConverterForDisplay.convertStringToDate("11-11-1111"));
//        InsertAppointmentInteractor insertAppointmentInteractor = new InsertAppointmentInteractorImpl(mExecutor, mMainThread, this, appointmentManager, appointment);
//        insertAppointmentInteractor.execute();
    }

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
            this.activity.hideProgress();
            onError("Insert appointment fail");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    public void onInsertAppointmentSuccess(int appointmentId) {
        try {
            this.activity.hideProgress();
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
            this.activity.navigateActivity(ConfirmBookingActivity.class);
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

    public void onBackPressed() {
        if (bookingSelectFragment.isVisible())
            navigateActivity(MainActivity.class);
        else if (bookingSelectDateFragment.isVisible())
            navigateFragment(BookingSelectFragment.class);
        else if (bookingBookFragment.isVisible())
            navigateFragment(BookingSelectDateFragment.class);
    }

    @Override
    public void destroy() {
        emptyCart();
    }

    public void getCountries(){
        interactor.execute(
            () -> appointmentRepository.getCountries(),
            success -> this.activity.getProvider().setCountries(success),
            error -> {}
        );
    }

    public void getCitiesByCountryId(DTOCountry country){
        this.activity.getProvider().getCurrentAppointment().setCountry(country);
        interactor.execute(
            () -> appointmentRepository.getCitiesByCountryId(country.getCountryId()),
            success -> this.activity.getProvider().setCities(success),
            error -> {}
        );
    }

    public void getDistrictsByCityId(DTOCity city){
        this.activity.getProvider().getCurrentAppointment().setCity(city);
        interactor.execute(
            () -> appointmentRepository.getDistrictsByCityId(city.getCityId()),
            success -> this.activity.getProvider().setDistricts(success),
            error -> {}
        );
    }

    public void getLocationsByDistrictId(DTODistrict district){
        this.activity.getProvider().getCurrentAppointment().setDistrict(district);
        interactor.execute(
            () -> appointmentRepository.getLocationsByDistrictId(district.getDistrictId()),
            success -> {
                this.activity.getProvider().setLocations(success);
            },
            error -> {}
        );
    }

    public void getVouchers() {
        interactor.execute(
            () -> appointmentRepository.getVouchers(),
            success -> {
                this.activity.getProvider().setVouchers(success);
            },
            error -> {}
        );
    }

    public void getTypes() {
        interactor.execute(
            () -> appointmentRepository.getTypes(),
            success -> {
                this.activity.getProvider().setTypes(success);
            },
            error -> {}
        );
    }

    public void getMachinesByLocationId(int locationId) {
        interactor.execute(
            () -> appointmentRepository.getMachinesByLocationId(locationId),
            success -> {
                this.activity.getProvider().setMachines(success);
            },
            error -> {}
        );
    }

    public void getWeekDays() {
        interactor.execute(
            () -> appointmentRepository.getWeekDays(),
            success -> {
                this.activity.getProvider().setWeekDays(success);
            },
            error -> {}
        );
    }

    public void getAllTime() {
        interactor.execute(
            () -> appointmentRepository.getAllTime(),
            success -> {
                this.activity.getProvider().setAllTime(success);
                getEcoTime();
            },
            error -> {}
        );
    }

    public void getEcoTime() {
        interactor.execute(
            () -> appointmentRepository.getEcoTime(),
            success -> {
                this.activity.getProvider().setEcoTime(success);
            },
            error -> {}
        );
    }

    public void getSelectedTime(int dayId, int locationId, int machineId) {
        interactor.execute(
            () -> appointmentRepository.getSelectedTime(dayId, locationId, machineId),
            success -> this.activity.getProvider().setSelectedTime(success),
            error -> {}
        );
    }

    /*========================== DATE ==========================*/
    @Override
    public void onStartDatePickerClick() {
        mView.showStartDatePicker(Calendar.getInstance());
    }

    @Override
    public void onExpireDatePickerClick() {
        if (this.startDate == null)
            mView.showExpireDatePicker(Calendar.getInstance());
        else {
            Calendar minDate = Calendar.getInstance();
            minDate.setTime(this.startDate.getTime());
            minDate.add(Calendar.DATE, 1);
            mView.showExpireDatePicker(minDate);
        }
    }

    //Add start date when start date is selected from View
    @Override
    public void onStartDateSet(Calendar startDate) {
        if (dtoAppointment.getVoucherId() == 1){
            if (!ecoBookingDayCheck(startDate)){
                mView.showError(mView.getStringResource(R.string.booking_error_eco_date));
                return;
            }
        }
        //Because we only care about the date, set time (hour, minute, second, millisecond) to 0
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        //Get current date and do the same
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        /*Check new start date with current date
         *If new start date is before the current date, show error to user and return
         */
        if (startDate.compareTo(currentDate) >= 0){
            this.startDate = startDate;
        }else{
            mView.showError("Ngày được chọn không phù hợp");
            return;
        }

        /*If select start date successfully, check new start date with old expire date
         *If new start date is after old expire date, reset expire date
         */
        if (this.expireDate != null) {
            if (this.startDate.compareTo(this.expireDate) >= 0) {
                this.expireDate = null;
                mView.displayExpireDate(null);
            }
        }

        dtoAppointment.setStartDate(this.startDate.getTime());
        String date = ConverterForDisplay.convertDateToDisplay(this.startDate.getTime());
        mView.displayStartDate(date);
        mView.enableExpireDate();
    }

    //Add expire date when expire date is selected from View
    @Override
    public void onExpireDateSet(Calendar expireDate) {
        if (dtoAppointment.getVoucherId() == 1){
            if (!ecoBookingDayCheck(expireDate)){
                mView.showError(mView.getStringResource(R.string.booking_error_eco_date));
                return;
            }
        }

        //Because we only care about the date, set time (hour, minute, second, millisecond) to 0
        expireDate.set(Calendar.HOUR_OF_DAY, 0);
        expireDate.set(Calendar.MINUTE, 0);
        expireDate.set(Calendar.SECOND, 0);
        expireDate.set(Calendar.MILLISECOND, 0);

        /*Check new expire date with current start date
         *If start date is null (type = Tu Do), check with current date
         *If new expire date is before the current start date, show error to user and return
         */
        if (this.startDate == null){
            //Get current date and set time (hour, minute, second, millisecond) to 0
            Calendar currentDate = Calendar.getInstance();
            currentDate.set(Calendar.HOUR_OF_DAY, 0);
            currentDate.set(Calendar.MINUTE, 0);
            currentDate.set(Calendar.SECOND, 0);
            currentDate.set(Calendar.MILLISECOND, 0);
            if (expireDate.compareTo(currentDate) >= 0) {
                this.expireDate = expireDate;
            } else {
                mView.showError("Ngày được chọn không phù hợp");
                return;
            }
        }else {
            if (expireDate.compareTo(this.startDate) > 0) {
                this.expireDate = expireDate;
            } else {
                mView.showError("Ngày được chọn không phù hợp");
                return;
            }
        }

        dtoAppointment.setExpireDate(this.expireDate.getTime());
        String date = ConverterForDisplay.convertDateToDisplay(this.expireDate.getTime());
        mView.displayExpireDate(date);
    }

    @Override
    public void resetExpireDate() {
        this.startDate = null;
        dtoAppointment.setStartDate(null);
    }

    @Override
    public void resetStartDate() {
        this.expireDate = null;
        dtoAppointment.setExpireDate(null);
    }

    @Override
    public boolean ecoBookingDayCheck(Calendar c) {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return false;
        else
            return true;
    }
}
