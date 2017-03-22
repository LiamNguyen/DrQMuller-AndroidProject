package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.UserDetailsActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.UserDetailsActivityPresenterImpl;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher,
        UserDetailsActivityPresenter.View{
    public static final String TAG = UserDetailsActivity.class.getSimpleName();
    private TextInputEditText name, address, gender, dob, email, phone;
    private TextInputLayout name_container, address_container, email_container, phone_container;
    private AppCompatButton abort, finish;
    private boolean validName, validAddress, validEmail, validPhone;
    private UserDetailsActivityPresenter userDetailsActivityPresenter;
    private NetworkController networkController;
    private AppBarLayout appBarLayout;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        init();

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coorLayout);

        Typeface fontNormal = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        Typeface fontImportant = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_SEMIBOLD);//Custom font

        //Name
        TextView name_txt = (TextView) findViewById(R.id.ud_name_txt);
        name_txt.setTypeface(fontImportant);
        name = (TextInputEditText) findViewById(R.id.ud_name);
        name.setTypeface(fontNormal);
        name.setText(userDetailsActivityPresenter.getName());
        name.addTextChangedListener(this);

        name_container = (TextInputLayout) findViewById(R.id.ud_name_container);
        name_container.setTypeface(fontNormal);

        //Address
        TextView address_txt = (TextView) findViewById(R.id.ud_address_txt);
        address_txt.setTypeface(fontImportant);
        address = (TextInputEditText) findViewById(R.id.ud_address);
        address.setTypeface(fontNormal);
        address.setText(userDetailsActivityPresenter.getAddress());
        address.addTextChangedListener(this);

        address_container = (TextInputLayout) findViewById(R.id.ud_address_container);
        address_container.setTypeface(fontNormal);

        //Gender
        TextView gender_txt = (TextView) findViewById(R.id.ud_gender_txt);
        gender_txt.setTypeface(fontImportant);
        gender = (TextInputEditText) findViewById(R.id.ud_gender);
        gender.setTypeface(fontNormal);
        gender.setText(userDetailsActivityPresenter.getGender());
        gender.setOnClickListener(this);

        //Dob
        TextView dob_txt = (TextView) findViewById(R.id.ud_dob_txt);
        dob_txt.setTypeface(fontImportant);
        dob = (TextInputEditText) findViewById(R.id.ud_dob);
        dob.setTypeface(fontNormal);
        dob.setText(userDetailsActivityPresenter.getDob());
        dob.setOnClickListener(this);

        //Email
        TextView email_txt = (TextView) findViewById(R.id.ud_email_txt);
        email_txt.setTypeface(fontImportant);
        email = (TextInputEditText) findViewById(R.id.ud_email);
        email.setTypeface(fontNormal);
        email.setText(userDetailsActivityPresenter.getEmail());
        email.addTextChangedListener(this);

        email_container = (TextInputLayout) findViewById(R.id.ud_email_container);
        email_container.setTypeface(fontNormal);

        //Phone
        TextView phone_txt = (TextView) findViewById(R.id.ud_phone_txt);
        phone_txt.setTypeface(fontImportant);
        phone = (TextInputEditText) findViewById(R.id.ud_phone);
        phone.setTypeface(fontNormal);
        phone.setText(userDetailsActivityPresenter.getPhone());
        phone.addTextChangedListener(this);

        phone_container = (TextInputLayout) findViewById(R.id.ud_phone_container);
        phone_container.setTypeface(fontNormal);

        //Button
        abort = (AppCompatButton) findViewById(R.id.ud_abort_button);
        abort.setTypeface(fontNormal);
        abort.setOnClickListener(this);
        finish = (AppCompatButton) findViewById(R.id.ud_finish_button);
        finish.setTypeface(fontNormal);
        finish.setOnClickListener(this);
    }

    public void init(){
        userDetailsActivityPresenter = new UserDetailsActivityPresenterImpl(getSharedPreferences("content", MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                getSupportFragmentManager(), new CustomerManagerImpl(iCareApiImpl.getAPI()), new AppointmentManagerImpl(iCareApiImpl.getAPI()));
        //Init controllers
        networkController = new NetworkController(this);
        validName = true; validAddress = true; validEmail = true; validPhone = true;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_userdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                userDetailsActivityPresenter.navigateToMainActivity();
                return true;
            case R.id.edit:
                unlockViews();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getStringResource(int id) {
        return getString(id);
    }

    @Override
    public void navigateToMainActivity() {
        hideProgress();
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra(TAG, true);
        startActivity(toMain);
        finish();
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
        userDetailsActivityPresenter.navigateToMainActivity();
    }

    @Override
    public void refreshViews() {
        name.setEnabled(false);
        address.setEnabled(false);
        gender.setEnabled(false);
        dob.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);

        name.setText(userDetailsActivityPresenter.getName());
        address.setText(userDetailsActivityPresenter.getAddress());
        gender.setText(userDetailsActivityPresenter.getGender());
        dob.setText(userDetailsActivityPresenter.getDob());
        email.setText(userDetailsActivityPresenter.getEmail());
        phone.setText(userDetailsActivityPresenter.getPhone());
        abort.setVisibility(View.INVISIBLE);
        finish.setVisibility(View.INVISIBLE);

        name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        address.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        phone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

        validName = true; validAddress = true; validEmail = true; validPhone = true;

        scrollToTop();
    }

    @Override
    public void unlockViews() {
        name.setEnabled(true);
        address.setEnabled(true);
        gender.setEnabled(true);
        dob.setEnabled(true);
        email.setEnabled(true);
        phone.setEnabled(true);

        abort.setVisibility(View.VISIBLE);
        finish.setVisibility(View.VISIBLE);

        scrollToBottom();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ud_dob:
                userDetailsActivityPresenter.showDobDialogFragment();
                break;
            case R.id.ud_gender:
                userDetailsActivityPresenter.showGenderDialogFragment();
                break;
            case R.id.ud_abort_button:
                refreshViews();
                break;
            case R.id.ud_finish_button:
                if (validName && validAddress && validEmail && validPhone) {
                    userDetailsActivityPresenter.setName(name.getText().toString().trim());
                    userDetailsActivityPresenter.setAddress(address.getText().toString().trim());
                    userDetailsActivityPresenter.setEmail(email.getText().toString().trim());
                    userDetailsActivityPresenter.setPhone(phone.getText().toString().trim());
                    userDetailsActivityPresenter.updateCustomer();
                }else{
                    if (!validName){
                        if (name.getText().toString().equals("")){
                            name_container.setError(getString(R.string.name_null));
                        }else {
                            name_container.setError(getString(R.string.name_requirement));
                        }
                        name_container.setErrorEnabled(true);
                    }
                    if (!validAddress){
                        if (address.getText().toString().equals("")){
                            address_container.setError(getString(R.string.address_null));
                        }else{
                            address_container.setError(getString(R.string.address_requirement));
                        }
                        address_container.setErrorEnabled(true);
                    }
                    if (!validEmail){
                        if (email.getText().toString().equals("")){
                            email_container.setError(getString(R.string.email_null));
                        }else {
                            email_container.setError(getString(R.string.email_requirement));
                        }
                        email_container.setErrorEnabled(true);
                    }
                    if (!validPhone){
                        if (phone.getText().toString().equals("")){
                            phone_container.setError(getString(R.string.phone_null));
                        }else {
                            phone_container.setError(getString(R.string.phone_requirement));
                        }
                        phone_container.setErrorEnabled(true);
                    }
                }
                break;
            default:
                break;
        }
        hideSoftKeyboard();
    }

    @Override
    public UserDetailsActivityPresenter getMainPresenter() {
        return userDetailsActivityPresenter;
    }

    @Override
    public void setDobView(String dob) {
        this.dob.setText(dob);
    }

    @Override
    public void setGenderView(String gender) {
        this.gender.setText(gender);
    }

    //nothing
    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (name.getText().hashCode() == s.hashCode()){//Edit name
            validName = false;
            String get_name = name.getText().toString();
            get_name.trim();
            if (!get_name.equals("")){
                if (get_name.matches(ModelInputRequirement.NAME)){
                    name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_valid_input, 0);
                    name_container.setErrorEnabled(false);
                    validName = true;
                }else{
                    name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_invalid_input, 0);
                    validName = false;
                }
            }else {
                name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                validName = false;
            }
        }else if (address.getText().hashCode() == s.hashCode()){//Edit Address
            validAddress = false;
            String get_address = address.getText().toString();
            get_address.toString();
            if (!get_address.equals("")){
                if (get_address.matches(ModelInputRequirement.ADDRESS)){
                    address.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_valid_input, 0);
                    address_container.setErrorEnabled(false);
                    validAddress = true;
                }else{
                    address.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_invalid_input, 0);
                    validAddress = false;
                }
            }else {
                address.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                validAddress = false;
            }
        }else if (email.getText().hashCode() == s.hashCode()){
            validEmail = false;
            String get_email = email.getText().toString();
            get_email.trim();
            if (!get_email.equals("")){
                if (get_email.matches(ModelInputRequirement.EMAIL)){
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_valid_input, 0);
                    email_container.setErrorEnabled(false);
                    validEmail = true;
                }else{
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_invalid_input, 0);
                    validEmail = false;
                }
            }else {
                email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                validEmail = false;
            }
        }else if (phone.getText().hashCode() == s.hashCode()){
            validPhone = false;
            String get_phone = phone.getText().toString();
            get_phone.trim();
            if (!get_phone.equals("")){
                if (get_phone.matches(ModelInputRequirement.PHONE)){
                    phone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_valid_input, 0);
                    phone_container.setErrorEnabled(false);
                    validPhone = true;
                }else{
                    phone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_invalid_input, 0);
                    validPhone = false;
                }
            }else {
                phone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                validPhone = false;
            }
        }
    }

    //nothing
    @Override
    public void afterTextChanged(Editable editable) {
    }

    public void scrollToBottom(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, 1000, true);
        }
    }

    public void scrollToTop(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, -1000, true);
        }
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
