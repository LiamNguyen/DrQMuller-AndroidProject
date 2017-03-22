package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.presenter.ConfirmBookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.ConfirmBookingActivityPresenterImpl;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class ConfirmBookingActivity extends AppCompatActivity implements View.OnClickListener,
        ConfirmBookingActivityPresenter.View {
    public static final String TAG = ConfirmBookingActivity.class.getSimpleName();
    public static final int CONFIRMED = 1;
    public static final int NOT_CONFIRMED = 0;
    private TextInputEditText edttxt;
    private ProgressBar progressBar;
    private ConfirmBookingActivityPresenter confirmBookingActivityPresenter;
    private NetworkController networkController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        init();

        //Init view
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //Set up Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        AppCompatButton button = (AppCompatButton) findViewById(R.id.confirm_button);
        button.setOnClickListener(this);
        button.setTypeface(font);
        edttxt = (TextInputEditText) findViewById(R.id.booking_confirm_input);
        edttxt.setTypeface(font);
        TextInputLayout edttxt_container = (TextInputLayout) findViewById(R.id.booking_confirm_container);
        edttxt_container.setTypeface(font);
        TextView txt = (TextView) findViewById(R.id.booking_instruction);
        txt.setTypeface(font);
    }

    public void init(){
        confirmBookingActivityPresenter = new ConfirmBookingActivityPresenterImpl(getSharedPreferences("content", MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                new CustomerManagerImpl(iCareApiImpl.getAPI()), new AppointmentManagerImpl(iCareApiImpl.getAPI()));
        //Init controllers
        networkController = new NetworkController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //networkController.registerNetworkReceiver();
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
                navigateToMainActivity(0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (edttxt.getText().toString().isEmpty()){
            showError(getString(R.string.empty_code));
        }else {
            confirmBookingActivityPresenter.updateAppointment(edttxt.getText().toString().trim());
        }
        hideSoftKeyboard();
    }

    @Override
    public void navigateToMainActivity(int extra) {
        hideProgress();
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra(TAG, extra);
        startActivity(toMain);
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
        toast.setGravity(Gravity.CENTER, 0, 110);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        navigateToMainActivity(NOT_CONFIRMED);
    }

    @Override
    public String getStringResource(int id) {
        return getString(id);
    }

    //Hide SoftKeyBoard when needed
    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}