package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.AppointmentRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.exceptions.UseCaseException;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.utils.ConverterUtils;
import com.lanthanh.admin.icareapp.utils.Function;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class BookingActivityPresenter extends BasePresenter{
    private BookingActivity activity;
    private Calendar startDate, expireDate;
    private DTOMachine currentMachine;
    private DTOAppointment currentAppointment;
    private int numberOfRequiredContents;

    //Fragment
    private BookingSelectFragment bookingSelectFragment;
    private BookingSelectDateFragment bookingSelectDateFragment;
    private BookingBookFragment bookingBookFragment;

    private AppointmentRepository appointmentRepository;
    private Interactor interactor;

    public BookingActivityPresenter(BookingActivity activity) {
        super(activity);
        this.activity = activity;
        this.numberOfRequiredContents = 0;
        init();
    }

    public void init(){
        //Fragment init
        bookingBookFragment = new BookingBookFragment();
        bookingSelectFragment = new BookingSelectFragment();
        bookingSelectDateFragment = new BookingSelectDateFragment();
        appointmentRepository = new AppointmentRepositoryImpl(this.activity);
        interactor = new Interactor();
        currentAppointment = new DTOAppointment();
    }

    @Override
    public void destroy() {
        //interactor.dispose(); //TODO check this one
    }

    public void refreshAfterLosingNetwork() {
        if (bookingSelectFragment.isVisible())
            bookingSelectFragment.refreshViews();
    }

    public void navigateFragment(Class<? extends Fragment> fragmentClass) {
        this.activity.hideProgress();
        if (fragmentClass == BookingSelectFragment.class)
            showFragment(bookingSelectFragment);
        else if (fragmentClass == BookingSelectDateFragment.class)
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
            fragmentTransaction.add(R.id.fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    public void onBackPressed() {
        this.activity.hideProgress();
        if (bookingSelectFragment.isVisible()) {
            this.activity.finish();
        }
        else if (bookingSelectDateFragment.isVisible())
            navigateFragment(BookingSelectFragment.class);
        else if (bookingBookFragment.isVisible())
            navigateFragment(BookingSelectDateFragment.class);
    }

    public boolean isBasicSelectionValid() {
        return this.currentAppointment.isBasicSelectFilled();
    }

    public boolean isDateSelectionValid() {
        return this.currentAppointment.isDateSelectFilled();
    }

    public boolean isScheduleValid() {
        return this.currentAppointment.isScheduleSelectFilled();
    }

    /*
     * These methods below are used for getting datasource
     */
    public void getCountries(Function.VoidParam<List<DTOCountry>> updateCallback){
        this.activity.showProgress();
        interactor.execute(
            () -> appointmentRepository.getCountries(),
            countries -> {
                checkNumberOfRequiredContentsForBasicSelect();
                updateCallback.apply(countries);
                this.bookingSelectFragment.setDefaultSelectionForCountry();
            },
            error -> this.activity.hideProgress()
        );
    }

    public void onCountrySelected(Function.VoidParam<List<DTOCity>> updateCallback, DTOCountry country){
        currentAppointment.setCountry(country);
        interactor.execute(
            () -> appointmentRepository.getCitiesByCountryId(country.getCountryId()),
            cities -> {
                updateCallback.apply(cities);
                this.bookingSelectFragment.setDefaultSelectionForCity();
            },
            error -> this.activity.hideProgress()
        );
    }

    public void onCitySelected(Function.VoidParam<List<DTODistrict>> updateCallback, DTOCity city){
        currentAppointment.setCity(city);
        interactor.execute(
            () -> appointmentRepository.getDistrictsByCityId(city.getCityId()),
            districts -> {
                updateCallback.apply(districts);
                this.bookingSelectFragment.setDefaultSelectionForDistrict();
            },
            error -> this.activity.hideProgress()
        );
    }

    public void onDistrictSelected(Function.VoidParam<List<DTOLocation>> updateCallback, DTODistrict district){
        currentAppointment.setDistrict(district);
        interactor.execute(
            () -> appointmentRepository.getLocationsByDistrictId(district.getDistrictId()),
            locations -> {
                checkNumberOfRequiredContentsForBasicSelect();
                updateCallback.apply(locations);
                this.bookingSelectFragment.enableLocationSelection();
            },
            error -> this.activity.hideProgress()
        );
    }

    public void onLocationSelected(Function.VoidEmpty updateCallback, DTOLocation location) {
        currentAppointment.setLocation(location);
        updateCallback.apply();
    }

    public void onVoucherSelected(Function.VoidEmpty updateCallback, DTOVoucher voucher) {
        currentAppointment.setVoucher(voucher);
        //Reset date on voucher change
        currentAppointment.setStartDate(null);
        currentAppointment.setExpireDate(null);
        //Reset cart on voucher change
        if (currentMachine != null) {
            if (currentAppointment.isScheduleSelectFilled()) {
                emptyCart(() -> this.activity.onEmptyCartItem());
            }
            currentMachine = null;
        }
        updateCallback.apply();
    }

    public void onTypeSelected(DTOType type) {
        currentAppointment.setType(type);
        //Reset date on type change
        currentAppointment.setStartDate(null);
        currentAppointment.setExpireDate(null);
        //Reset cart on type change
        if (currentMachine != null) {
            if (currentAppointment.isScheduleSelectFilled()) {
                emptyCart(() -> this.activity.onEmptyCartItem());
            }
            currentMachine = null;
        }
    }


    public void getVouchers(Function.VoidParam<List<DTOVoucher>> updateCallback) {
        interactor.execute(
            () -> appointmentRepository.getVouchers(),
            vouchers -> {
                checkNumberOfRequiredContentsForBasicSelect();
                updateCallback.apply(vouchers);
            },
            error -> this.activity.hideProgress()
        );
    }

    public void getTypes(Function.VoidParam<List<DTOType>> updateCallback) {
        interactor.execute(
            () -> appointmentRepository.getTypes(),
            types -> {
                checkNumberOfRequiredContentsForBasicSelect();
                updateCallback.apply(types);
            },
            error -> this.activity.hideProgress()
        );
    }

    public void getMachines(Function.VoidParam<List<DTOMachine>> updateCallback) {
        this.activity.showProgress();
        interactor.execute(
            () -> appointmentRepository.getMachinesByLocationId(currentAppointment.getLocation().getLocationId()),
            updateCallback::apply,
            error -> this.activity.showProgress()
        );
    }

    public void getWeekDays(Function.VoidParam<List<DTOWeekDay>> updateCallback) {
        interactor.execute(
            () -> appointmentRepository.getWeekDays(currentAppointment.getVoucher().getVoucherId()),
            success -> {
                if (currentAppointment.getType().getTypeId() == 2) {
                    success = getDayOfWeekForTypeFree(success, currentAppointment.getExpireDate());
                }
                updateCallback.apply(success);
            },
            error -> this.activity.hideProgress()
        );
    }

    public void getTime(Function.VoidParam<List<DTOTime>> updateCallback) {
        interactor.execute(
            () -> appointmentRepository.getTime(currentAppointment.getVoucher().getVoucherId()),
            success -> {
                this.activity.hideProgress();
                updateCallback.apply(success);
            },
            error -> this.activity.hideProgress()
        );
    }

    /*
     * These methods below contain logic for booking's date
     */
    public void onStartDatePickerClick(Function.VoidParam<Calendar> callback) {
        callback.apply(Calendar.getInstance());
    }

    public void onExpireDatePickerClick(Function.VoidParam<Calendar> callback) {
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
    public void onStartDateSet(Calendar startDate, Function.VoidParam<String> success, Function.VoidParam<String> fail) {
        if (currentAppointment.getVoucher().getVoucherId() == 1){
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
            fail.apply(this.activity.getString(R.string.booking_date_invalid));
            return;
        }

        /*If select start date successfully, check new start date with old expire date
         *If new start date is after old expire date, reset expire date
         */
        if (this.expireDate != null) {
            if (this.startDate.compareTo(this.expireDate) >= 0) {
                this.expireDate = null;
                currentAppointment.setExpireDate(null);
                this.bookingSelectDateFragment.resetExpireDateForFixedType();
            }
        }

        currentAppointment.setStartDate(this.startDate.getTime());
        if (currentAppointment.isDateSelectFilled()) {
            bookingSelectDateFragment.enableNextButton(true);
        } else {
            bookingSelectDateFragment.enableNextButton(false);
        }
        String date = ConverterUtils.date.convertDateForDisplay(this.startDate.getTime());
        success.apply(date);
    }

    //Add expire date when expire date is selected from View
    public void onExpireDateSet(Calendar expireDate, Function.VoidParam<String> success, Function.VoidParam<String> fail) {
        if (currentAppointment.getVoucher().getVoucherId() == 1){
            if (!ecoBookingDayCheck(expireDate)){
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
                fail.apply(this.activity.getString(R.string.booking_date_invalid));
                return;
            }
        }else {
            if (expireDate.compareTo(this.startDate) > 0) {
                this.expireDate = expireDate;
            } else {
                fail.apply(this.activity.getString(R.string.booking_date_invalid));
                return;
            }
        }

        currentAppointment.setExpireDate(this.expireDate.getTime());
        if (currentAppointment.isDateSelectFilled()) {
            bookingSelectDateFragment.enableNextButton(true);
        } else {
            bookingSelectDateFragment.enableNextButton(false);
        }
        if (currentAppointment.isScheduleSelectFilled()) {
            emptyCart(() -> this.activity.onEmptyCartItem());
            currentMachine = null;
        }
        currentMachine = null;
        String date = ConverterUtils.date.convertDateForDisplay(this.expireDate.getTime());
        success.apply(date);
    }

    public boolean ecoBookingDayCheck(Calendar c) {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return false;
        else
            return true;
    }

    public void resetPickerView(Function.VoidParam<Integer> resetViews) {
        if (currentAppointment.getStartDate() != null ||
            currentAppointment.getExpireDate() != null)
            return;
        this.startDate = null;
        this.expireDate = null;
        resetViews.apply(currentAppointment.getType().getTypeId());
    }

    /*
     * These methods below are used for booking appointment
     */
    public void onMachineSelected(Function.VoidEmpty callback, DTOMachine machine) {
        currentMachine = machine;
        callback.apply();
    }

    public boolean onDaySelected(Function.VoidEmpty success) {
        if (currentMachine == null){
            this.activity.showToast(this.activity.getString(R.string.machine_alert));
            return true;
        }
        success.apply();
        return false;
    }

    public void resetMachine(Function.VoidEmpty resetView) {
        if (currentMachine == null)
            resetView.apply();
        else {
            if (this.currentAppointment.getType().getTypeId() == 2) {
                this.activity.showProgress();
                bookingBookFragment.expandGroup(0, true);
            }
        }
    }

    public void getAvailableTime(Function.VoidParam<List<DTOTime>> updateCallback, int dayId) {
        this.activity.showProgress();
        this.bookingBookFragment.enableListView(false);
        interactor.execute(
                () -> appointmentRepository.getAvailableTime(
                        dayId,
                        currentAppointment.getLocation().getLocationId(),
                        this.currentMachine.getMachineId(),
                        currentAppointment.getVoucher().getVoucherId()),
                success -> {
                    this.activity.hideProgress();
                    this.bookingBookFragment.enableListView(true);
                    if (currentAppointment.getType().getTypeId() == 2) {
                        Calendar calendarNow = Calendar.getInstance();
                        Calendar bookedDay = Calendar.getInstance();
                        bookedDay.setTime(currentAppointment.getExpireDate());
                        if (bookedDay.get(Calendar.DATE) == calendarNow.get(Calendar.DATE)) {
                            int now = calendarNow.get(Calendar.HOUR_OF_DAY) * 60 + calendarNow.get(Calendar.MINUTE);
                            for (DTOTime time : new ArrayList<>(success)) {
                                if (ConverterUtils.date.convertToHours(time.getTime()) < now)
                                    success.remove(time);
                            }
                        }
                    }
                    //Notify user if there is no appropriate schedule left today
                    if (success.size() <= 0) {
                        this.activity.showToast(this.activity.getString(R.string.out_of_schedule));
                        this.bookingBookFragment.collapseAllGroups();
                    }

                    updateCallback.apply(success);
                },
                error -> this.activity.hideProgress()
        );
    }

    public void onTimeSelected(Function.VoidEmpty unlockViews, Function.VoidEmpty refreshTime, DTOWeekDay weekDay, DTOTime time) {
        //Maximum 3 schedules for 1 appointment
        if (currentAppointment.getAppointmentScheduleList().size() == 3) {
            this.activity.showToast(this.activity.getString(R.string.max_item));
            unlockViews.apply();
            return;
        }
        //Maximum 1 schedule for 1 day
        for (DTOAppointmentSchedule appointment: currentAppointment.getAppointmentScheduleList()) {
            if (appointment.getBookedDay().getDayId() == weekDay.getDayId()) {
                this.activity.showToast(this.activity.getString(R.string.selected_day));
                unlockViews.apply();
                return;
            }
        }
        //Else allow to book
        DTOAppointmentSchedule appointmentSchedule = new DTOAppointmentSchedule();
        appointmentSchedule.setBookedMachine(currentMachine);
        appointmentSchedule.setBookedDay(weekDay);
        appointmentSchedule.setBookedTime(time);
        this.activity.showProgress();
        interactor.execute(
            () -> appointmentRepository.bookTime(currentAppointment.getLocation().getLocationId(), appointmentSchedule),
            success -> {
                currentAppointment.getAppointmentScheduleList().add(appointmentSchedule);
                String newItem = appointmentSchedule.toString();
                this.activity.onAddCartItem(newItem);
                refreshTime.apply();
                if (currentAppointment.isScheduleSelectFilled())
                    bookingBookFragment.enableFinishButton(true);
                else
                    bookingBookFragment.enableFinishButton(false);
                unlockViews.apply();
            },
            error -> {
                this.activity.hideProgress();
                if (error instanceof UseCaseException) {
                    switch (((UseCaseException) error).getStatus()) {
                        case TIME_HAS_BEEN_BOOKED:
                            this.activity.showToast(this.activity.getString(R.string.already_booked));
                            break;
                        case INVALID_TOKEN:
                            this.activity.showToast(this.activity.getString(R.string.invalid_token));
                            break;
                    }
                }
                unlockViews.apply();
            }
        );
    }

    public void releaseTime(String item, Function.VoidParam<String> removeItem) {
        this.activity.showProgress();
        //Set responsive color for item being removed
        this.activity.onRemoveCartItemColor(false);
        //Get appointment schedule
        DTOAppointmentSchedule dtoAppointmentSchedule = getSpecificSchedule(item);
        if (dtoAppointmentSchedule != null){
            //Remove on DB
            interactor.execute(
                    () -> appointmentRepository.releaseTime(currentAppointment.getLocation().getLocationId(), Arrays.asList(dtoAppointmentSchedule)),
                    success -> {
                        this.activity.hideProgress();
                        removeItem.apply(item);
                        if (bookingBookFragment.isVisible())
                            bookingBookFragment.expandGroup(0, true);
                        //Update DTO
                        currentAppointment.getAppointmentScheduleList().remove(dtoAppointmentSchedule);
                        if (currentAppointment.isScheduleSelectFilled())
                            bookingBookFragment.enableFinishButton(true);
                        else
                            bookingBookFragment.enableFinishButton(false);
                    },
                    error -> this.activity.hideProgress()
            );
        }else
            Log.e(this.getClass().getName(), "Item to be removed doesn't exist");
    }


    public DTOAppointmentSchedule getSpecificSchedule(String item) {
        List<DTOAppointmentSchedule> dtoAppointmentScheduleList = currentAppointment.getAppointmentScheduleList();
        for (DTOAppointmentSchedule dtoAppointmentSchedule : dtoAppointmentScheduleList){
            if (dtoAppointmentSchedule.toString().equals(item))
                return dtoAppointmentSchedule;
        }
        return null;
    }

    public void emptyCart(Function.VoidEmpty clearCart) {
        if (currentAppointment.getAppointmentScheduleList().size() > 0) {
            this.activity.showProgress();
            //Remove on DB
            interactor.execute(
                () -> appointmentRepository.releaseTime(currentAppointment.getLocation().getLocationId(),
                        currentAppointment.getAppointmentScheduleList()),
                success -> {
                    this.activity.hideProgress();
                    currentAppointment.getAppointmentScheduleList().clear();
                    clearCart.apply();
                    if (bookingBookFragment.isVisible())
                        bookingBookFragment.expandGroup(0, true);
                },
                error -> this.activity.hideProgress()
            );
        }
    }

    public void validateAppointment() {
        interactor.execute(
            () -> appointmentRepository.validateAppointment(),
            success -> {
                if (success == RepositorySimpleStatus.SUCCESS)
                    Log.i(this.getClass().getName(), "Validate appointment successfully");
            },
            error -> {}
        );
    }

    public void createAppointment() {
        if (this.currentAppointment.getAppointmentScheduleList().size() <= 0){
            this.activity.showToast(this.activity.getString(R.string.min_item));
        } else {
            this.activity.showProgress();
            currentAppointment.setVerificationCode(NetworkUtils.generateVerificationCode());
            if (currentAppointment.getStartDate() == null) {
                currentAppointment.setStartDate(ConverterUtils.date.convertStringToDate("11-11-1111"));
            }
            interactor.execute(
                    () -> appointmentRepository.createAppointment(currentAppointment),
                    success -> {
                        this.activity.hideProgress();
                        if (success == RepositorySimpleStatus.SUCCESS) {
                            navigateActivity(MainActivity.class);
                        }
                    },
                    error -> this.activity.hideProgress()
            );
        }
    }

    public List<DTOWeekDay> getDayOfWeekForTypeFree(List<DTOWeekDay> weekDaysList, Date doDate) {
        List<DTOWeekDay> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(doDate);
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:
                result.add(weekDaysList.get(0));
                break;
            case Calendar.TUESDAY:
                result.add(weekDaysList.get(1));
                break;
            case Calendar.WEDNESDAY:
                result.add(weekDaysList.get(2));
                break;
            case Calendar.THURSDAY:
                result.add(weekDaysList.get(3));
                break;
            case Calendar.FRIDAY:
                result.add(weekDaysList.get(4));
                break;
            case Calendar.SATURDAY:
                result.add(weekDaysList.get(5));
                break;
            case Calendar.SUNDAY:
                result.add(weekDaysList.get(6));
                break;
        }
        return result;
    }

    public void checkNumberOfRequiredContentsForBasicSelect() {
        this.numberOfRequiredContents++;
        if (this.numberOfRequiredContents >= 4) {
            this.activity.hideProgress();
        }
    }
}
