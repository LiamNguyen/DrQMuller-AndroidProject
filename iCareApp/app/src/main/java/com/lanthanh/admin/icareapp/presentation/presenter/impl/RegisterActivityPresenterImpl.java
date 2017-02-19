package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.auth0.jwt.JWTVerifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.RestClient;
import com.lanthanh.admin.icareapp.api.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.service.LoginService;
import com.lanthanh.admin.icareapp.data.service.RegisterService;
import com.lanthanh.admin.icareapp.data.service.Service;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.interactor.InsertNewCustomerInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.LogInInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.UpdateVerifyAccInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.InsertNewCustomerInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.LogInInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.UpdateVerifyAccInteractorImpl;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.RegisterActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.ChooseFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.SignInFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.SignUpFragment;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class RegisterActivityPresenterImpl extends AbstractPresenter implements RegisterActivityPresenter{
    public static final String TAG = RegisterActivityPresenterImpl.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private RegisterActivityPresenter.View mView;
    private FragmentManager fragmentManager;
    private CustomerManager customerManager;
    private ChooseFragment chooseFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    //Service
    private LoginService loginService;
    private RegisterService registerService;

    //Rest client
    private RestClient restClient;

    //Composite disposable
    private CompositeDisposable compositeDisposable;

    public RegisterActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view,
                                         FragmentManager fragmentManager, CustomerManager customerManager) {
        super(executor, mainThread);
        mView = view;
        this.sharedPreferences = sharedPreferences;
        this.fragmentManager = fragmentManager;
        this.customerManager = customerManager;
        init();
    }

    public void init() {
        //Init fragment
        chooseFragment = new ChooseFragment();
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        //Init rest client
        restClient = RestClientImpl.getRestClient();
        //Init service
        loginService = restClient.createService(LoginService.class);
        registerService = restClient.createService(RegisterService.class);
        //Init composite disposable
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void resume() {

    }

    @Override
    public void navigateFragment(int selected) {
        if (selected == RegisterActivity.CHOOSE)
            mView.showFragment(fragmentManager, chooseFragment, getVisibleFragments());
        else if (selected == RegisterActivity.LOG_IN)
            mView.showFragment(fragmentManager, signInFragment, getVisibleFragments());
        else if (selected == RegisterActivity.SIGN_UP)
            mView.showFragment(fragmentManager, signUpFragment, getVisibleFragments());
    }

    @Override
    public void onBackPressed() {
        if (chooseFragment.isVisible()) {
            mView.backToHomeScreen();
        } else
            navigateFragment(RegisterActivity.CHOOSE);
    }

    @Override
    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result
        if (chooseFragment.isVisible()) {
            result.add(chooseFragment);
        }
        if (signInFragment.isVisible()) {
            result.add(signInFragment);
        }
        if (signUpFragment.isVisible()) {
            result.add(signUpFragment);
        }

        return result;
    }

    @Override
    public void navigateToMainActivity() {
        Bundle extras = new Bundle();
        extras.putInt(RegisterActivity.LOGIN_STATUS, RegisterActivity.LOGGED_IN);
        mView.navigateActivity(MainActivity.class, extras);
    }

    @Override
    public void navigateToUserInfo(int id, String uiStep) {
        Bundle extras = new Bundle();
        extras.putInt(RegisterActivity.EXTRA_ID, id);
        extras.putString(RegisterActivity.EXTRA_UISTEP, uiStep);
        mView.navigateActivity(UserInfoActivity.class, extras);
    }

    @Override
    public void navigateToResetPW() {
        mView.navigateActivity(ResetPasswordActivity.class);
    }

    /*=================== USE CASES ===================*/

    @Override
    public void login(String username, String password) {
        mView.showProgress();
        mView.hideSoftKeyboard();
        RequestBody body = restClient.createRequestBody(new String[]{"username", "password"}, new String[]{username, password});
        compositeDisposable.add(
                loginService.login(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>() {
                            String result;

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    JsonArray array = jsonObject.getAsJsonArray("Select_ToAuthenticate");
                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("0"))
                                        result = Service.Status.UNAUTHORIZED;
                                    else
                                        result = array.get(1).getAsJsonObject().get("jwt").getAsString();
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Login status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Login status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                RegisterActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.UNAUTHORIZED) || result.equals(Service.Status.INTERNAL_ERROR)) {
                                    if (result.equals(Service.Status.UNAUTHORIZED)) {
                                        RegisterActivityPresenterImpl.this.mView.showError("Tên Đăng Nhập Hoặc Mật Khẩu Sai");
                                    }
                                } else {
                                    final JWTVerifier verifier = new JWTVerifier("drmuller");
                                    try {
                                        final Map<String, String> jwtClaims = (Map<String, String>) verifier.verify(result).get("data");
                                        ModelUser user = new ModelUser(Integer.parseInt(jwtClaims.get("userId")),
                                                Integer.parseInt(jwtClaims.get("active")),
                                                jwtClaims.get("step"),
                                                jwtClaims.get("userName"),
                                                jwtClaims.get("userGender"),
                                                jwtClaims.get("userDob"),
                                                jwtClaims.get("userAddress"),
                                                jwtClaims.get("userEmail"),
                                                jwtClaims.get("userPhone"));
                                        customerManager.saveLocalUserToPref(sharedPreferences, user);
                                        if (user.getActive() != 0)
                                            navigateToMainActivity();
                                        else
                                            navigateToUserInfo(user.getID(), user.getStep());
                                        Log.i(TAG, "Login status: onComplete -> " + Service.Status.SUCCESS);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Login status: onComplete -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })

        );
    }

    @Override
    public void register(String username, String password) {
        mView.showProgress();
        mView.hideSoftKeyboard();
        RequestBody body = restClient.createRequestBody(new String[]{"username", "password"}, new String[]{username, password});
        compositeDisposable.add(
                registerService.register(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>() {
                            String result;

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    JsonArray array = jsonObject.getAsJsonArray("Insert_NewCustomer");
                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("0"))
                                        result = Service.Status.INTERNAL_ERROR;
                                    else if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("2"))
                                        result = Service.Status.EXISTED;
                                    else
                                        result = array.get(1).getAsJsonObject().get("jwt").getAsString();
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Register status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Register status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                RegisterActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.EXISTED) || result.equals(Service.Status.INTERNAL_ERROR)) {
                                    if (result.equals(Service.Status.EXISTED)) {
                                        RegisterActivityPresenterImpl.this.mView.showError(mView.getStringResource(R.string.username_invalid));
                                    }
                                } else {
                                    final JWTVerifier verifier = new JWTVerifier("drmuller");
                                    try {
                                        final Map<String, String> jwtClaims = (Map<String, String>) verifier.verify(result).get("data");
                                        navigateToUserInfo(Integer.parseInt(jwtClaims.get("userId")), jwtClaims.get("step"));
                                        Log.i(TAG, "Register status: onComplete -> " + Service.Status.SUCCESS);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Register status: onComplete -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })

        );
    }

    @Override
    public void verifyAccount(String id) {
        mView.showProgress();
        mView.hideSoftKeyboard();
        RequestBody body = restClient.createRequestBody(new String[]{"cus_id"}, new String[]{id});
        compositeDisposable.add(
                registerService.verifyAccount(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JsonObject>() {
                            String result;

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                try {
                                    result = jsonObject.get("Update_VerifyAcc").getAsString();
                                    if (result.equals("Updated"))
                                        result = Service.Status.SUCCESS;
                                    else
                                        result = Service.Status.FAILED;
                                } catch (Exception e) {
                                    result = Service.Status.INTERNAL_ERROR;
                                    Log.e(TAG, "Register status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Register status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                RegisterActivityPresenterImpl.this.mView.hideProgress();
                                if (result.equals(Service.Status.INTERNAL_ERROR)) {
                                    Log.e(TAG, "Register status: onComplete -> " + result);
                                } else if (result.equals(Service.Status.FAILED)) {
                                    mView.showAlertDialog(R.string.verify_fail);
                                    Log.e(TAG, "Register status: onComplete -> " + result);
                                } else {
                                    mView.showAlertDialog(R.string.verify_success);
                                    Log.i(TAG, "Register status: onComplete -> " + result);
                                }
                            }
                        })

        );
    }
}