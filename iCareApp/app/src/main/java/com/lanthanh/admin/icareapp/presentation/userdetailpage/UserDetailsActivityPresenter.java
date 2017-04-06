package com.lanthanh.admin.icareapp.presentation.userdetailpage;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.utils.ConverterUtils;
import com.lanthanh.admin.icareapp.utils.Function;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public class UserDetailsActivityPresenter extends BasePresenter {
    private UserDetailsActivity activity;

    private UserRepository userRepository;
    private Interactor interactor;

    public UserDetailsActivityPresenter(UserDetailsActivity activity) {
        super(activity);
        this.activity = activity;
        init();
    }

    public void init(){
        userRepository = new UserRepositoryImpl(this.activity);
        interactor = new Interactor();
    }

    public void populateUserInformation(Function.VoidParam<String> populateName, Function.VoidParam<String> populateAddress,
                                        Function.VoidParam<String> populateDob, Function.VoidParam<String> populateGender,
                                        Function.VoidParam<String> populateEmail, Function.VoidParam<String> populatePhone) {
        interactor.execute(
            () -> userRepository.getUserInformation(),
            user -> {
                populateName.apply(user.getName());
                populateAddress.apply(user.getAddress());
                populateDob.apply(ConverterUtils.date.convertDateForDisplay(user.getDateOfBirth()));
                populateGender.apply(ConverterUtils.gender.convertGenderForDisplay(user.getGender(), this.activity.getString(R.string.male), this.activity.getString(R.string.female)));
                populateEmail.apply(user.getEmail());
                populatePhone.apply(user.getPhone());
            },
            error -> {}
        );
    }

    public void updateCustomerInformation(String name, String address, String dob, String gender, String email, String phone) {
        this.activity.showProgress();
        interactor.execute(
            () -> userRepository.updateCustomerInfo(name, address, ConverterUtils.date.convertDateForDb(dob),
                    ConverterUtils.gender.convertGenderForDb(gender, this.activity.getString(R.string.male), this.activity.getString(R.string.female)), email, phone),
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
