package com.lanthanh.admin.icareapp.presentation.signupinfopage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by ADMIN on 10-Jan-17.
 */

public class UserInfoActivityPresenterImpl extends BasePresenter {
    public static final String TAG = UserInfoActivityPresenterImpl.class.getSimpleName();

    private NameAndAddressFragment nameLocationFragment;
    private DOBvsGenderFragment dobGenderFragment;
    private ContactFragment contactFragment;
    private ValidateFragment validateFragment;
    private ChangeEmailFragment changeEmailFragment;
    private UserInfoActivity activity;

    private WelcomeRepository welcomeRepository;

    public UserInfoActivityPresenterImpl(UserInfoActivity activity){
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
            fragmentTransaction.add(R.id.wel_fragment_container, f, f.getClass().getName());
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

//    @Override
//    public void onBackPressed() {
//        if (nameLocationFragment.isVisible() || validateFragment.isVisible())
//            navigateToRegisterActivity();
//        else if (dobGenderFragment.isVisible())
//            navigateFragment(UserInfoActivity.NAME_LOCATION);
//        else if (contactFragment.isVisible())
//            navigateFragment(UserInfoActivity.DOB_GENDER);
//        else if (changeEmailFragment.isVisible())
//            navigateFragment(UserInfoActivity.VALIDATE);
//
//        mView.hideSoftKeyboard();
//    }

//    @Override
//    public void navigateToMainActivity() {
//        mView.navigateActivity(MainActivity.class);
//    }
//
//    @Override
//    public void navigateToRegisterActivity() {
//        mView.navigateActivity(RegisterActivity.class);
//    }

    @Override
    public void resume() {

    }

//    @Override
//    public void setUserId(int userId) {
//        mUser.setId(userId);
//    }
//
//    @Override
//    public void setAddress(String address) {
//        mUser.setAddress(address);
//    }
//
//    @Override
//    public void setName(String name) {
//        mUser.setName(name);
//    }
//
//    @Override
//    public void setDob(String dob) {
//        mUser.setDob(dob);
//    }
//
//    @Override
//    public void setGender(String gender) {
//        mUser.setGender(gender);
//    }
//
//    @Override
//    public void setEmail(String email) {
//        mUser.setEmail(email);
//    }
//
//    @Override
//    public void setPhone(String phone) {
//        mUser.setPhone(phone);
//    }
//
//    @Override
//    public boolean isDobSet() {
//        return mUser.getDOB() != null;
//    }
//
//    @Override
//    public boolean isGenderSet() {
//        return mUser.getGender() != null;
//    }
//
//    @Override
//    public void onEmailChange(int string) {
//        validateFragment.showEmailResult(string);
//    }

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
//        new UpdateCustomerBasicInfoInteractor(welcomeRepository).execute(
//                success -> {
//                    this.activity.hideProgress();
//                    if (success == RepositorySimpleStatus.SUCCESS){
//                        navigateFragment(DOBvsGenderFragment.class);
//                    }
//                },
//                error -> {
//                    this.activity.hideProgress();
//                },
//                () -> {},
//                UpdateCustomerBasicInfoInteractor.Params.forUpdateCustomerBasicInfo(name, address)
//        );
    }

    public void updateNecessaryInfo(String dob, String gender) {
        this.activity.showProgress();
//        new UpdateCustomerNecessaryInfoInteractor(welcomeRepository).execute(
//                success -> {
//                    this.activity.hideProgress();
//                    if (success == RepositorySimpleStatus.SUCCESS){
//                        navigateFragment(ContactFragment.class);
//                    }
//                },
//                error -> {
//                    this.activity.hideProgress();
//                },
//                () -> {},
//                UpdateCustomerNecessaryInfoInteractor.Params.forUpdateCustomerNecessaryInfo(dob, gender)
//        );
    }

    public void updateImportantInfo(String email, String phone) {
        this.activity.showProgress();
//        new UpdateCustomerImportantInfoInteractor(welcomeRepository).execute(
//                success -> {
//                    this.activity.hideProgress();
//                    if (success == RepositorySimpleStatus.SUCCESS){
//                        navigateFragment(ValidateFragment.class);
//                    }
//                },
//                error -> {
//                    this.activity.hideProgress();
//                },
//                () -> {},
//                UpdateCustomerImportantInfoInteractor.Params.forUpdateCustomerImportantInfo(phone, email)
//        );
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
