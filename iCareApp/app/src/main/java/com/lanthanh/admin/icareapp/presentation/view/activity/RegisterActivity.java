package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.RegisterActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.RegisterActivityPresenterImpl;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import java.util.List;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterActivityPresenter.View{
    public final static String TAG = RegisterActivity.class.getSimpleName();
    public final static String EXTRA_USERNAME = "username";
    public final static String EXTRA_UISTEP = "uistep";
    public final static String LOGIN_STATUS = "loginstatus";
    public final static int LOGGED_IN = 1;
    public final static int NOT_LOGGED_IN = 0;
    public final static int CHOOSE = 0;
    public final static int LOG_IN = 1;
    public final static int SIGN_UP = 2;
    private RegisterActivityPresenter registerActivityPresenter;
    private Toolbar toolBar;
    private ProgressBar progressBar;
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        //Init view
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //Set up for Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    protected void onResume() {
        super.onResume();

        //networkController.registerNetworkReceiver();

        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            if (b != null) {
                if (b.containsKey(DeepLinkActivity.TAG)) {
                    Bundle bundle = b.getBundle(DeepLinkActivity.TAG);
                    if (bundle != null)
                        registerActivityPresenter.updateVerifyAcc(bundle.getString("cus_id"));
                    else
                        Log.e(TAG, "No data received from DeepLinkActivity");
                }else if (b.containsKey(ResetPasswordActivity.TAG)){
                    Bundle bundle = b.getBundle(ResetPasswordActivity.TAG);
                    if (bundle != null){
                        int result = bundle.getInt("result");

                        if (result == ResetPasswordActivity.SUCCESS)
                            showAlertDialog(R.string.reset_success);
                        else if (result == ResetPasswordActivity.FAIL)
                            showAlertDialog(R.string.reset_fail);
                    }else
                        Log.e(TAG, "No data received from ResetPasswordActivity");
                }
            }else{
                Log.i(TAG, "No data received from intent");
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
                registerActivityPresenter.onBackPressed();
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
        hideProgress();
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
        hideProgress();
        Intent toActivity = new Intent(this, activityClass);
        hideSoftKeyboard();
        startActivity(toActivity);
        finish();
    }

    @Override
    public void navigateActivity(Class activityClass, Bundle extras) {
        hideProgress();
        Intent toActivity = new Intent(this, activityClass);
        hideSoftKeyboard();
        if (activityClass == UserInfoActivity.class || activityClass == MainActivity.class) {
            toActivity.putExtra(TAG, extras);
        }
        startActivity(toActivity);
        finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    @Override
    public void showAlertDialog(int id) {
        new AlertDialog.Builder(this)
                .setMessage(getString(id))
                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
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
        hideProgress();
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
}
