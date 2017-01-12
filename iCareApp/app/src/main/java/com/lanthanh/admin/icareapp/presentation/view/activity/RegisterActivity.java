package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.UserManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.RegisterActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.ChooseFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.SignInFragment;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.SignUpFragment;
import com.lanthanh.admin.icareapp.domain.model.ModelURL;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterActivityPresenter.View{
    public final static int CHOOSE = 0;
    public final static int LOG_IN = 1;
    public final static int SIGN_UP = 2;
    private RegisterActivityPresenter registerActivityPresenter;
    private Toolbar toolBar;
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        //ChooseFragment as default -> hide ToolBar
        registerActivityPresenter.navigateFragment(CHOOSE);
        toolBar.setVisibility(View.GONE);
    }

    public void init(){
        registerActivityPresenter = new RegisterActivityPresenterImpl(getSharedPreferences("content", Context.MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                    getSupportFragmentManager(), new CustomerManagerImpl(iCareApiImpl.getAPI()));
        //Init controllers
        networkController = new NetworkController(this);
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
//        if (getIntent() != null) {
//            Intent intent = getIntent();
//            Bundle b = intent.getExtras();
//            if (b != null) {
//                if (!b.getString("cus_id", "").isEmpty()) {
//                    aController.setRequestData(this, this, ModelURL.UPDATE_VERIFYACC.getUrl(MainActivity.isUAT), "cus_id=" + intent.getStringExtra("cus_id"));
//                }else if (b.getInt("fromResetPw", 0) == 0){
//                    new AlertDialog.Builder(this)
//                            .setMessage(getString(R.string.reset_fail))
//                            .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }).setCancelable(false).show();
//                }else if (b.getInt("fromResetPw", 0) == 1){
//                    new AlertDialog.Builder(this)
//                            .setMessage(getString(R.string.reset_success))
//                            .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }).setCancelable(false).show();
//                }else {
//                    System.out.println("Problem in DeepLinkActivity or ResetPw");
//                }
//            }
//        }
//        setIntent(null);
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
                registerActivityPresenter.navigateBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getStringResource(int id) {
        return getString(id);
    }

    @Override
    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    @Override
    public void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        //Hide all current visible fragment
        hideFragments(fragmentTransaction, visibleFrags);

        if (!f.isAdded()){
            fragmentTransaction.add(R.id.wel_fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
        hideSoftKeyboard();
    }

    @Override
    public void navigateActivity(Class activityClass) {
        Intent toActivity = new Intent(this, activityClass);
        if (activityClass == MainActivity.class)
            toActivity.putExtra("isSignedIn", 1);
        startActivity(toActivity);
        finish();
        hideSoftKeyboard();
    }

    @Override
    public void navigateActivity(Class activityClass, String extra) {
        Intent toActivity = new Intent(this, activityClass);
        if (activityClass == UserInfoActivity.class)
            toActivity.putExtra("fromRegisterActivity", extra);
        startActivity(toActivity);
        finish();
        hideSoftKeyboard();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    //Hide SoftKeyBoard when needed
    @Override
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
    public void backToHomeScreen() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    public void onBackPressed() {
        registerActivityPresenter.onBackPressed();
    }

    @Override
    public RegisterActivityPresenter getMainPresenter() {
        return registerActivityPresenter;
    }

    //    @Override
//    public void update(Object o) {
//        JSONObject status = (JSONObject) o;
//
//        if (status == null) {
//            System.out.println("ERROR IN PHP FILE");
//            return;
//        }
//
//        try{
//            if (status.has("Update_VerifyAcc")) {
//                //String result = status.getString("Select_ToAuthenticate");
//                String response = status.getString("Update_VerifyAcc");
//                if (!response.isEmpty()){
//                    if (response.equals("QueryFailed")){
//                        new AlertDialog.Builder(this)
//                                .setMessage(getString(R.string.verify_fail))
//                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).setCancelable(false).show();
//                    }else if (response.equals("Updated")){
//                        new AlertDialog.Builder(this)
//                                .setMessage(getString(R.string.verify_success))
//                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).setCancelable(false).show();
//                    }else if (response.equals("Failed")){
//                        new AlertDialog.Builder(this)
//                                .setMessage(getString(R.string.verify_already))
//                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).setCancelable(false).show();
//                    }
//                }
//            }
//        }catch (JSONException je){
//            je.printStackTrace();
//        }

//    }
}
