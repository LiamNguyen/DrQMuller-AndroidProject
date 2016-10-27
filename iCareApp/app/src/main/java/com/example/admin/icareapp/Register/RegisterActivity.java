package com.example.admin.icareapp.Register;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.icareapp.MainActivity;
import com.example.admin.icareapp.R;
import com.example.admin.icareapp.UserInfo.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class RegisterActivity extends AppCompatActivity{
    private FragmentManager fragmentManager;
    private RegisterFragment chooseFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Initialize Fragment
        chooseFragment = new RegisterFragment();
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        //Get ChooseFragment for when loading Acitivity
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.wel_fragment_container, chooseFragment, chooseFragment.getClass().getName()).commit();
    }

    /*
     *Navigate to Sign In Screen
     */
    public void navigateToSignIn(){
        fragmentManager = getSupportFragmentManager();
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
        fragmentManager = getSupportFragmentManager();
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
        fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
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
     *Navigate to Booking Activity
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

}
