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
import com.lanthanh.admin.icareapp.presentation.Function;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class BookingActivityPresenterImpl extends BasePresenter{
    public static final String TAG = BookingActivityPresenterImpl.class.getSimpleName();
    private BookingActivity activity;
    private Calendar startDate, expireDate;
    private int typeId, voucherId;

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

    public void releaseTime(String item, Function.Void<String> callback) {
        //Set responsive color for item being removed
        this.activity.onRemoveCartItemColor(false);
        //Get appointment schedule
        DTOAppointmentSchedule dtoAppointmentSchedule = getSpecificSchedule(item);
        if (dtoAppointmentSchedule != null){
            //Remove on DB
            interactor.execute(
                () -> appointmentRepository.releaseTime(this.activity.getProvider().getCurrentAppointment().getLocation().getLocationId(), Arrays.asList(dtoAppointmentSchedule)),
                success -> {
                    callback.apply(item);
                    bookingBookFragment.expandGroup(0, true);
                    //Update DTO
                    this.activity.getProvider().getCurrentAppointment().getAppointmentScheduleList().remove(dtoAppointmentSchedule);
                },
                error -> {}
            );
        }else
            Log.e(this.getClass().getName(), "Item to be removed doesn't exist");
    }

    
    public DTOAppointmentSchedule getSpecificSchedule(String item) {
        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = this.activity.getProvider().getCurrentAppointment().getAppointmentScheduleList();
        for (DTOAppointmentSchedule dtoAppointmentSchedule : dtoAppointmentScheduleList){
            if (dtoAppointmentSchedule.toString().equals(item))
                return dtoAppointmentSchedule;
        }
        return null;
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


    public void emptyCart(Function.VoidParam callback) {
        //Remove on DB
        interactor.execute(
                () -> appointmentRepository.releaseTime(this.activity.getProvider().getCurrentAppointment().getLocation().getLocationId(),
                                this.activity.getProvider().getCurrentAppointment().getAppointmentScheduleList()),
                success -> {
                    callback.apply();
                    bookingBookFragment.expandGroup(0, true);
                    //Update DTO
                    this.activity.getProvider().getCurrentAppointment().setAppointmentScheduleList(null);
                    this.activity.getProvider().getCurrentAppointment().setCurrentSchedule(null);
                },
                error -> {}
        );
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
//        //Get appointment from local shared pref
//        List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
//        if (appointmentsList == null)
//            return false;
//        else{
//            for (DTOAppointment appointment: appointmentsList){
//                if (appointment.getVerficationCode().equals(verificationCode))
//                    return true;
//            }
//        }
        return false;
    }



//    public void onInsertAppointmentSuccess(int appointmentId) {
//        try {
//            this.activity.hideProgress();
//            //Send mail to staff
////            SendEmailNotifyBookingInteractor sendEmailNotifyBookingInteractor = new SendEmailNotifyBookingInteractorImpl(mExecutor, mMainThread, this, sendEmailManager, appointment);
////            sendEmailNotifyBookingInteractor.execute();
//
//            //Get appointment from local shared pref
//            List<DTOAppointment> appointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences, mUser.getID());
//            if (appointmentsList == null)
//                appointmentsList = new ArrayList<>();
//            //Add appointment to a list of appointments
//            appointment.setAppointmentId(appointmentId);
//            appointmentsList.add(appointment);
//            //Put to shared pref
//            appointmentManager.saveLocalAppointmentsToPref(sharedPreferences, appointmentsList, mUser.getID());
//            //reset appointment
//            appointment = new DTOAppointment();
//            //empty cart
//            emptyCart();
//            //move to confirm activity
//            this.activity.navigateActivity(ConfirmBookingActivity.class);
//        }catch (Exception e){
//            Log.w(TAG, e.toString());
//        }
//    }


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

    }

    /*
     * These methods below are used for getting datasource
     */

    public void getCountries(){
        interactor.execute(
            () -> appointmentRepository.getCountries(),
            success -> this.activity.getProvider().setCountries(success),
            error -> {}
        );
    }

    public void getCitiesByCountryId(int countryId){
        interactor.execute(
            () -> appointmentRepository.getCitiesByCountryId(countryId),
            success -> this.activity.getProvider().setCities(success),
            error -> {}
        );
    }

    public void getDistrictsByCityId(int cityId){
        interactor.execute(
            () -> appointmentRepository.getDistrictsByCityId(cityId),
            success -> this.activity.getProvider().setDistricts(success),
            error -> {}
        );
    }

    public void getLocationsByDistrictId(int districtId){
        interactor.execute(
            () -> appointmentRepository.getLocationsByDistrictId(districtId),
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
        this.activity.showProgress();
        interactor.execute(
            () -> appointmentRepository.getSelectedTime(dayId, locationId, machineId),
            success -> this.activity.getProvider().setSelectedTime(success),
            error -> {}
        );
    }

    /*
     * These methods below contain UI logic for BookingBookFragment
     */

    public void onStartDatePickerClick(Function.Void<Calendar> callback) {
        callback.apply(Calendar.getInstance());
    }

    public void onExpireDatePickerClick(Function.Void<Calendar> callback) {
        if (this.startDate == null)
            callback.apply(Calendar.getInstance());
        else {
            Calendar minDate = Calendar.getInstance();
            minDate.setTime(this.startDate.getTime());
            minDate.add(Calendar.DATE, 1);
            callback.apply(minDate);
        }
    }

    //Add start date when start date is selected from View
    public void onStartDateSet(Calendar startDate, Function.Void<String> success, Function.Void<String> fail) {
        if (this.activity.getProvider().getCurrentAppointment().getVoucher().getVoucherId() == 1){
            if (!ecoBookingDayCheck(startDate)){
                fail.apply(this.activity.getString(R.string.booking_error_eco_date));
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
            fail.apply("Ngày được chọn không phù hợp");
            return;
        }

        /*If select start date successfully, check new start date with old expire date
         *If new start date is after old expire date, reset expire date
         */
        if (this.expireDate != null) {
            if (this.startDate.compareTo(this.expireDate) >= 0) {
                this.expireDate = null;
                fail.apply(null);
            }
        }

        this.activity.getProvider().getCurrentAppointment().setStartDate(this.startDate.getTime());
        String date = ConverterForDisplay.convertDateToDisplay(this.startDate.getTime());
        success.apply(date);
    }

    //Add expire date when expire date is selected from View
    public void onExpireDateSet(Calendar expireDate, Function.Void<String> success, Function.Void<String> fail) {
        if (this.activity.getProvider().getCurrentAppointment().getVoucher().getVoucherId() == 1){
            if (!ecoBookingDayCheck(startDate)){
                fail.apply(this.activity.getString(R.string.booking_error_eco_date));
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
                fail.apply("Ngày được chọn không phù hợp");
                return;
            }
        }else {
            if (expireDate.compareTo(this.startDate) > 0) {
                this.expireDate = expireDate;
            } else {
                fail.apply("Ngày được chọn không phù hợp");
                return;
            }
        }

        this.activity.getProvider().getCurrentAppointment().setExpireDate(this.expireDate.getTime());
        String date = ConverterForDisplay.convertDateToDisplay(this.expireDate.getTime());
        success.apply(date);
    }

    public boolean ecoBookingDayCheck(Calendar c) {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return false;
        else
            return true;
    }

    public void setUpDatePickerView(Function.Void<Integer> callback) {
        if (this.activity.getProvider().getCurrentAppointment().getType().getTypeId() == typeId &&
            this.activity.getProvider().getCurrentAppointment().getVoucher().getVoucherId() == voucherId)
            return;
        this.startDate = null;
        this.activity.getProvider().getCurrentAppointment().setStartDate(null);
        this.expireDate = null;
        this.activity.getProvider().getCurrentAppointment().setExpireDate(null);
        callback.apply(this.activity.getProvider().getCurrentAppointment().getType().getTypeId());
    }

    /*
     * These methods below are used for booking appointment
     */
    public void bookTime(DTOWeekDay weekDay, DTOTime time) {
        DTOAppointmentSchedule appointmentSchedule = this.activity.getProvider().getCurrentAppointment().getCurrentSchedule();
        appointmentSchedule.setBookedDay(weekDay);
        appointmentSchedule.setBookedTime(time);
        interactor.execute(
            () -> appointmentRepository.bookTime(this.activity.getProvider().getCurrentAppointment().getLocation().getLocationId(),
                    Arrays.asList(this.activity.getProvider().getCurrentAppointment().getCurrentSchedule())),
            success -> {
                int totalItems = this.activity.getProvider().getCurrentAppointment().getAppointmentScheduleList().size();
                String latest = this.activity.getProvider().getCurrentAppointment().getAppointmentScheduleList().get(totalItems - 1).toString();
                this.activity.onAddCartItem(latest);
                this.activity.getProvider().getCurrentAppointment().getAppointmentScheduleList().add(appointmentSchedule);
            },
            error -> {}
        );
    }
}
