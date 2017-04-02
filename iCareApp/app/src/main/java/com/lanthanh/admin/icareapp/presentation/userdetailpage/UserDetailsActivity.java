package com.lanthanh.admin.icareapp.presentation.userdetailpage;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;
import com.lanthanh.admin.icareapp.presentation.model.InputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class UserDetailsActivity extends BaseActivity {
    @BindView(R.id.ud_name_txt) TextView nameTitle;
    @BindView(R.id.ud_address_txt) TextView addressTitle;
    @BindView(R.id.ud_dob_txt) TextView dobTitle;
    @BindView(R.id.ud_gender_txt) TextView genderTitle;
    @BindView(R.id.ud_email_txt) TextView emailTitle;
    @BindView(R.id.ud_phone_txt) TextView phoneTitle;
    @BindView(R.id.ud_name) TextInputEditText editName;
    @BindView(R.id.ud_address) TextInputEditText editAddress;
    @BindView(R.id.ud_dob) TextView displayDob;
    @BindView(R.id.ud_gender) TextView displayGender;
    @BindView(R.id.ud_email) TextInputEditText editEmail;
    @BindView(R.id.ud_phone) TextInputEditText editPhone;
    @BindView(R.id.ud_name_container) TextInputLayout editNameContainer;
    @BindView(R.id.ud_address_container) TextInputLayout editAddressContainer;
    @BindView(R.id.ud_email_container) TextInputLayout editEmailContainer;
    @BindView(R.id.ud_phone_container) TextInputLayout editPhoneContainer;
    @BindView(R.id.toolbar) Toolbar toolBar;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.appBar) AppBarLayout appBarLayout;
    @BindView(R.id.coorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ud_abort_button) TextView abortButton;
    @BindView(R.id.ud_save_button) TextView saveButton;
    @BindView(R.id.button_container) LinearLayout buttonContainer;
    @BindDrawable(R.drawable.ic_check_circle_white_24dp) Drawable validInputDrawable;
    @BindDrawable(R.drawable.ic_error_white_24dp) Drawable invalidInputDrawable;
    @BindDrawable(R.drawable.ic_chevron_left_white_48dp) Drawable backDrawable;
    @BindDrawable(R.drawable.ic_mode_edit_white_36dp) Drawable editDrawble;

    private boolean validName, validAddress, validEmail, validPhone;
    private UserDetailsActivityPresenter userDetailsActivityPresenter;
    private boolean isEditMode;
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        init();

        setDrawableColor(validInputDrawable);
        setDrawableColor(invalidInputDrawable);
        setDrawableColor(backDrawable);
        setDrawableColor(editDrawble);

        setSupportActionBar(toolBar);
        toolBar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(backDrawable);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface fontNormal = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        Typeface fontImportant = Typeface.createFromAsset(getAssets(), GraphicUtils.FONT_SEMIBOLD);//Custom font

        //Name
        nameTitle.setTypeface(fontImportant);
        editName.setTypeface(fontNormal);
        editName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = editName.getText().toString();
                if (isEditMode) {
                    if (!name.isEmpty()) {
                        if (name.matches(InputRequirement.NAME)) {
                            editName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                            editNameContainer.setErrorEnabled(false);
                            validName = true;
                        } else {
                            editName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                            validName = false;
                        }
                    } else {
                        editName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        validName = false;
                    }
                }
            }
        });
        editNameContainer.setTypeface(fontNormal);

        //Address
        addressTitle.setTypeface(fontImportant);
        editAddress.setTypeface(fontNormal);
        editAddress.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String address = editAddress.getText().toString().trim();
                if (isEditMode) {
                    if (!address.isEmpty()) {
                        if (address.matches(InputRequirement.ADDRESS)) {
                            editAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                            editAddressContainer.setErrorEnabled(false);
                            validAddress = true;
                        } else {
                            editAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                            validAddress = false;
                        }
                    } else {
                        editAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        validAddress = false;
                    }
                }
            }
        });
        editAddressContainer.setTypeface(fontNormal);

        //Gender
        genderTitle.setTypeface(fontImportant);
        displayGender.setTypeface(fontNormal);
        displayGender.setOnClickListener(
            view -> {
                hideSoftKeyboard();
                userDetailsActivityPresenter.showGenderDialogFragment();
            }
        );

        //Dob
        dobTitle.setTypeface(fontImportant);
        displayDob.setTypeface(fontNormal);
        displayDob.setOnClickListener(
            view -> {
                hideSoftKeyboard();
                userDetailsActivityPresenter.showDobDialogFragment();
            }
        );

        //Email
        emailTitle.setTypeface(fontImportant);
        editEmail.setTypeface(fontNormal);
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = editEmail.getText().toString().trim();
                if (isEditMode) {
                    if (!email.isEmpty()) {
                        if (email.matches(InputRequirement.EMAIL)) {
                            editEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                            editEmailContainer.setErrorEnabled(false);
                            validEmail = true;
                        } else {
                            editEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                            validEmail = false;
                        }
                    } else {
                        editEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        validEmail = false;
                    }
                }
            }
        });
        editEmailContainer.setTypeface(fontNormal);

        //Phone
        phoneTitle.setTypeface(fontImportant);
        editPhone.setTypeface(fontNormal);
        editPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = editPhone.getText().toString().trim();
                if (isEditMode) {
                    if (!phone.isEmpty()) {
                        if (phone.matches(InputRequirement.PHONE)) {
                            editPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_white_24dp, 0);
                            editPhoneContainer.setErrorEnabled(false);
                            validPhone = true;
                        } else {
                            editPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0);
                            validPhone = false;
                        }
                    } else {
                        editPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        validPhone = false;
                    }
                }
            }
        });
        editPhoneContainer.setTypeface(fontNormal);

        //Button
        abortButton.setTypeface(fontNormal);
        abortButton.setOnClickListener(
            view -> {
                hideSoftKeyboard();
                refreshViews();
            }
        );
        saveButton.setTypeface(fontNormal);
        saveButton.setOnClickListener(
            view -> {
                hideSoftKeyboard();
                if (validName && validAddress && validEmail && validPhone) {
                    userDetailsActivityPresenter.updateCustomerInformation(editName.getText().toString().trim(), editAddress.getText().toString().trim(),
                                                                           displayDob.getText().toString(), displayGender.getText().toString(),
                                                                           editEmail.getText().toString(), editPhone.getText().toString());
                }else{
                    if (!validName){
                        if (editName.getText().toString().isEmpty()){
                            editNameContainer.setError(getString(R.string.name_null));
                        }else {
                            editNameContainer.setError(getString(R.string.name_requirement));
                        }
                        editNameContainer.setErrorEnabled(true);
                    }
                    if (!validAddress){
                        if (editAddress.getText().toString().isEmpty()){
                            editAddressContainer.setError(getString(R.string.address_null));
                        }else{
                            editAddressContainer.setError(getString(R.string.address_requirement));
                        }
                        editAddressContainer.setErrorEnabled(true);
                    }
                    if (!validEmail){
                        if (editEmail.getText().toString().isEmpty()){
                            editEmailContainer.setError(getString(R.string.email_null));
                        }else {
                            editEmailContainer.setError(getString(R.string.email_requirement));
                        }
                        editEmailContainer.setErrorEnabled(true);
                    }
                    if (!validPhone){
                        if (editPhone.getText().toString().isEmpty()){
                            editPhoneContainer.setError(getString(R.string.phone_null));
                        }else {
                            editPhoneContainer.setError(getString(R.string.phone_requirement));
                        }
                        editPhoneContainer.setErrorEnabled(true);
                    }
                }
            }
        );

        init();
    }

    public void init(){
        //Init controllers
        //networkController = new NetworkController(this);
        userDetailsActivityPresenter = new UserDetailsActivityPresenter(this);
        populateUserInfdormation();
        validName = true; validAddress = true; validEmail = true; validPhone = true;
        isEditMode = false;
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
    protected void onDestroy() {
        super.onDestroy();
        userDetailsActivityPresenter.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_userdetails, menu);
        menu.getItem(0).setIcon(editDrawble);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.edit:
                unlockViews();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void refreshViews() {
        isEditMode = false;

        editName.setEnabled(false);
        editAddress.setEnabled(false);
        displayDob.setEnabled(false);
        displayGender.setEnabled(false);
        editEmail.setEnabled(false);
        editPhone.setEnabled(false);

        populateUserInfdormation();
        editName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        editAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        editEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        editPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        editNameContainer.setErrorEnabled(false);
        editAddressContainer.setErrorEnabled(false);
        editEmailContainer.setErrorEnabled(false);
        editPhoneContainer.setErrorEnabled(false);

        buttonContainer.setVisibility(View.INVISIBLE);

        validName = true; validAddress = true; validEmail = true; validPhone = true;

        scrollToTop();
    }

    public void unlockViews() {
        isEditMode = true;

        editName.setEnabled(true);
        editAddress.setEnabled(true);
        displayDob.setEnabled(true);
        displayGender.setEnabled(true);
        editEmail.setEnabled(true);
        editPhone.setEnabled(true);

        buttonContainer.setVisibility(View.VISIBLE);

        scrollToBottom();
    }

    public void populateUserInfdormation() {
        userDetailsActivityPresenter.populateUserInformation(
            name -> editName.setText(name),
            address -> editAddress.setText(address),
            dob -> displayDob.setText(dob),
            gender -> displayGender.setText(gender),
            email -> editEmail.setText(email),
            phone -> editPhone.setText(phone)
        );
    }
    
    public UserDetailsActivityPresenter getMainPresenter() {
        return userDetailsActivityPresenter;
    }

    public void setDobValue(String dob) {
        this.displayDob.setText(dob);
    }

    public void setGenderValue(String gender) {
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

    public void setDrawableColor(Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //for M and above (API >= 23)
            drawable.setColorFilter(getResources().getColor(R.color.colorPrimary, null), PorterDuff.Mode.SRC_ATOP);
        } else{
            //below M (API <23)
            drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
