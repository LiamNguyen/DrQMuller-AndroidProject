package com.lanthanh.admin.icareapp.presentation.userdetailpage;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class UserDetailsActivity extends BaseActivity implements View.OnClickListener{
    public static final String TAG = UserDetailsActivity.class.getSimpleName();
    @BindView(R.id.ud_name_txt) private TextView nameTitle;
    @BindView(R.id.ud_address_txt) private TextView addressTitle;
    @BindView(R.id.ud_dob_txt) private TextView dobTitle;
    @BindView(R.id.ud_gender_txt) private TextView genderTitle;
    @BindView(R.id.ud_email_txt) private TextView emailTitle;
    @BindView(R.id.ud_phone_txt) private TextView phoneTitle;
    @BindView(R.id.ud_name) private TextInputEditText editName;
    @BindView(R.id.ud_address) private TextInputEditText editAddress;
    @BindView(R.id.ud_dob) private TextView displayDob;
    @BindView(R.id.ud_gender) private TextView displayGender;
    @BindView(R.id.ud_email) private TextInputEditText editEmail;
    @BindView(R.id.ud_phone) private TextInputEditText editPhone;
    @BindView(R.id.ud_name_container) private TextInputLayout editNameContainer;
    @BindView(R.id.ud_address_container) private TextInputLayout editAddressContainer;
    @BindView(R.id.ud_email_container) private TextInputLayout editEmailContainer;
    @BindView(R.id.ud_phone_container) private TextInputLayout editPhoneContainer;
    @BindView(R.id.toolbar) private Toolbar toolBar;
    @BindView(R.id.appBar) private AppBarLayout appBarLayout;
    @BindView(R.id.coorLayout) private CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ud_abort_button) private AppCompatButton abortButton;
    @BindView(R.id.ud_finish_button) private AppCompatButton finishButton;

    private boolean validName, validAddress, validEmail, validPhone;
    private UserDetailsActivityPresenter userDetailsActivityPresenter;
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        
        init();

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface fontNormal = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        Typeface fontImportant = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_SEMIBOLD);//Custom font

        //Name
        nameTitle.setTypeface(fontImportant);
        editName.setTypeface(fontNormal);
        editName.setText(userDetailsActivityPresenter.getName());
        editName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validName = false;
                String name = editName.getText().toString().trim();
                if (!name.equals("")){
                    if (name.matches(ModelInputRequirement.NAME)){
                        editName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editNameContainer.setErrorEnabled(false);
                        validName = true;
                    }else{
                        editName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                        validName = false;
                    }
                }else {
                    editName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    validName = false;
                }
            }
        });
        editNameContainer.setTypeface(fontNormal);

        //Address
        addressTitle.setTypeface(fontImportant);
        editAddress.setTypeface(fontNormal);
        editAddress.setText(userDetailsActivityPresenter.getAddress());
        editAddress.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validAddress = false;
                String address = editAddress.getText().toString().trim();
                if (!address.equals("")){
                    if (address.matches(ModelInputRequirement.ADDRESS)){
                        editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editAddressContainer.setErrorEnabled(false);
                        validAddress = true;
                    }else{
                        editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                        validAddress = false;
                    }
                }else {
                    editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    validAddress = false;
                }
            }
        });
        editAddressContainer.setTypeface(fontNormal);

        //Gender
        genderTitle.setTypeface(fontImportant);
        displayGender.setTypeface(fontNormal);
        displayGender.setText(userDetailsActivityPresenter.getGender());
        displayGender.setOnClickListener(this);

        //Dob
        dobTitle.setTypeface(fontImportant);
        displayDob.setTypeface(fontNormal);
        displayDob.setText(userDetailsActivityPresenter.getDob());
        displayDob.setOnClickListener(this);

        //Email
        emailTitle.setTypeface(fontImportant);
        editEmail.setTypeface(fontNormal);
        editEmail.setText(userDetailsActivityPresenter.getEmail());
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validEmail = false;
                String email = editEmail.getText().toString().trim();
                if (!email.equals("")){
                    if (email.matches(ModelInputRequirement.EMAIL)){
                        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editEmailContainer.setErrorEnabled(false);
                        validEmail = true;
                    }else{
                        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                        validEmail = false;
                    }
                }else {
                    editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    validEmail = false;
                }
            }
        });
        editEmailContainer.setTypeface(fontNormal);

        //Phone
        phoneTitle.setTypeface(fontImportant);
        editPhone.setTypeface(fontNormal);
        editPhone.setText(userDetailsActivityPresenter.getPhone());
        editPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validPhone = false;
                String phone = editPhone.getText().toString().trim();
                if (!phone.equals("")){
                    if (phone.matches(ModelInputRequirement.PHONE)){
                        editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editPhoneContainer.setErrorEnabled(false);
                        validPhone = true;
                    }else{
                        editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                        validPhone = false;
                    }
                }else {
                    editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    validPhone = false;
                }
            }
        });
        editPhoneContainer.setTypeface(fontNormal);

        //Button
        abortButton.setTypeface(fontNormal);
        abortButton.setOnClickListener(this);
        finishButton.setTypeface(fontNormal);
        finishButton.setOnClickListener(this);
    }

    public void init(){
        //Init controllers
        //networkController = new NetworkController(this);
        userDetailsActivityPresenter = new UserDetailsActivityPresenter();
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
                //userDetailsActivityPresenter.navigateToMainActivity();
                return true;
            case R.id.edit:
                unlockViews();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void navigateToMainActivity() {
        //hideProgress();
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra(TAG, true);
        startActivity(toMain);
        finish();
    }


//    public void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    public void hideProgress() {
//        progressBar.setVisibility(View.GONE);
//    }


    @Override
    public void onBackPressed() {
        //userDetailsActivityPresenter.navigateToMainActivity();
    }

    public void refreshViews() {
        editName.setEnabled(false);
        editAddress.setEnabled(false);
        displayDob.setEnabled(false);
        displayGender.setEnabled(false);
        editEmail.setEnabled(false);
        editPhone.setEnabled(false);

        editName.setText(userDetailsActivityPresenter.getName());
        editAddress.setText(userDetailsActivityPresenter.getAddress());
        displayDob.setText(userDetailsActivityPresenter.getGender());
        displayGender.setText(userDetailsActivityPresenter.getDob());
        editEmail.setText(userDetailsActivityPresenter.getEmail());
        editPhone.setText(userDetailsActivityPresenter.getPhone());
        abortButton.setVisibility(View.INVISIBLE);
        finishButton.setVisibility(View.INVISIBLE);

        editName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

        validName = true; validAddress = true; validEmail = true; validPhone = true;

        scrollToTop();
    }

    public void unlockViews() {
        editName.setEnabled(true);
        editAddress.setEnabled(true);
        displayDob.setEnabled(true);
        displayGender.setEnabled(true);
        editEmail.setEnabled(true);
        editPhone.setEnabled(true);

        abortButton.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.VISIBLE);

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
                    userDetailsActivityPresenter.setName(editName.getText().toString().trim());
                    userDetailsActivityPresenter.setAddress(editAddress.getText().toString().trim());
                    userDetailsActivityPresenter.setEmail(editEmail.getText().toString().trim());
                    userDetailsActivityPresenter.setPhone(editPhone.getText().toString().trim());
                    userDetailsActivityPresenter.updateCustomer();
                }else{
                    if (!validName){
                        if (editName.getText().toString().equals("")){
                            editNameContainer.setError(getString(R.string.name_null));
                        }else {
                            editNameContainer.setError(getString(R.string.name_requirement));
                        }
                        editNameContainer.setErrorEnabled(true);
                    }
                    if (!validAddress){
                        if (editAddress.getText().toString().equals("")){
                            editAddressContainer.setError(getString(R.string.address_null));
                        }else{
                            editAddressContainer.setError(getString(R.string.address_requirement));
                        }
                        editAddressContainer.setErrorEnabled(true);
                    }
                    if (!validEmail){
                        if (editEmail.getText().toString().equals("")){
                            editEmailContainer.setError(getString(R.string.email_null));
                        }else {
                            editEmailContainer.setError(getString(R.string.email_requirement));
                        }
                        editEmailContainer.setErrorEnabled(true);
                    }
                    if (!validPhone){
                        if (editPhone.getText().toString().equals("")){
                            editPhoneContainer.setError(getString(R.string.phone_null));
                        }else {
                            editPhoneContainer.setError(getString(R.string.phone_requirement));
                        }
                        editPhoneContainer.setErrorEnabled(true);
                    }
                }
                break;
            default:
                break;
        }
        hideSoftKeyboard();
    }
    
    public UserDetailsActivityPresenter getMainPresenter() {
        return userDetailsActivityPresenter;
    }

    public void setDobView(String dob) {
        this.displayDob.setText(dob);
    }

    public void setGenderView(String gender) {
        this.displayGender.setText(gender);
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
}
