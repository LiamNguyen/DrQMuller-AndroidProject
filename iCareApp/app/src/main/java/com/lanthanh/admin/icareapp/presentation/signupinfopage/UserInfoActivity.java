package com.lanthanh.admin.icareapp.presentation.signupinfopage;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolBar;
    private UserInfoActivityPresenterImpl userInfoActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        init();

        //Init toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            //ChooseFragment as default -> hide ToolBar
            userInfoActivityPresenter.navigateFragment(NameAndAddressFragment.class);
        }
    }

    public void init(){
        //Init presenter
        userInfoActivityPresenter = new UserInfoActivityPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        //networkController.registerNetworkReceiver();
//        Intent i = getIntent();
//        Bundle b = i.getExtras();
//        if (b != null){
//            if (b.containsKey(RegisterActivity.TAG)) {
//                Bundle bundle = b.getBundle(RegisterActivity.TAG);
//                if (bundle != null) {
//                    userInfoActivityPresenter.setUserId(bundle.getInt(RegisterActivity.EXTRA_ID, 0));
//                    if (bundle.getString(RegisterActivity.EXTRA_UISTEP).equals("none"))
//                        userInfoActivityPresenter.navigateFragment(NAME_LOCATION);
//                    else if (bundle.getString(RegisterActivity.EXTRA_UISTEP).equals("basic"))
//                        userInfoActivityPresenter.navigateFragment(DOB_GENDER);
//                    else if (bundle.getString(RegisterActivity.EXTRA_UISTEP).equals("necessary"))
//                        userInfoActivityPresenter.navigateFragment(CONTACT);
//                    else if (bundle.getString(RegisterActivity.EXTRA_UISTEP).equals("important"))
//                        userInfoActivityPresenter.navigateFragment(VALIDATE);
//
//                }
//            }
//        }
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
                //userInfoActivityPresenter.onBackPressed();//TODO check this one
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public UserInfoActivityPresenterImpl getMainPresenter() {
        return userInfoActivityPresenter;
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    //Hide/show tool bar
    public void showToolbar(boolean shouldShow){
        if (shouldShow)
            toolBar.setVisibility(View.VISIBLE);
        else
            toolBar.setVisibility(View.GONE);
    }
//    @Override
//    public void onBackPressed() {
//        userInfoActivityPresenter.onBackPressed();
//    }
}
