package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.SendEmailManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.ResetPasswordActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.ResetPasswordActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword.EmailForResetFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword.ResetPasswordFragment;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 22-Nov-16.
 */
public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordActivityPresenter.View {
    public static final int EMAIL_FOR_RESET = 0;
    public static final int RESET_PW = 1;
    private FragmentManager fragmentManager;
    private EmailForResetFragment emailForResetFragment;
    private ResetPasswordFragment resetPasswordFragment;
    private ResetPasswordActivityPresenter resetPasswordActivityPresenter;
    private NetworkController networkController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        //Get ChooseFragment for when loading Acitivity
        resetPasswordActivityPresenter.navigateTab(EMAIL_FOR_RESET);
    }

    public void init(){
        resetPasswordActivityPresenter = new ResetPasswordActivityPresenterImpl(getSharedPreferences("content", Context.MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                this, getSupportFragmentManager(), new SendEmailManagerImpl(iCareApiImpl.getAPI()), new CustomerManagerImpl(iCareApiImpl.getAPI()));
        //Init controllers
        networkController = new NetworkController(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            if (b != null) {
                if (!b.getString("login_id", "").isEmpty()) {
                    resetPasswordActivityPresenter.setResetPWFragmentArguments(b);
                    resetPasswordActivityPresenter.navigateTab(RESET_PW);
                } else {
                    System.out.println("Problem in DeepLinkActivity");
                    navigateToRegisterActivity(-1);
                }
            }else {
                resetPasswordActivityPresenter.navigateTab(EMAIL_FOR_RESET);
            }
        }
        setIntent(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkController.registerNetworkReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkController.unregisterNetworkReceiver();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                if (emailForResetFragment.isVisible())
                    navigateToRegisterActivity(-1);
//                else
//                    navigateBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        //Hide all current visible fragment
        hideFragments(fragmentTransaction, visibleFrags);

        if (!f.isAdded()){
            fragmentTransaction.add(R.id.resetpw_fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showError(String message) {

    }

//    /*
//    *Navigate to last fragment
//    */
//    public void navigateBack(){
//        fragmentManager.popBackStack();
//
//        //Hide soft keyboard if it is open
//        hideSoftKeyboard();
//    }

    @Override
    public void navigateToRegisterActivity(int extra){
        Intent toRegister = new Intent(this, RegisterActivity.class);
        if (extra != -1){
            toRegister.putExtra("fromResetPw", extra);
        }
        startActivity(toRegister);
        finish();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    @Override
    public ResetPasswordActivityPresenter getMainPresenter() {
        return resetPasswordActivityPresenter;
    }

    //Hide SoftKeyBoard when needed
    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
//        if (emailForResetFragment.isVisible())
            navigateToRegisterActivity(-1);
//        else
//            navigateBack();
    }
}
