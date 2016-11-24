package com.lanthanh.admin.icareapp.Register;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.Service.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.UserInfo.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 17-Oct-16.
 */

@DeepLink("icare://210.211.109.180/drmuller/verify")
public class RegisterActivity extends AppCompatActivity{
    private FragmentManager fragmentManager;
    private ChooseFragment chooseFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize Fragment
        chooseFragment = new ChooseFragment();
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);

        //ChooseFragment as default
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.wel_fragment_container, chooseFragment, chooseFragment.getClass().getName()).commit();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                if (fragmentManager.findFragmentByTag(emailForRe.getClass().getName()).isVisible())
//                    NavUtils.navigateUpFromSameTask(this);
//                else
//                    navigateBack();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /*
     *Navigate to Sign In Screen
     */
    public void navigateToSignIn(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllVisibleFragment(fragmentTransaction);
        if (!signInFragment.isAdded()){
            fragmentTransaction.add(R.id.wel_fragment_container, signInFragment, signInFragment.getClass().getName());
        }else{
            fragmentTransaction.show(signInFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    /*
     *Navigate to Sign Up Screen
     */
    public void navigateToSignUp(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllVisibleFragment(fragmentTransaction);
        if (!signUpFragment.isAdded()){
            fragmentTransaction.add(R.id.wel_fragment_container, signUpFragment, signUpFragment.getClass().getName());
        }else{
            fragmentTransaction.show(signUpFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    /*
     *Navigate to last fragment
     */
    public void navigateBack(){
        fragmentManager.popBackStack();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
     *Navigate to UserInfo Activity
     */
    public void navigateToUserInfo(){
        Intent toUserInfo = new Intent(this, UserInfoActivity.class);
        startActivity(toUserInfo);
        finish();
    }

    /*
     *Navigate to UserInfo Activity
     */
    public void navigateToResetPW(){
        Intent toReset = new Intent(this, ResetPasswordActivity.class);
        startActivity(toReset);
        finish();
    }

    /*
     *Navigate to Booking Tab in MainActivity
     */
    public void navigateToBookingActivity(){
        Intent toBooking = new Intent(this, MainActivity.class);
        startActivity(toBooking);
        finish();
    }

    /*
     *Get all visible fragments
     */
    private List<Fragment> getVisibleFragments(){
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
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
