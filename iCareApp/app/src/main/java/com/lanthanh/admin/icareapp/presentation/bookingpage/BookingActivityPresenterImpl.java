package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.AppointmentRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.interactor.InteractorFactory;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointmentSchedule;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class BookingActivityPresenterImpl extends BasePresenter{
    public static final String TAG = BookingActivityPresenterImpl.class.getSimpleName();
    private BookingActivity activity;
    private DTOAppointment appointment;

    //Fragment
    private BookingSelectFragment bookingSelectFragment;
    private BookingSelectDateFragment bookingSelectDateFragment;
    private BookingBookFragment bookingBookFragment;
    private AppointmentRepository appointmentRepository;

    private Interactor<List<DTOCountry>> getCountriesInteractor;
    private Interactor<List<DTOCity>> getCitiesInteractor;

    public BookingActivityPresenterImpl(BookingActivity activity) {
        this.activity = activity;
        init();
    }

    public void init(){
        appointment = new DTOAppointment();
        //Fragment init
        bookingBookFragment = new BookingBookFragment();
        bookingSelectFragment = new BookingSelectFragment();
        bookingSelectDateFragment = new BookingSelectDateFragment();
        appointmentRepository = new AppointmentRepositoryImpl();

        getCountriesInteractor = InteractorFactory.create(() -> appointmentRepository.getCountries());
        getCitiesInteractor = InteractorFactory.create(() -> appointmentRepository.getCitiesByCountryId())
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
        int totalItems = appointment.getAppointmentScheduleList().size();
        String latest = appointment.getAppointmentScheduleList().get(totalItems - 1).toString();
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
                this.activity.onRemoveCartItem(dtoAppointmentSchedule.toString());
                this.activity.onRemoveCartItemColor(true);
                bookingBookFragment.autoExpandGroup(0);
                //Update DTO
                appointment.getAppointmentScheduleList().remove(dtoAppointmentSchedule);
            }
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }


    public void emptyCart() {
        if (!appointment.isMachineFilled())
            return;
        this.activity.onEmptyCart();
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


    public DTOAppointment getDTOAppointment() {
        return appointment;
    }

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

    public void insertAppointment() {
        this.activity.showProgress();
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
            this.activity.navigateActivity(MainActivity.class);
        else if (bookingSelectDateFragment.isVisible())
            navigateFragment(BookingSelectFragment.class);
        else if (bookingBookFragment.isVisible())
            navigateFragment(BookingSelectDateFragment.class);
    }

    @Override
    public void destroy() {
        emptyCart();
    }

    public void getCountries(int id){
        getCountriesInteractor.execute(
                success -> System.out.println(),
                er -> System.out.println()
        );
    }
}
