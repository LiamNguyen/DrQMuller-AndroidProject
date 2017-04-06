package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;

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

    @BindView(R.id.toolbar) Toolbar toolBar;
    @BindView(R.id.progressbar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        init();

        //Set up for Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            //ChooseFragment as default -> hide ToolBar
            registerActivityPresenter.navigateFragment(ChooseFragment.class);
            CURRENT_FRAGMENT = CHOOSE_FRAGMENT;
        } else {
            CURRENT_FRAGMENT = savedInstanceState.getString(CURRENT_FRAGMENT_KEY, "");
            if (CURRENT_FRAGMENT.equals(LOGIN_FRAGMENT)) {
                registerActivityPresenter.navigateFragment(LogInFragment.class);
            } else if (CURRENT_FRAGMENT.equals(SIGNUP_FRAGMENT)) {
                registerActivityPresenter.navigateFragment(SignUpFragment.class);
            } else {
                registerActivityPresenter.navigateFragment(ChooseFragment.class);
            }
        }

    }

    public void init(){
        registerActivityPresenter = new WelcomeActivityPresenter(this);
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
                registerActivityPresenter.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_FRAGMENT_KEY, CURRENT_FRAGMENT);
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
        registerActivityPresenter.onBackPressed();
    }

    public WelcomeActivityPresenter getMainPresenter() {
        return registerActivityPresenter;
    }
}

