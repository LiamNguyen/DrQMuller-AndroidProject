package com.lanthanh.admin.icareapp.presentation.welcomepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.view.activity.BaseActivity;
import com.lanthanh.admin.icareapp.presentation.view.fragment.register.ChooseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class WelcomeActivity extends BaseActivity {
    //public final static String TAG = RegisterActivity.class.getSimpleName();
    public final static String EXTRA_ID = "id";
    public final static String EXTRA_UISTEP = "uistep";
    public final static String LOGIN_STATUS = "loginstatus";
    public final static int LOGGED_IN = 1;
    public final static int NOT_LOGGED_IN = 0;
    public final static int CHOOSE = 0;
    public final static int LOG_IN = 1;
    public final static int SIGN_UP = 2;
    private WelcomeActivityPresenter registerActivityPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        init();

        //Set up for Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            //ChooseFragment as default -> hide ToolBar
            registerActivityPresenter.navigateFragment(ChooseFragment.class);
        }

    }

    public void init(){
        registerActivityPresenter = new WelcomeActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //networkController.registerNetworkReceiver();
//
//        if (getIntent() != null) {
//            Intent intent = getIntent();
//            Bundle b = intent.getExtras();
//            if (b != null) {
//                if (b.containsKey(DeepLinkActivity.TAG)) {
//                    Bundle bundle = b.getBundle(DeepLinkActivity.TAG);
//                    if (bundle != null)
//                        //registerActivityPresenter.verifyAccount(bundle.getString("cus_id"));
//                        System.out.println("yaa");
//                    else
//                        Log.e(TAG, "No data received from DeepLinkActivity");
//                }else if (b.containsKey(ResetPasswordActivity.TAG)){
//                    Bundle bundle = b.getBundle(ResetPasswordActivity.TAG);
//                    if (bundle != null){
//                        int result = bundle.getInt("result");
//
//                        if (result == ResetPasswordActivity.SUCCESS)
//                            showAlertDialog(R.string.reset_success);
//                        else if (result == ResetPasswordActivity.FAIL)
//                            showAlertDialog(R.string.reset_fail);
//                    }else
//                        Log.e(TAG, "No data received from ResetPasswordActivity");
//                }
//            }else{
//                Log.i(TAG, "No data received from intent");
//            }
//        }
//        setIntent(null);
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
                //registerActivityPresenter.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getStringResource(int id) {
        return getString(id);
    }



//    public void navigateActivity(Class activityClass) {
//        hideProgress();
//        Intent toActivity = new Intent(this, activityClass);
//        hideSoftKeyboard();
//        startActivity(toActivity);
//        finish();
//    }
//
//
//    public void navigateActivity(Class activityClass, Bundle extras) {
//        hideProgress();
//        Intent toActivity = new Intent(this, activityClass);
//        hideSoftKeyboard();
//        if (activityClass == UserInfoActivity.class || activityClass == MainActivity.class) {
//            toActivity.putExtra(TAG, extras);
//        }
//        startActivity(toActivity);
//        finish();
//    }


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    public void showError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    public void showAlertDialog(int id) {
        new AlertDialog.Builder(this)
                .setMessage(getString(id))
                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }

    //Hide/show tool bar
    public void showToolbar(boolean shouldShow){
        if (shouldShow)
            toolBar.setVisibility(View.VISIBLE);
        else
            toolBar.setVisibility(View.GONE);
    }

    public void backToHomeScreen() {
        hideProgress();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
//
//    @Override
//    public void onBackPressed() {
//        registerActivityPresenter.onBackPressed();
//    }

    public WelcomeActivityPresenter getMainPresenter() {
        return registerActivityPresenter;
    }
}

