package com.lanthanh.admin.icareapp.presentation.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab.AppointmentFragment;
import com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab.DefaultAppointmentFragment;
import com.lanthanh.admin.icareapp.presentation.homepage.usertab.UserFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 12-Nov-16.
 */

public class MainActivity extends BaseActivity{
    public final static int NEWSTAB = 0;
    public final static int APPOINTMENTTAB = 1;//TODO check if these are still necessary
    public final static int USERTAB = 2;

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.progressbar) ProgressBar progressBar;

    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

        //Bottom Navigation View
        bottomNavigationView.setOnNavigationItemSelectedListener(
            menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_news:
                        break;
                    case R.id.action_booking:
                        bottomNavigationView.getMenu().getItem(USERTAB).setChecked(false);
                        bottomNavigationView.getMenu().getItem(APPOINTMENTTAB).setChecked(true);
                        mainActivityPresenter.navigateFragment(AppointmentFragment.class);
                        break;
                    case R.id.action_user:
                        bottomNavigationView.getMenu().getItem(APPOINTMENTTAB).setChecked(false);
                        bottomNavigationView.getMenu().getItem(USERTAB).setChecked(true);
                        mainActivityPresenter.navigateFragment(UserFragment.class);
                        break;
                    default:
                        break;
                }
                return false;
            }
        );
        bottomNavigationView.getMenu().getItem(NEWSTAB).setChecked(false);
        bottomNavigationView.getMenu().getItem(APPOINTMENTTAB).setChecked(true);
        mainActivityPresenter.navigateFragment(DefaultAppointmentFragment.class);//TODO this is only for testing, plz change for appropriate set up
    }

    public void init(){
        mainActivityPresenter = new MainActivityPresenter(this);
    }

    public MainActivityPresenter getMainPresenter(){
        return mainActivityPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        //networkController.registerNetworkReceiver();
//        mMainPresenter.resume();
//
//        Intent i = getIntent();
//        if (i != null) {
//            Bundle b = i.getExtras();
//
//            if (b == null) {
//                //Check user's privilege to use the app. If false (NOT log in or NOT activate account), return to register
//                if (!mMainPresenter.checkPrivilege()) {
//                    mMainPresenter.navigateToRegisterActivity();
//                } else {
//                    showCurrentTab();
//                }
//            } else {
//                if (b.containsKey(RegisterActivity.TAG)) {
//                    Bundle bundle = b.getBundle(RegisterActivity.TAG);
//                    if (bundle != null) {
//                        int n = bundle.getInt(RegisterActivity.LOGIN_STATUS);
//                        if (n == RegisterActivity.LOGGED_IN) {
//                            onNavigationItemSelected(bottomNavigationView.getMenu().getItem(APPOINTMENTTAB));
//                        }
//                    }
//                } else if (b.containsKey(UserDetailsActivity.TAG)) {
//                    if (b.getBoolean(UserDetailsActivity.TAG))
//                        onNavigationItemSelected(bottomNavigationView.getMenu().getItem(USERTAB));
//                } else if (b.containsKey(ConfirmBookingActivity.TAG)) {
//                    int m = b.getInt(ConfirmBookingActivity.TAG, 0);
//                    if (m == ConfirmBookingActivity.CONFIRMED) {
//                        new AlertDialog.Builder(this)
//                                .setMessage(getString(R.string.booking_success))
//                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).setCancelable(false).show();
//                    } else {
//                        new AlertDialog.Builder(this)
//                                .setMessage(getString(R.string.booking_fail))
//                                .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).setCancelable(false).show();
//                    }
//                    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(APPOINTMENTTAB));
//                }else{
//                    //Check user's privilege to use the app. If false (NOT log in or NOT activate account), return to register
//                    if (!mMainPresenter.checkPrivilege()) {
//                        mMainPresenter.navigateToRegisterActivity();
//                    } else {
//                        showCurrentTab();
//                    }
//                }
//            }
//        }
//        setIntent(null);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        hideProgress();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
    }

    public int getSelectedTab() {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked()) {
                return i;
            }
        }
        return 1;
    }

//    @Override
//    public void showCurrentTab() {
//        int selected = getSelectedTab();
//        onNavigationItemSelected(bottomNavigationView.getMenu().getItem(selected));
//    }

    //Call in case losing network and then connected again
    public void refreshAfterNetworkConnected(){
        this.onPostResume();
    }
}
