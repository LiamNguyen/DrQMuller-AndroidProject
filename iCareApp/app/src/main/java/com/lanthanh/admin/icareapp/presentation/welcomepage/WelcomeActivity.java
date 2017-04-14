package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class WelcomeActivity extends BaseActivity {
    //public final static String TAG = RegisterActivity.class.getSimpleName();
    //TODO check used fields
    public static final String CHOOSE_FRAGMENT = ChooseFragment.class.getName();
    public static final String LOGIN_FRAGMENT = LogInFragment.class.getName();
    public static final String SIGNUP_FRAGMENT = SignUpFragment.class.getName();
    public static String CURRENT_FRAGMENT;
    public static final String CURRENT_FRAGMENT_KEY = "CurrentFragment";

    private WelcomeActivityPresenter registerActivityPresenter;
    private ChooseFragment chooseFragment;
    private LogInFragment logInFragment;
    private SignUpFragment signUpFragment;

    @BindView(R.id.toolbar) Toolbar toolBar;
    @BindView(R.id.progressbar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        //Init fragment
        chooseFragment = new ChooseFragment();
        logInFragment = new LogInFragment();
        signUpFragment = new SignUpFragment();
        registerActivityPresenter = new WelcomeActivityPresenter(logInFragment, signUpFragment, new WelcomeRepositoryImpl(this), new UserRepositoryImpl(this), new Interactor());

        //Set up for Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            //ChooseFragment as default -> hide ToolBar
            navigateFragment(ChooseFragment.class);
            CURRENT_FRAGMENT = CHOOSE_FRAGMENT;
        } else {
            CURRENT_FRAGMENT = savedInstanceState.getString(CURRENT_FRAGMENT_KEY, "");
            if (CURRENT_FRAGMENT.equals(LOGIN_FRAGMENT)) {
                navigateFragment(LogInFragment.class);
            } else if (CURRENT_FRAGMENT.equals(SIGNUP_FRAGMENT)) {
                navigateFragment(SignUpFragment.class);
            } else {
                navigateFragment(ChooseFragment.class);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerActivityPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        registerActivityPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerActivityPresenter.destroy();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_FRAGMENT_KEY, CURRENT_FRAGMENT);
    }

    public void navigateFragment(Class<? extends Fragment> fragmentClass) {
        hideSoftKeyboard();
        if (fragmentClass == ChooseFragment.class) {
            WelcomeActivity.CURRENT_FRAGMENT = WelcomeActivity.CHOOSE_FRAGMENT;
            showFragment(chooseFragment);
        }
        else if (fragmentClass == LogInFragment.class) {
            WelcomeActivity.CURRENT_FRAGMENT = WelcomeActivity.LOGIN_FRAGMENT;
            showFragment(logInFragment);
        }
        else if (fragmentClass == SignUpFragment.class) {
            WelcomeActivity.CURRENT_FRAGMENT = WelcomeActivity.SIGNUP_FRAGMENT;
            showFragment(signUpFragment);
        }
    }

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

    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    public void showFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    //Hide/show tool bar
    public void showToolbar(boolean shouldShow){
        if (shouldShow)
            toolBar.setVisibility(View.VISIBLE);
        else
            toolBar.setVisibility(View.GONE);
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
            navigateFragment(ChooseFragment.class);
        } else {
            backToHomeScreen();
        }
    }

    public WelcomeActivityPresenter getMainPresenter() {
        return registerActivityPresenter;
    }

    @Override
    public void refreshAfterLosingNetwork() {}
}

