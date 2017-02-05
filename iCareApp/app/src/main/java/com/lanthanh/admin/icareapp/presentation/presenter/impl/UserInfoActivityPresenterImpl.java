package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.gson.Gson;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.GetCustomerIdInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.SendEmailVerifyAccInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateCustomerInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetCustomerIdInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.SendEmailVerifyAccInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateCustomerInteractorImpl;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.presenter.UserInfoActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo.ChangeEmailFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo.ContactFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo.DOBvsGenderFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo.NameAndAddressFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo.ValidateFragment;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class UserInfoActivityPresenterImpl extends AbstractPresenter implements UserInfoActivityPresenter,
            GetCustomerIdInteractor.Callback, UpdateCustomerInteractor.Callback, SendEmailVerifyAccInteractor.Callback{
    public static final String TAG = UserInfoActivityPresenterImpl.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private UserInfoActivityPresenter.View mView;
    private FragmentManager fragmentManager;
    private CustomerManager customerManager;
    private SendEmailManager sendEmailManager;
    private NameAndAddressFragment nameLocationFragment;
    private DOBvsGenderFragment dobGenderFragment;
    private ContactFragment contactFragment;
    private ValidateFragment validateFragment;
    private ChangeEmailFragment changeEmailFragment;
    private ModelUser mUser;
    private String username;

    public UserInfoActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view,
                                         FragmentManager fragmentManager, CustomerManager customerManager, SendEmailManager sendEmailManager){
        super(executor, mainThread);
        mView = view;
        this.sharedPreferences = sharedPreferences;
        this.fragmentManager = fragmentManager;
        this.customerManager = customerManager;
        this.sendEmailManager = sendEmailManager;
        init();
    }

    public void init(){
        nameLocationFragment = new NameAndAddressFragment();
        dobGenderFragment = new DOBvsGenderFragment();
        contactFragment = new ContactFragment();
        validateFragment = new ValidateFragment();
        changeEmailFragment = new ChangeEmailFragment();
        //Model User
        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
        if (mUser == null)
            mUser = new ModelUser();
    }

    @Override
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

    @Override
    public void navigateFragment(int selected) {
        if (selected == UserInfoActivity.NAME_LOCATION)
            mView.showFragment(fragmentManager, nameLocationFragment, getVisibleFragments());
        else if (selected == UserInfoActivity.DOB_GENDER)
            mView.showFragment(fragmentManager, dobGenderFragment, getVisibleFragments());
        else if (selected == UserInfoActivity.CONTACT)
            mView.showFragment(fragmentManager, contactFragment, getVisibleFragments());
        else if (selected == UserInfoActivity.VALIDATE)
            mView.showFragment(fragmentManager, validateFragment, getVisibleFragments());
        else if (selected == UserInfoActivity.CHANGEEMAIL)
            mView.showFragment(fragmentManager, changeEmailFragment, getVisibleFragments());
    }

    @Override
    public void onBackPressed() {
        if (nameLocationFragment.isVisible() || validateFragment.isVisible())
            navigateToRegisterActivity();
        else if (dobGenderFragment.isVisible())
            navigateFragment(UserInfoActivity.NAME_LOCATION);
        else if (contactFragment.isVisible())
            navigateFragment(UserInfoActivity.DOB_GENDER);
        else if (changeEmailFragment.isVisible())
            navigateFragment(UserInfoActivity.VALIDATE);

        mView.hideSoftKeyboard();
    }

    @Override
    public void navigateToMainActivity() {
        mView.navigateActivity(MainActivity.class);
    }

    @Override
    public void navigateToRegisterActivity() {
        mView.navigateActivity(RegisterActivity.class);
    }

    @Override
    public void resume() {

    }

    @Override
    public void setAddress(String address) {
        mUser.setAddress(address);
    }

    @Override
    public void setName(String name) {
        mUser.setName(name);
    }

    @Override
    public void setDob(String dob) {
        mUser.setDob(dob);
    }

    @Override
    public void setGender(String gender) {
        mUser.setGender(gender);
    }

    @Override
    public void setEmail(String email) {
        mUser.setEmail(email);
    }

    @Override
    public void setPhone(String phone) {
        mUser.setPhone(phone);
    }

    @Override
    public boolean isDobSet() {
        return mUser.getDOB() != null;
    }

    @Override
    public boolean isGenderSet() {
        return mUser.getGender() != null;
    }

    @Override
    public void onEmailChange(int string) {
        validateFragment.showEmailResult(string);
    }

    @Override
    public void getCustomerId() {
        GetCustomerIdInteractor getCustomerIdInteractor = new GetCustomerIdInteractorImpl(mExecutor, mMainThread, this, customerManager, username);
        getCustomerIdInteractor.execute();
    }

    @Override
    public void onCustomerIdFound(int id) {
        try {
            mUser.setId(id);
            UpdateCustomerInteractor updateCustomerInteractor = new UpdateCustomerInteractorImpl(mExecutor, mMainThread, this, customerManager, mUser);
            updateCustomerInteractor.execute();
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onCustomerIdNotFound() {
        try {
            onError("Fail to get customer id");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void updateCustomer() {
        mView.showProgress();
        if (mUser.getID() == 0)
            getCustomerId();
        else{
            UpdateCustomerInteractor updateCustomerInteractor = new UpdateCustomerInteractorImpl(mExecutor, mMainThread, this, customerManager, mUser);
            updateCustomerInteractor.execute();
        }
    }

    @Override
    public void onUpdateCustomerFail() {
        try {
            mView.hideProgress();
            mView.showError(mView.getStringResource(R.string.update_fail));
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onUpdateCustomerSuccess() {
        try {
            mView.hideProgress();
            mUser.setGender(ConverterForDisplay.convertStringGenderFromDBToDisplay(mUser.getGender(), mView.getStringResource(R.string.male), mView.getStringResource(R.string.male)));
            customerManager.saveLocalUserToPref(sharedPreferences, mUser);
            navigateFragment(UserInfoActivity.VALIDATE);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void sendEmailVerifyAcc() {
        mView.showProgress();
        SendEmailVerifyAccInteractor sendEmailVerifyAccInteractor = new SendEmailVerifyAccInteractorImpl(mExecutor, mMainThread, this, sendEmailManager, mUser.getEmail(), mUser.getID());
        sendEmailVerifyAccInteractor.execute();
    }

    @Override
    public void onEmailVerifyAccNotSent() {
        try {
            mView.hideProgress();
            validateFragment.showEmailResult(R.string.validate_noti_fail);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onEmailVerifyAccSent() {
        try {
            mView.hideProgress();
            validateFragment.showEmailResult(R.string.validate_noti_success);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }
}
