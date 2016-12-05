package com.lanthanh.admin.icareapp.Register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.Service.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.UserInfo.UserInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class RegisterActivity extends AppCompatActivity implements DatabaseObserver{
    private FragmentManager fragmentManager;
    private ChooseFragment chooseFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private Toolbar toolBar;
    private Controller aController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        aController = Controller.getInstance();

        //Initialize Fragment
        chooseFragment = new ChooseFragment();
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        //ChooseFragment as default -> hide ToolBar
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.wel_fragment_container, chooseFragment, chooseFragment.getClass().getName()).commit();
        toolBar.setVisibility(View.GONE);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            if (b != null) {
                if (!b.getString("cus_id", "").isEmpty()) {
                    aController.setRequestData(this, this, ModelURL.UPDATE_VERIFYACC.getUrl(MainActivity.isUAT), "cus_id=" + intent.getStringExtra("cus_id"));
                } else {
                    System.out.println("Problem in DeepLinkActivity");
                }
            }
        }
        setIntent(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
        hideSoftKeyboard();
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
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra("isSignedIn", 1);
        startActivity(toMain);
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

    //Hide/show tool bar
    public void isToolBarHidden(boolean hidden){
        if (hidden)
            toolBar.setVisibility(View.GONE);
        else
            toolBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (chooseFragment.isVisible()){
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }else
            navigateBack();
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        if (status == null) {
            System.out.println("ERROR IN PHP FILE");
            return;
        }

        try{
            if (status.has("Update_VerifyAcc")) {
                //String result = status.getString("Select_ToAuthenticate");
                String response = status.getString("Update_VerifyAcc");
                if (!response.isEmpty()){
                    if (response.equals("QueryFailed")){
                        new AlertDialog.Builder(this)
                                .setMessage(getString(R.string.verify_fail))
                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setCancelable(false).show();
                    }else if (response.equals("Updated")){
                        new AlertDialog.Builder(this)
                                .setMessage(getString(R.string.verify_success))
                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setCancelable(false).show();
                    }else if (response.equals("Failed")){
                        new AlertDialog.Builder(this)
                                .setMessage(getString(R.string.verify_already))
                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setCancelable(false).show();
                    }
                }
            }
        }catch (JSONException je){
            je.printStackTrace();
        }

    }
}
