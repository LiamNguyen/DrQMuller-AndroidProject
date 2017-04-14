package com.lanthanh.admin.icareapp.presentation.resetpasswordpage;

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
import android.widget.ProgressBar;

import com.lanthanh.admin.icareapp.presentation.broadcastreceivers.NetworkBroadcastReceiver;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivity;

import java.util.List;

/**
 * Created by ADMIN on 22-Nov-16.
 */
public class ResetPasswordActivity extends AppCompatActivity {
    public static final String TAG = ResetPasswordActivity.class.getSimpleName();
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int USERNAME_FOR_RESET = 0;
    public static final int RESET_PW = 1;
    //private ResetPasswordActivityPresenter resetPasswordActivityPresenter;
    private ProgressBar progressBar;
    private NetworkBroadcastReceiver networkBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();

        //Init view
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //Set up Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get ChooseFragment for when loading Acitivity

    }

    public void init(){
//        resetPasswordActivityPresenter = new ResetPasswordActivityPresenterImpl(getSharedPreferences("content", Context.MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
//                this, getSupportFragmentManager(), new SendEmailManagerImpl(iCareApiImpl.getAPI()), new CustomerManagerImpl(iCareApiImpl.getAPI()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //networkController.registerNetworkReceiver();
        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            if (b != null) {
//                if (b.containsKey(DeepLinkActivity.TAG)) {
//                    Bundle bundle = b.getBundle(DeepLinkActivity.TAG);
//                    if (bundle != null){
//                        resetPasswordActivityPresenter.setResetPWFragmentArguments(bundle);
//                        resetPasswordActivityPresenter.navigateTab(RESET_PW);
//                    }else{
//                        Log.e(TAG, "No data received from DeepLinkActivity");
//                    }
//                }
            }else{
                //resetPasswordActivityPresenter.navigateTab(USERNAME_FOR_RESET);
            }
        }
        setIntent(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //networkController.unregisterNetworkReceiver();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //resetPasswordActivityPresenter.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags) {
        hideProgress();
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

    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void navigateToRegisterActivity(int result){
        hideProgress();
        Intent toRegister = new Intent(this, WelcomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("result", result);
        toRegister.putExtra(TAG, bundle);
        startActivity(toRegister);
        finish();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    public void navigateToRegisterActivity() {
        hideProgress();
        Intent toRegister = new Intent(this, WelcomeActivity.class);
        startActivity(toRegister);
        finish();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }



    //Hide SoftKeyBoard when needed
    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    @Override
//    public void onBackPressed() {
//        resetPasswordActivityPresenter.onBackPressed();
//    }
}
