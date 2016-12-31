package com.lanthanh.admin.icareapp.Service;

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
import com.lanthanh.admin.icareapp.Register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 22-Nov-16.
 */
public class ResetPasswordActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private EmailForResetFragment emailForResetFragment;
    private ResetPasswordFragment resetPasswordFragment;
    private Toolbar toolBar;
    private NetworkController networkController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Init fragment
        emailForResetFragment = new EmailForResetFragment();
        resetPasswordFragment = new ResetPasswordFragment();
        fragmentManager = getSupportFragmentManager();

        //Init controllers
        networkController = new NetworkController(this);

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        //Get ChooseFragment for when loading Acitivity
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.resetpw_fragment_container, emailForResetFragment, emailForResetFragment.getClass().getName()).commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            if (b != null) {
                if (!b.getString("login_id", "").isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("login_id", b.getString("login_id", ""));
                    resetPasswordFragment.setArguments(bundle);
                    navigateToReset();
                } else {
                    System.out.println("Problem in DeepLinkActivity");
                    navigateToRegister();
                }
            }else {
                navigateToEmail();
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
                    navigateToRegister();
//                else
//                    navigateBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
   *Navigate to Sign Up Screen
   */
    public void navigateToEmail(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllVisibleFragment(fragmentTransaction);
        if (!emailForResetFragment.isAdded()){
            fragmentTransaction.add(R.id.resetpw_fragment_container, emailForResetFragment, emailForResetFragment.getClass().getName());
        }else{
            fragmentTransaction.show(emailForResetFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
    *Navigate to Sign Up Screen
    */
    public void navigateToReset(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllVisibleFragment(fragmentTransaction);
        if (!resetPasswordFragment.isAdded()){
            fragmentTransaction.add(R.id.resetpw_fragment_container, resetPasswordFragment, resetPasswordFragment.getClass().getName());
        }else{
            fragmentTransaction.show(resetPasswordFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
    *Navigate to last fragment
    */
    public void navigateBack(){
        fragmentManager.popBackStack();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    public void navigateToRegister(){
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
        finish();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    public void navigateToRegisterAfterReset(int n){
        Intent toRegister = new Intent(this, RegisterActivity.class);
        toRegister.putExtra("fromResetPw", n);
        startActivity(toRegister);
        finish();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
    *Get all visible fragments
    */
    private List<Fragment> getVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(2);

        // Add each visible fragment to the result
        if (emailForResetFragment.isVisible()) {
            result.add(emailForResetFragment);
        }
        if (resetPasswordFragment.isVisible()) {
            result.add(resetPasswordFragment);
        }

        return result;
    }

    /*
    *Hide all visible fragments
    */
    private FragmentTransaction hideAllVisibleFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }

        return fragmentTransaction;
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
            navigateToRegister();
//        else
//            navigateBack();
    }
}
