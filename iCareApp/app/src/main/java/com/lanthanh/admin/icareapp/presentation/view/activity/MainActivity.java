package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.SendEmailManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.MainActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.adapter.ListPopupWindowAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12-Nov-16.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainActivityPresenter.View{
    public final static boolean isUAT = false;
    public final static int NEWSTAB = 0;
    public final static int APPOINTMENTTAB = 1;
    public final static int USERTAB = 2;

    private MainActivityPresenter mMainPresenter;
    private BottomNavigationView bottomNavigationView;

    //Controller
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //Bottom Navigation View
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(NEWSTAB).setChecked(false);
        bottomNavigationView.getMenu().getItem(APPOINTMENTTAB).setChecked(true);
    }

    public void init(){
        mMainPresenter = new MainActivityPresenterImpl(
                getSharedPreferences("content", Context.MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                getSupportFragmentManager(), new AppointmentManagerImpl(iCareApiImpl.getAPI()), new CustomerManagerImpl(iCareApiImpl.getAPI()));

        //Init controllers
        networkController = new NetworkController(this);
    }

    public MainActivityPresenter getMainPresenter(){
        return mMainPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //networkController.registerNetworkReceiver();

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b == null){
            //Check user's privilege to use the app. If false (NOT log in or NOT activate account), return to register
            if (!mMainPresenter.checkPrivilege()) {
                mMainPresenter.navigateToRegisterActivity();
            }else {
                int selected = getSelectedTab();
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(selected));
            }
        }else {
            if (b.containsKey(RegisterActivity.TAG)) {
                Bundle bundle = b.getBundle(RegisterActivity.TAG);
                if (bundle != null) {
                    int n = bundle.getInt(RegisterActivity.LOGIN_STATUS);
                    if (n == RegisterActivity.LOGGED_IN) {
                        onNavigationItemSelected(bottomNavigationView.getMenu().getItem(APPOINTMENTTAB));
                    }
                }
            }else if (b.containsKey(UserDetailsActivity.TAG)){
                if (b.getBoolean(UserDetailsActivity.TAG))
                    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(USERTAB));
            }else if (b.containsKey(ConfirmBookingActivity.TAG)) {
                int m = b.getInt(ConfirmBookingActivity.TAG, 0);
                if (m == ConfirmBookingActivity.CONFIRMED) {
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.booking_success))
                            .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setCancelable(false).show();
                }else{
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.booking_fail))
                            .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setCancelable(false).show();
                }
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(APPOINTMENTTAB));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //releaseCartWhenReselect();
        //networkController.unregisterNetworkReceiver();
    }

    @Override
    protected void onDestroy() {
        //releaseCartWhenReselect();
        super.onDestroy();
    }

    @Override
    public void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        //Hide all current visible fragment
        hideFragments(fragmentTransaction, visibleFrags);

        if (!f.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    @Override
    public void navigateActivity(Class activityClass) {
        Intent toActivity = new Intent(this, activityClass);
        startActivity(toActivity);
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
        toast.setGravity(Gravity.BOTTOM, 0, 110);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    /* =============================== BOTTOM NAVIGATION VIEW ===============================*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_news:
                break;
            case R.id.action_booking:
                bottomNavigationView.getMenu().getItem(USERTAB).setChecked(false);
                bottomNavigationView.getMenu().getItem(APPOINTMENTTAB).setChecked(true);
                mMainPresenter.navigateTab(APPOINTMENTTAB);
                break;
            case R.id.action_user:
                bottomNavigationView.getMenu().getItem(APPOINTMENTTAB).setChecked(false);
                bottomNavigationView.getMenu().getItem(USERTAB).setChecked(true);
                mMainPresenter.navigateTab(USERTAB);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public int getSelectedTab() {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked()) {
                return i;
            }
        }
        return 1;
    }

    //Call in case losing network and then connected again
    public void refreshAfterNetworkConnected(){
        this.onPostResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("CONCCCCCCACCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        return super.onTouchEvent(event);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int eventaction=event.getAction();

        switch(eventaction) {
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

}
