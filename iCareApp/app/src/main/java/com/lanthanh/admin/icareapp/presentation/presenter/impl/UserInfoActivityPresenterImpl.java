package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.RestClient;
import com.lanthanh.admin.icareapp.api.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.SendEmailManager;
import com.lanthanh.admin.icareapp.data.service.EmailService;
import com.lanthanh.admin.icareapp.data.service.LoginService;
import com.lanthanh.admin.icareapp.data.service.RegisterService;
import com.lanthanh.admin.icareapp.data.service.Service;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.SendEmailVerifyAccInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateCustomerBasicInfoInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateCustomerEmailInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateCustomerImportantInfoInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateCustomerInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateCustomerNecessaryInfo;
import com.lanthanh.admin.icareapp.domain.interactor.impl.SendEmailVerifyAccInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateCustomerBasicInfoInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateCustomerEmailInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateCustomerImportantInfoInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateCustomerInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateCustomerNecessaryInfoInteractorImpl;
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
import com.lanthanh.admin.icareapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 *
 * Created by ADMIN on 10-Jan-17.
 */

public class UserInfoActivityPresenterImpl extends AbstractPresenter implements UserInfoActivityPresenter{
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
    private int userId;

    //Service
    private RegisterService registerService;
    private EmailService emailService;

    //Rest client
    private RestClient restClient;

    //Composite disposable
    private CompositeDisposable compositeDisposable;

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
        //Init fragment
        nameLocationFragment = new NameAndAddressFragment();
        dobGenderFragment = new DOBvsGenderFragment();
        contactFragment = new ContactFragment();
        validateFragment = new ValidateFragment();
        changeEmailFragment = new ChangeEmailFragment();
        //Model User
        mUser = customerManager.getLocalUserFromPref(sharedPreferences);
        if (mUser == null)
            mUser = new ModelUser();
        //Init rest client
        restClient = RestClientImpl.getRestClient();
        //Init service
        registerService = restClient.createService(RegisterService.class);
        emailService = restClient.createService(EmailService.class);
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
    public void setUserId(int userId) {
        mUser.setId(userId);
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

    /*=================== USE CASES ===================*/

    @Override
    public void updateBasicInfo() {
        mView.showProgress();
        RequestBody body = restClient.createRequestBody(new String[]{"userId", "userName", "userAddress", "updatedAt"},
                                                        new String[]{Integer.toString(mUser.getID()), mUser.getName(), mUser.getAddress(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
        compositeDisposable.add(
            registerService.updateBasicInfo(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>(){
                            String result;
                            @Override
                            public void onComplete() {
                                UserInfoActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.FAILED) || result.equals(Service.Status.INTERNAL_ERROR)) {
                                    if (result.equals(Service.Status.FAILED))
                                        Log.e(TAG, "Update basic info status: onComplete -> " + result);
                                }else{
                                    UserInfoActivityPresenterImpl.this.navigateFragment(UserInfoActivity.DOB_GENDER);
                                    Log.i(TAG, "Update basic info status: onComplete -> " + Service.Status.SUCCESS);
                                }
                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    JsonArray array = jsonObject.getAsJsonArray("Update_BasicInfo");
                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                                        result = Service.Status.SUCCESS;
                                    else
                                        result = Service.Status.FAILED;
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Update basic info status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Update basic info status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    public void updateNecessaryInfo() {
        mView.showProgress();
        RequestBody body = restClient.createRequestBody(new String[]{"userId", "userDob", "userGender", "updatedAt"},
                new String[]{Integer.toString(mUser.getID()), mUser.getDOB(), mUser.getGender(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
        compositeDisposable.add(
                registerService.updateNecessaryInfo(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>(){
                            String result;
                            @Override
                            public void onComplete() {
                                UserInfoActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.FAILED) || result.equals(Service.Status.INTERNAL_ERROR)) {
                                    if (result.equals(Service.Status.FAILED))
                                        Log.e(TAG, "Update necessary info status: onComplete -> " + result);
                                }else{
                                    UserInfoActivityPresenterImpl.this.navigateFragment(UserInfoActivity.CONTACT);
                                    Log.i(TAG, "Update necessary info status: onComplete -> " + Service.Status.SUCCESS);
                                }
                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    JsonArray array = jsonObject.getAsJsonArray("Update_NecessaryInfo");
                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                                        result = Service.Status.SUCCESS;
                                    else
                                        result = Service.Status.FAILED;
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Update necessary info status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Update necessary info status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    public void updateImportantInfo() {
        mView.showProgress();
        RequestBody body = restClient.createRequestBody(new String[]{"userId", "userEmail", "userPhone", "updatedAt"},
                new String[]{Integer.toString(mUser.getID()), mUser.getEmail(), mUser.getPhone(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
        compositeDisposable.add(
                registerService.updateImportantInfo(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>(){
                            String result;
                            @Override
                            public void onComplete() {
                                UserInfoActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.FAILED) || result.equals(Service.Status.INTERNAL_ERROR)) {
                                    if (result.equals(Service.Status.FAILED))
                                        Log.e(TAG, "Update important info status: onComplete -> " + result);
                                }else{
                                    UserInfoActivityPresenterImpl.this.navigateFragment(UserInfoActivity.VALIDATE);
                                    Log.i(TAG, "Update important info status: onComplete -> " + Service.Status.SUCCESS);
                                }
                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    JsonArray array = jsonObject.getAsJsonArray("Update_ImportantInfo");
                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                                        result = Service.Status.SUCCESS;
                                    else
                                        result = Service.Status.FAILED;
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Update important info status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Update important info status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    public void updateEmail() {
        mView.showProgress();
        RequestBody body = restClient.createRequestBody(new String[]{"userId", "userEmail", "updatedAt"},
                                new String[]{Integer.toString(mUser.getID()), mUser.getEmail(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
        compositeDisposable.add(
                registerService.updateEmail(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>(){
                            String result;
                            @Override
                            public void onComplete() {
                                UserInfoActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.FAILED) || result.equals(Service.Status.INTERNAL_ERROR)) {
                                    if (result.equals(Service.Status.FAILED))
                                        Log.e(TAG, "Update email status: onComplete -> " + result);
                                }else{
                                    UserInfoActivityPresenterImpl.this.navigateFragment(UserInfoActivity.VALIDATE);
                                    Log.i(TAG, "Update email status: onComplete -> " + Service.Status.SUCCESS);
                                }
                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    JsonArray array = jsonObject.getAsJsonArray("Update_CustomerEmail");
                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
                                        result = Service.Status.SUCCESS;
                                    else
                                        result = Service.Status.FAILED;
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Update email status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Update email status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    public void sendEmailVerifyAcc() {
        mView.showProgress();
        RequestBody body = restClient.createRequestBody(new String[]{"cus_id", "email"},
                                                        new String[]{Integer.toString(mUser.getID()), mUser.getEmail()});
        compositeDisposable.add(
                emailService.sendEmailVerifyAcc(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>(){
                            String result;
                            @Override
                            public void onComplete() {
                                UserInfoActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.FAILED) || result.equals(Service.Status.INTERNAL_ERROR)) {
                                    UserInfoActivityPresenterImpl.this.validateFragment.showEmailResult(R.string.validate_noti_fail);
                                    if (result.equals(Service.Status.FAILED))
                                        Log.e(TAG, "Send verify email status: onComplete -> " + result);
                                }else{
                                    UserInfoActivityPresenterImpl.this.validateFragment.showEmailResult(R.string.validate_noti_success);
                                    Log.i(TAG, "Send verify email status: onComplete -> " + Service.Status.SUCCESS);
                                }
                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    result = jsonObject.get("SendEmail_VerifyAcc").getAsString();
                                    if (result.equals("Message has been sent"))
                                        result = Service.Status.SUCCESS;
                                    else
                                        result = Service.Status.FAILED;
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Send verify email status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Send verify email status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }
                        })
        );
    }
}
