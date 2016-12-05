package com.lanthanh.admin.icareapp.UserInfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.Register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class UserInfoActivity extends AppCompatActivity{
    private FragmentManager fragmentManager;
    private NameAndAddressFragment nameLocationFragment;
    private DOBvsGenderFragment dobGenderFragment;
    private ContactFragment contactFragment;
    private ValidateFragment validateFragment;
    private ChangeEmailFragment changeEmailFragment;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        //Init Fragment
        nameLocationFragment = new NameAndAddressFragment();
        dobGenderFragment = new DOBvsGenderFragment();
        contactFragment = new ContactFragment();
        validateFragment = new ValidateFragment();
        changeEmailFragment = new ChangeEmailFragment();

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ui_fragment_container, nameLocationFragment, nameLocationFragment.getClass().getName()).commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
        if (!sharedPref.getString("step", "0").equals("0"))
            navigateToValidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (nameLocationFragment.isVisible())
                    navigateToRegister();
                else
                    navigateBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     *Navigate to DOBvsGender Screen
     */
    public void navigateToDOBvsGender(){
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

        toolBar.setVisibility(View.GONE);
    }

    public void navigateToChangeEmail(){
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
        fragmentManager.popBackStack();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    /*
     *Navigate to Register Activity
     */
    public void navigateToRegister(){
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
        finish();

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
        List<Fragment> result = new ArrayList<>(5);

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
            result.add(validateFragment);
        }
        if (changeEmailFragment.isVisible()){
            result.add(changeEmailFragment);
        }

        return result;
    }

    public FragmentTransaction hideAllVisibleFragments(FragmentTransaction fragmentTransaction){
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

    //Hide/show tool bar
    public void isToolBarHidden(boolean hidden){
        if (hidden)
            toolBar.setVisibility(View.GONE);
        else
            toolBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (nameLocationFragment.isVisible())
            navigateToRegister();
        else
            navigateBack();
    }
}
