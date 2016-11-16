package com.example.admin.icareapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.admin.icareapp.BookingTab.BookingSelectFragment;
import com.example.admin.icareapp.Register.RegisterFragment;
import com.example.admin.icareapp.Register.SignInFragment;
import com.example.admin.icareapp.Register.SignUpFragment;
import com.example.admin.icareapp.UserInfo.ChangeEmailFragment;
import com.example.admin.icareapp.UserInfo.ContactFragment;
import com.example.admin.icareapp.UserInfo.DOBvsGenderFragment;
import com.example.admin.icareapp.UserInfo.NameAndLocationFragment;
import com.example.admin.icareapp.UserInfo.ValidateFragment;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12-Nov-16.
 */

public class ForTesting extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private FragmentManager fragmentManager;
    private int badgeCount;
    private Drawable cartIcon;

    //UserTab Fragment
    private RegisterFragment chooseFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private NameAndLocationFragment nameLocationFragment;
    private DOBvsGenderFragment dobGenderFragment;
    private ContactFragment contactFragment;
    private ValidateFragment validateFragment;
    private ChangeEmailFragment changeEmailFragment;
    private BottomNavigationView bottomNavigationView;

    //BookingTab Fragment
    private BookingSelectFragment bookingSelectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fortesting);

        badgeCount = 0;
        cartIcon = getResources().getDrawable(R.drawable.ic_shopping_cart, null);

        //Initialize Fragment
        chooseFragment = new RegisterFragment();
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        nameLocationFragment = new NameAndLocationFragment();
        dobGenderFragment = new DOBvsGenderFragment();
        contactFragment = new ContactFragment();
        validateFragment = new ValidateFragment();
        changeEmailFragment = new ChangeEmailFragment();
        bookingSelectFragment = new BookingSelectFragment();

        //Toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(R.string.app_name);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    /* =============================== TOOLBAR ===============================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        if (badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), cartIcon, ActionItemBadge.BadgeStyles.DARK_GREY, badgeCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        }

        return true;
    }


    /* =============================== USER TAB ===============================*/
    /* ------------- NOT LOGIN ------------- */
    //"Choose Fragment" by default
    //If user chose SIGN IN -> navigate to "Sign In Fragment"
    public void navigateToSignIn(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllUserTabVisibleFragments(fragmentTransaction);
        if (!signInFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, signInFragment, signInFragment.getClass().getName());
        }else{
            fragmentTransaction.show(signInFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide BottomNavigationView
        bottomNavigationView.setVisibility(View.GONE);
    }

    //If use chose SIGN UP -> navigate to "Sign Up Fragment"
    public void navigateToSignUp(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllUserTabVisibleFragments(fragmentTransaction);
        if (!signUpFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, signUpFragment, signUpFragment.getClass().getName());
        }else{
            fragmentTransaction.show(signUpFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();

        //Hide BottomNavigationView
        bottomNavigationView.setVisibility(View.GONE);
    }

    //After successfully SIGNED UP -> move to User Info fragments to fill in information
    //"NameAndLocation Fragment" by default
    public void navigateToNameAndLocation(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllUserTabVisibleFragments(fragmentTransaction);

        if (!dobGenderFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, nameLocationFragment, nameLocationFragment.getClass().getName());
        }else{
            fragmentTransaction.show(nameLocationFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    //After "NameAndLocation Fragment", move on to "DOBvsGender Fragment"
    public void navigateToDOBvsGender(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllUserTabVisibleFragments(fragmentTransaction);

        if (!dobGenderFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, dobGenderFragment, dobGenderFragment.getClass().getName());
        }else{
            fragmentTransaction.show(dobGenderFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    //After "DOBvsGender Fragment", move on to "Contact Fragment"
    public void navigateToContact(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllUserTabVisibleFragments(fragmentTransaction);

        if (!contactFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, contactFragment, contactFragment.getClass().getName());
        }else{
            fragmentTransaction.show(contactFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    //After "Contact Fragment", move on to "Validate Fragment"
    public void navigateToValidate(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllUserTabVisibleFragments(fragmentTransaction);

        if (!validateFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, validateFragment, validateFragment.getClass().getName());
        }else{
            fragmentTransaction.show(validateFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    //If user chose "Change Email", move to "Change Email Fragment"
    public void navigateToChangeEmail(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hideAllUserTabVisibleFragments(fragmentTransaction);

        if (!changeEmailFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, changeEmailFragment, changeEmailFragment.getClass().getName());
        }else{
            fragmentTransaction.show(changeEmailFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    //If user pressed back symbol, back to previous fragment
    public void navigateBack(){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        bottomNavigationView.setVisibility(View.VISIBLE);

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    //Get all visible fragment in User Tab
    private List<Fragment> getUserTabVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(8);

        // Add each visible fragment to the result IF VISIBLE
        //Add "Choose Fragment"
        if (chooseFragment.isVisible()) {
            result.add(chooseFragment);
        }
        //Add "Sign In Fragment"
        if (signInFragment.isVisible()) {
            result.add(signInFragment);
        }
        //Add "Sign Up Fragment"
        if (signUpFragment.isVisible()) {
            result.add(signUpFragment);
        }
        //Add "NameAndLocation Fragment"
        if (nameLocationFragment.isVisible()) {
            result.add(nameLocationFragment);
        }
        //Add "DOBvsGenderFragment"
        if (dobGenderFragment.isVisible()) {
            result.add(dobGenderFragment);
        }
        //Add "Contact Fragment"
        if (contactFragment.isVisible()) {
            result.add(contactFragment);
        }
        //Add "Validate Fragment"
        if (validateFragment.isVisible()) {
            result.add(chooseFragment);
        }
        //Add "Change Email Fragment"
        if (changeEmailFragment.isVisible()) {
            result.add(changeEmailFragment);
        }

        return result;
    }

    //Hide all visible fragment in User Tab
    private FragmentTransaction hideAllUserTabVisibleFragments(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getUserTabVisibleFragments()) {
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

    //Show BottomNavigationView when needed
    public void showBottomNavigationView(){
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    /* =============================== BOOKING TAB ===============================*/
    /* ------------- LOGIN ------------- */
    //Get all visible fragment in User Tab
    private List<Fragment> getBookingTabVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(2);

        // Add each visible fragment to the result IF VISIBLE
        //Add "Booking Select Fragment"
        if (bookingSelectFragment.isVisible()) {
            result.add(bookingSelectFragment);
        }
        return result;
    }

    //Hide all visible fragment in User Tab
    private FragmentTransaction hideAllBookingTabVisibleFragments(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getBookingTabVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }

        return fragmentTransaction;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;

        switch (item.getItemId()) {
            case R.id.action_news:
                break;
            case R.id.action_booking:
                fragmentTransaction = fragmentManager.beginTransaction();

                //Hide ALL fragments from OTHER TABS
                hideAllUserTabVisibleFragments(fragmentTransaction);

                //If user signed in, these are fragments that have to appear. "Booking Select Fragment" is default
                //Initialize Fragment

                if (!bookingSelectFragment.isAdded()){
                    fragmentTransaction.add(R.id.fragment_container, bookingSelectFragment, bookingSelectFragment.getClass().getName());
                }else{
                    fragmentTransaction.show(bookingSelectFragment);
                }

                fragmentTransaction.addToBackStack(null).commit();
                break;
            case R.id.action_user:
                fragmentTransaction = fragmentManager.beginTransaction();

                //Hide ALL fragments from OTHER TABS
                hideAllBookingTabVisibleFragments(fragmentTransaction);

                //If user haven't sign in, these are fragments that have to appear. "Choose Fragment" is default
                if (!chooseFragment.isAdded()){
                    fragmentTransaction.add(R.id.fragment_container, chooseFragment, chooseFragment.getClass().getName());
                }else{
                    fragmentTransaction.show(chooseFragment);
                }

                fragmentTransaction.addToBackStack(null).commit();
                break;
        }
        return false;
    }
}
