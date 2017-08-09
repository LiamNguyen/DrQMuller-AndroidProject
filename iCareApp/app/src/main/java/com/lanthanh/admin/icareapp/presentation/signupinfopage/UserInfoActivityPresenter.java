package com.lanthanh.admin.icareapp.presentation.signupinfopage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by ADMIN on 10-Jan-17.
 */

public class UserInfoActivityPresenter extends BasePresenter {
    private NameAndAddressFragment nameLocationFragment;
    private DOBvsGenderFragment dobGenderFragment;
    private ContactFragment contactFragment;
    private ValidateFragment validateFragment;
    private ChangeEmailFragment changeEmailFragment;
    private UserInfoActivity activity;

    private WelcomeRepository welcomeRepository;
    private UserRepository userRepository;
    private Interactor interactor;
    private RepositorySimpleStatus currentStatus;

    public UserInfoActivityPresenter(UserInfoActivity activity){
        super(activity);
        this.activity = activity;
        init();
    }

    public void init(){
        //Init fragment
        nameLocationFragment = new NameAndAddressFragment();
        dobGenderFragment = new DOBvsGenderFragment();
        contactFragment = new ContactFragment();
        validateFragment = new ValidateFragment();
        changeEmailFragment = new ChangeEmailFragment();

        welcomeRepository = new WelcomeRepositoryImpl(this.activity);
        userRepository = new UserRepositoryImpl(this.activity);
        interactor = new Interactor();
    }

    @Override
    public void destroy() {
        interactor.dispose();
    }

    public List<Fragment> getVisibleFragments() {
        //Create array of 3 for 3 fragments
        List<Fragment> result = new ArrayList<>(5);

        //Add each visble fragments
        if (nameLocationFragment.isVisible()){
            result.add(nameLocationFragment);
        }
        if (dobGenderFragment.isVisible()){
            result.add(dobGenderFragment);
        }
        if (contactFragment.isVisible()){
            result.add(contactFragment);
        }
        if (validateFragment.isVisible()){
            result.add(validateFragment);
        }
        if (changeEmailFragment.isVisible()){
            result.add(changeEmailFragment);
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
            fragmentTransaction.add(R.id.ui_fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    public void navigateFragment(Class<? extends Fragment> fragmentClass) {
        this.activity.hideSoftKeyboard();
        this.activity.hideProgress();
        if (fragmentClass == NameAndAddressFragment.class)
            showFragment(nameLocationFragment);
        else if (fragmentClass == DOBvsGenderFragment.class)
            showFragment(dobGenderFragment);
        else if (fragmentClass == ContactFragment.class)
            showFragment(contactFragment);
        else if (fragmentClass == ValidateFragment.class)
            showFragment(validateFragment);
        else if (fragmentClass == ChangeEmailFragment.class)
            showFragment(changeEmailFragment);
    }

    public void onBackPressed() {
        if (currentStatus == null) currentStatus = RepositorySimpleStatus.MISSING_NAME_AND_ADDRESS;
        switch (currentStatus) {
            case MISSING_DOB_AND_GENDER:
                if (dobGenderFragment.isVisible())
                    navigateActivity(WelcomeActivity.class);
                else if (contactFragment.isVisible())
                    navigateFragment(DOBvsGenderFragment.class);
                break;
            case MISSING_EMAIL_AND_PHONE:
                navigateActivity(WelcomeActivity.class);
                break;
            case MISSING_NAME_AND_ADDRESS:
            default:
                if (nameLocationFragment.isVisible())
                    navigateActivity(WelcomeActivity.class);
                else if (dobGenderFragment.isVisible())
                    navigateFragment(NameAndAddressFragment.class);
                else if (contactFragment.isVisible())
                    navigateFragment(DOBvsGenderFragment.class);
                break;
        }
    }

    public void checkUserInformationValidity(){
        interactor.execute(
            () -> userRepository.checkUserInformationValidity(),
            success -> {
                if (currentStatus == null) currentStatus = success;
                if (success == RepositorySimpleStatus.MISSING_NAME_AND_ADDRESS)
                    navigateFragment(NameAndAddressFragment.class);
                else if (success == RepositorySimpleStatus.MISSING_DOB_AND_GENDER)
                    navigateFragment(DOBvsGenderFragment.class);
                else if (success == RepositorySimpleStatus.MISSING_EMAIL_AND_PHONE)
                    navigateFragment(ContactFragment.class);
                else //Default
                    navigateFragment(NameAndAddressFragment.class);
            },
            error -> {}
        );
    }

    /*=================== USE CASES ===================*/

    public void updateBasicInfo(String name, String address) {
        this.activity.showProgress();
        interactor.execute(
            () -> userRepository.updateCustomerBasicInfo(name, address),
            success -> {
                this.activity.hideProgress();
                navigateFragment(DOBvsGenderFragment.class);
            },
            error -> this.activity.hideProgress()
        );
    }

    public void updateNecessaryInfo(String dob, String gender) {
        this.activity.showProgress();
        interactor.execute(
            () -> userRepository.updateCustomerNecessaryInfo(dob, gender),
            success -> {
                this.activity.hideProgress();
                navigateFragment(ContactFragment.class);
            },
            error -> this.activity.hideProgress()
        );
    }

    public void updateImportantInfo(String email, String phone) {
        this.activity.showProgress();
        interactor.execute(
            () -> userRepository.updateCustomerImportantInfo(email, phone),
            success -> {
                this.activity.hideProgress();
                navigateActivity(MainActivity.class);
            },
            error -> this.activity.hideProgress()
        );
    }
}
