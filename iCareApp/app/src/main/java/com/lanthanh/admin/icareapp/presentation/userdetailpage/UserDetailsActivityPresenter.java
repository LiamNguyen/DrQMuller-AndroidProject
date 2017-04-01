package com.lanthanh.admin.icareapp.presentation.userdetailpage;

import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.presentation.Function;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public class UserDetailsActivityPresenter extends BasePresenter {
    private UserDetailsActivity activity;

    private UserRepository userRepository;
    private Interactor interactor;

    public UserDetailsActivityPresenter(UserDetailsActivity activity) {
        this.activity = activity;
        init();
    }

    public void init(){
        userRepository = new UserRepositoryImpl(this.activity);
        interactor = new Interactor();
    }

    @Override
    public void resume() {}

    public void populateUserInformation(Function.Void<String> populateName, Function.Void<String> populateAddress,
                                        Function.Void<String> populateDob, Function.Void<String> populateGender,
                                        Function.Void<String> populateEmail, Function.Void<String> populatePhone) {
        interactor.execute(
            () -> userRepository.getUserInformation(),
            user -> {
                populateName.apply(user.getName());
                populateAddress.apply(user.getAddress());
                populateDob.apply(user.getDateOfBirth());
                populateGender.apply(user.getGender());
                populateEmail.apply(user.getEmail());
                populatePhone.apply(user.getPhone());
            },
            error -> {}
        );
    }

    public void updateCustomerInformation() {

    }

    @Override
    public void destroy() {
        interactor.dispose();
    }

    public void showDobDialogFragment() {
        DobFragment dobFragment = new DobFragment();
        dobFragment.show(this.activity.getSupportFragmentManager(), dobFragment.getClass().getName());
    }

    public void showGenderDialogFragment() {
        GenderFragment genderFragment = new GenderFragment();
        genderFragment.show(this.activity.getSupportFragmentManager(), genderFragment.getClass().getName());
    }

//    @Override
//    public void updateCustomer() {
////        UpdateCustomerInteractor updateCustomerInteractor = new UpdateCustomerInteractorImpl(mExecutor, mMainThread, this, customerManager, mUser);
////        updateCustomerInteractor.execute();
//    }

//    public void onUpdateCustomerSuccess() {
//        try {
//            customerManager.saveLocalUserToPref(sharedPreferences, mUser);
//            if (mAppointments.size() != 0) {
//                for (DTOAppointment dtoAppointment: mAppointments){
//                    dtoAppointment.setCustomer(mUser);
//                }
//                appointmentManager.saveLocalAppointmentsToPref(sharedPreferences, mAppointments, mUser.getID());
//            }
//            mView.refreshViews();
//        }catch (Exception e){
//            Log.w(TAG, e.toString());
//        }
//    }
}
