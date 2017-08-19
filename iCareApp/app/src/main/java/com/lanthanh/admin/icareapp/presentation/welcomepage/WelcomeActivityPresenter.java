package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;
import com.lanthanh.admin.icareapp.exceptions.UseCaseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public class WelcomeActivityPresenter  {
    public static final String TAG = WelcomeActivityPresenter.class.getSimpleName();
    //private ChooseFragment chooseFragment;
    private LoginFragment logInFragment;
    private SignUpFragment signUpFragment;
    private WelcomeActivity activity;

    private WelcomeRepository welcomeRepository;
    private UserRepository userRepository;
    private Interactor interactor;

    public WelcomeActivityPresenter(WelcomeActivity activity) {
        //super(activity);
        this.activity = activity;
        init();
    }

    private void init() {
        //Init fragment
        //chooseFragment = new ChooseFragment();
        logInFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();

        welcomeRepository = new WelcomeRepositoryImpl(this.activity);
        userRepository = new UserRepositoryImpl(this.activity);
        interactor = new Interactor();
    }

    public void navigateFragment(Class<? extends Fragment> fragmentClass) {
        //this.activity.hideSoftKeyboard();
        //if (fragmentClass == ChooseFragment.class) {
            //WelcomeActivity.Companion.getCURRENT_FRAGMENT() = WelcomeActivity.Companion.getCHOOSE_FRAGMENT();
            //showFragment(chooseFragment);
      //  }
        //else if (fragmentClass == LoginFragment.class) {
            //WelcomeActivity.CURRENT_FRAGMENT = WelcomeActivity.LOGIN_FRAGMENT;
         //   showFragment(logInFragment);
    //    }
      //  else if (fragmentClass == SignUpFragment.class) {
      //      //WelcomeActivity.CURRENT_FRAGMENT = WelcomeActivity.SIGNUP_FRAGMENT;
     //       showFragment(signUpFragment);
    ////    }
    }

    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result
        //if (chooseFragment.isVisible()) {
         //   result.add(chooseFragment);
       // }
        if (logInFragment.isVisible()) {
            result.add(logInFragment);
        }
        if (signUpFragment.isVisible()) {
            result.add(signUpFragment);
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
            fragmentTransaction.add(R.id.fragmentContainer, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    public void login(String username, String password){
        //this.activity.showProgress();
        interactor.execute(
            () -> welcomeRepository.login2(username, password),
            success -> {
                //this.activity.hideProgress();
                interactor.execute(
                        () -> userRepository.checkUserInformationValidity(),
                        check -> {
//                            if (check == RepositorySimpleStatus.VALID_USER)
//                                //navigateActivity(MainActivity.class);
//                            else //Default
//                                navigateActivity(UserInfoActivity.class);
                        },
                        error -> {}
                );
            },
            error -> {
                //this.activity.hideProgress();
                if (error instanceof UseCaseException) {
                    switch (((UseCaseException) error).getStatus()) {
                        case PATTERN_FAIL:
                            //this.activity.showToast(this.activity.getString(R.string.pattern_fail));
                            break;
                        case USERNAME_PASSWORD_NOT_MATCH:
                            //this.activity.showToast(this.activity.getString(R.string.login_fail));
                            break;
                    }
                }
            }
        );
    }

    public void onBackPressed() {
        //this.activity.hideProgress();
        if (logInFragment.isVisible() || signUpFragment.isVisible()) {
            //navigateFragment(ChooseFragment.class);
        } else {
            this.activity.backToDeviceHomeScreen();
        }
    }

    public void signup(String username, String password){
        //this.activity.showProgress();
        interactor.execute(
            () -> welcomeRepository.signup(username, password),
            success -> {
                //this.activity.hideProgress();
                //navigateActivity(UserInfoActivity.class);
            },
                error -> {
                    //this.activity.hideProgress();
                    if (error instanceof UseCaseException) {
                        switch (((UseCaseException) error).getStatus()) {
                            case PATTERN_FAIL:
                                //this.activity.showToast(this.activity.getString(R.string.pattern_fail));
                                break;
                            case USERNAME_EXISTED:
                                //this.activity.showToast(this.activity.getString(R.string.username_unavailable));
                                break;
                        }
                    }
                }
        );
    }

//    @Override
//    public void destroy() {
//        interactor.dispose();
//    }
}