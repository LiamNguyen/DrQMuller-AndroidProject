package com.lanthanh.admin.icareapp.presentation.signupinfopage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivity;

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

    public UserInfoActivityPresenter(UserInfoActivity activity){
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
        this.activity.hideSoftKeyboard();
        if (nameLocationFragment.isVisible() || validateFragment.isVisible())
            navigateActivity(WelcomeActivity.class);
        else if (dobGenderFragment.isVisible())
            navigateFragment(NameAndAddressFragment.class);
        else if (contactFragment.isVisible())
            navigateFragment(DOBvsGenderFragment.class);
        else if (changeEmailFragment.isVisible())
            navigateFragment(ValidateFragment.class);
    }

    @Override
    public void resume() {}

    public void checkUserInformationValidity(){
        interactor.execute(
            () -> userRepository.checkUserInformationValidity(),
            success -> {
                if (success == RepositorySimpleStatus.MISSING_NAME_AND_ADDRESS)
                    navigateFragment(NameAndAddressFragment.class);
                else if (success == RepositorySimpleStatus.MISSING_DOB_AND_GENDER)
                    navigateFragment(DOBvsGenderFragment.class);
                else if (success == RepositorySimpleStatus.MISSING_EMAIL_AND_PHONE)
                    navigateFragment(ContactFragment.class);
                else if (success == RepositorySimpleStatus.VALID_USER)
                    navigateActivity(MainActivity.class);
                else //Default
                    navigateFragment(NameAndAddressFragment.class);
            },
            error -> {}
        );
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

    /*=================== USE CASES ===================*/

    public void updateBasicInfo(String name, String address) {
        this.activity.showProgress();
        interactor.execute(
            () -> welcomeRepository.updateCustomerBasicInfo(name, address),
            success -> {
                this.activity.hideProgress();
                if (success == RepositorySimpleStatus.SUCCESS){
                    checkUserInformationValidity();
                }
            },
            error -> this.activity.hideProgress()
        );
    }

    public void updateNecessaryInfo(String dob, String gender) {
        this.activity.showProgress();
        interactor.execute(
            () -> welcomeRepository.updateCustomerNecessaryInfo(dob, gender),
            success -> {
                this.activity.hideProgress();
                if (success == RepositorySimpleStatus.SUCCESS){
                    checkUserInformationValidity();
                }
            },
            error -> this.activity.hideProgress()
        );
    }

    public void updateImportantInfo(String email, String phone) {
        this.activity.showProgress();
        interactor.execute(
            () -> welcomeRepository.updateCustomerImportantInfo(email, phone),
            success -> {
                this.activity.hideProgress();
                if (success == RepositorySimpleStatus.SUCCESS){
                    checkUserInformationValidity();
                }
            },
            error -> this.activity.hideProgress()
        );
    }

//    @Override
//    public void updateEmail() {
//        mView.showProgress();
//        RequestBody body = restClient.createRequestBody(new String[]{"userId", "userEmail", "updatedAt"},
//                                new String[]{Integer.toString(mUser.getID()), mUser.getEmail(), NetworkUtils.convertDateForDB(Calendar.getInstance().getTime())});
//        compositeDisposable.add(
//                registerService.updateEmail(body)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<JsonObject>(){
//                            String result;
//                            @Override
//                            public void onComplete() {
//                                UserInfoActivityPresenterImpl.this.mView.hideProgress();
//                                if (result.equals(Service.Status.FAILED) || result.equals(Service.Status.INTERNAL_ERROR)) {
//                                    if (result.equals(Service.Status.FAILED))
//                                        Log.e(TAG, "Update email status: onComplete -> " + result);
//                                }else{
//                                    UserInfoActivityPresenterImpl.this.navigateFragment(UserInfoActivity.VALIDATE);
//                                    Log.i(TAG, "Update email status: onComplete -> " + Service.Status.SUCCESS);
//                                }
//                            }
//
//                            @Override
//                            public void onNext(JsonObject jsonObject) {
//                                try {
//                                    JsonArray array = jsonObject.getAsJsonArray("Update_CustomerEmail");
//                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("1"))
//                                        result = Service.Status.SUCCESS;
//                                    else
//                                        result = Service.Status.FAILED;
//                                } catch (Exception e) {
//                                    result = Service.Status.INTERNAL_ERROR;
//                                    Log.e(TAG, "Update email status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Update email status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
//                                e.printStackTrace();
//                            }
//                        })
//        );
//    }
//
//    @Override
//    public void sendEmailVerifyAcc() {
//        mView.showProgress();
//        RequestBody body = restClient.createRequestBody(new String[]{"cus_id", "email"},
//                                                        new String[]{Integer.toString(mUser.getID()), mUser.getEmail()});
//        compositeDisposable.add(
//                emailService.sendEmailVerifyAcc(body)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<JsonObject>(){
//                            String result;
//                            @Override
//                            public void onComplete() {
//                                UserInfoActivityPresenterImpl.this.mView.hideProgress();
//                                if (result.equals(Service.Status.FAILED) || result.equals(Service.Status.INTERNAL_ERROR)) {
//                                    UserInfoActivityPresenterImpl.this.validateFragment.showEmailResult(R.string.validate_noti_fail);
//                                    if (result.equals(Service.Status.FAILED))
//                                        Log.e(TAG, "Send verify email status: onComplete -> " + result);
//                                }else{
//                                    UserInfoActivityPresenterImpl.this.validateFragment.showEmailResult(R.string.validate_noti_success);
//                                    Log.i(TAG, "Send verify email status: onComplete -> " + Service.Status.SUCCESS);
//                                }
//                            }
//
//                            @Override
//                            public void onNext(JsonObject jsonObject) {
//                                try {
//                                    result = jsonObject.get("SendEmail_VerifyAcc").getAsString();
//                                    if (result.equals("Message has been sent"))
//                                        result = Service.Status.SUCCESS;
//                                    else
//                                        result = Service.Status.FAILED;
//                                } catch (Exception e) {
//                                    result = Service.Status.INTERNAL_ERROR;
//                                    Log.e(TAG, "Send verify email status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Send verify email status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
//                                e.printStackTrace();
//                            }
//                        })
//        );
//    }
}
