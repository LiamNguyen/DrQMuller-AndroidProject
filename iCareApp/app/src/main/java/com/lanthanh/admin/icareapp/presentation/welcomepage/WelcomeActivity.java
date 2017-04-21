package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.lanthanh.admin.icareapp.utils.injection.Injection;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragmentActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.signupinfopage.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class WelcomeActivity extends BaseFragmentActivity {
    private WelcomeContract.Presenter mPresenter;
    private ChooseFragment chooseFragment;
    private LogInFragment logInFragment;
    private SignUpFragment signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init fragment
        chooseFragment = new ChooseFragment();
        logInFragment = new LogInFragment();
        signUpFragment = new SignUpFragment();
        //Init presenter
        mPresenter = new WelcomeActivityPresenter(
                                logInFragment,
                                signUpFragment,
                                Injection.provideWelcomeRepository(this),
                                Injection.provideUserRepository(this),
                                Injection.provideScheduleProvider());
        mPresenter.setNavigator(new WelcomeContract.Navigator() {
            @Override public void goToMainPage() {navigateActivity(MainActivity.class);}
            @Override public void goToUserInfoPage() {
                navigateActivity(UserInfoActivity.class);
            }
        });

        navigateFragment(chooseFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public List<Fragment> getVisibleFragments() {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result
        if (chooseFragment.isVisible()) {
            result.add(chooseFragment);
        }
        if (logInFragment.isVisible()) {
            result.add(logInFragment);
        }
        if (signUpFragment.isVisible()) {
            result.add(signUpFragment);
        }

        return result;
    }

    public void showLogInPage() {
        showFragment(logInFragment);
    }

    public void showSignUpPage() {
        showFragment(signUpFragment);
    }

    public void backToHomeScreen() {
        hideProgress();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    public void onBackPressed() {
        hideProgress();
        if (logInFragment.isVisible() || signUpFragment.isVisible()) {
            navigateFragment(chooseFragment);
        } else {
            backToHomeScreen();
        }
    }

    @Override
    public void refreshAfterLosingNetwork() {}
}

