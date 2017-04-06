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
                        mainActivityPresenter.showBookingTab();
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
        mainActivityPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainActivityPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.destroy();
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

    //Call in case losing network and then connected again
    public void refreshAfterNetworkConnected(){
        this.onPostResume();
    }
}
