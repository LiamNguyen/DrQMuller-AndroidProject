package com.example.admin.icareapp.UserInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.admin.icareapp.MainActivity;
import com.example.admin.icareapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by ADMIN on 22-Oct-16.
 */

public class UserInfoActivity extends AppCompatActivity{
    private FragmentManager fragmentManager;
    private NameAndLocationFragment nameLocationFragment;
    private DOBvsGenderFragment dobGenderFragment;
    private ContactFragment contactFragment;
    private ValidateFragment validateFragment;
    private ChangeEmailFragment changeEmailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        //Init Fragment
        nameLocationFragment = new NameAndLocationFragment();
        dobGenderFragment = new DOBvsGenderFragment();
        contactFragment = new ContactFragment();
        validateFragment = new ValidateFragment();
        changeEmailFragment = new ChangeEmailFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ui_fragment_container, nameLocationFragment, nameLocationFragment.getClass().getName()).commit();
    }

    /*
     *Navigate to DOBvsGender Screen
     */
    public void navigateToDOBvsGender(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllVisibleFragments(fragmentTransaction);

        if (!dobGenderFragment.isAdded()){
            fragmentTransaction.add(R.id.ui_fragment_container, dobGenderFragment, dobGenderFragment.getClass().getName());
        }else{
            fragmentTransaction.show(dobGenderFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
     *Navigate to Contact Screen
     */
    public void navigateToContact(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllVisibleFragments(fragmentTransaction);

        if (!contactFragment.isAdded()){
            fragmentTransaction.add(R.id.ui_fragment_container, contactFragment, contactFragment.getClass().getName());
        }else{
            fragmentTransaction.show(contactFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    public void navigateToValidate(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllVisibleFragments(fragmentTransaction);

        if (!validateFragment.isAdded()){
            fragmentTransaction.add(R.id.ui_fragment_container, validateFragment, validateFragment.getClass().getName());
        }else{
            fragmentTransaction.show(validateFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    public void navigateToChangeEmail(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllVisibleFragments(fragmentTransaction);

        if (!changeEmailFragment.isAdded()){
            fragmentTransaction.add(R.id.ui_fragment_container, changeEmailFragment, changeEmailFragment.getClass().getName());
        }else{
            fragmentTransaction.show(changeEmailFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
     *Navigate back
     */
    public void navigateBack(){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
     *Navigate to Booking Activity
     */
    public void navigateToBooking(){
        Intent toBooking = new Intent(this, MainActivity.class);
        startActivity(toBooking);
        finish();
    }

    public List<Fragment> getVisibleFragments(){
        //Create array of 3 for 3 fragments
        List<Fragment> result = new ArrayList<>(3);

        //Add each visble fragments
        if (nameLocationFragment.isVisible()){
            result.add(nameLocationFragment);
        }
        if (dobGenderFragment.isVisible()){
            result.add(dobGenderFragment);
        }
        if (contactFragment.isVisible()){
            result.add(contactFragment);
        }
        if (validateFragment.isVisible()){
            result.add(contactFragment);
        }
        if (changeEmailFragment.isVisible()){
            result.add(contactFragment);
        }

        return result;
    }

    public FragmentTransaction hideAllVisibleFragments(FragmentTransaction fragmentTransaction){
        for (Fragment fragment : getVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }

        return fragmentTransaction;
    }

    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
