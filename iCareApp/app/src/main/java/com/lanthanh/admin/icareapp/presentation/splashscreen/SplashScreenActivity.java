package com.lanthanh.admin.icareapp.presentation.splashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.application.iCareApplication;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by long.vu on 3/22/2017.
 */

public class SplashScreenActivity extends BaseActivity {
    @BindView(R.id.versionName) TextView versionName;

    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        init();
        versionName.setText(((iCareApplication) getApplication()).getVersionName());
    }

    public void init() {
        presenter = new SplashScreenPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkLoggedIn();
    }
}
