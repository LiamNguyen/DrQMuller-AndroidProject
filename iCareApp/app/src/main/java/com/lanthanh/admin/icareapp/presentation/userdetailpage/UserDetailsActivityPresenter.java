package com.lanthanh.admin.icareapp.presentation.userdetailpage;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.utils.Function;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.utils.converter.ConverterForDisplay;

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
                populateDob.apply(ConverterForDisplay.convertDateForDisplay(user.getDateOfBirth()));
                populateGender.apply(ConverterForDisplay.convertGenderForDisplay(user.getGender(), this.activity.getString(R.string.male), this.activity.getString(R.string.female)));
                populateEmail.apply(user.getEmail());
                populatePhone.apply(user.getPhone());
            },
            error -> {}
        );
    }

    public void updateCustomerInformation(String name, String address, String dob, String gender, String email, String phone) {
        this.activity.showProgress();
        interactor.execute(
            () -> userRepository.updateCustomerInfo(name, address, ConverterForDisplay.convertDateForDb(dob),
                    ConverterForDisplay.convertGenderForDb(gender, this.activity.getString(R.string.male), this.activity.getString(R.string.female)), email, phone),
            success -> {
                this.activity.hideProgress();
                this.activity.refreshViews();
            },
            error -> this.activity.hideProgress()
        );
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
}
