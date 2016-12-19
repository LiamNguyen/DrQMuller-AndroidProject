package com.lanthanh.admin.icareapp.UserDetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, DatabaseObserver{
    TextInputEditText name, address, gender, dob, email, phone;
    private TextInputLayout name_container, address_container, email_container, phone_container;
    private SharedPreferences sharedPref;
    private AppCompatButton abort, finish;
    private boolean validName, validAddress, validEmail, validPhone;
    private Controller aController;
    private NetworkController networkController;
    private FragmentManager fm;
    private AppBarLayout appBarLayout;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //Init controllers
        networkController = new NetworkController(this);
        aController = Controller.getInstance();

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coorLayout);

        sharedPref = getSharedPreferences("content", Context.MODE_PRIVATE);
        fm = getSupportFragmentManager();

        validName = true; validAddress = true; validEmail = true; validPhone = true;

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Semibold.ttf");//Custom font
        //Name
        TextView name_txt = (TextView) findViewById(R.id.ud_name_txt);
        name_txt.setTypeface(font2);
        name = (TextInputEditText) findViewById(R.id.ud_name);
        name.setTypeface(font);
        name.setText(sharedPref.getString("tokenName", getString(R.string.user_name)));
        name.addTextChangedListener(this);
//        TextView change_name = (TextView) findViewById(R.id.ud_change_name);
//        change_name.setTypeface(font);
//        change_name.setOnClickListener(this);
        name_container = (TextInputLayout) findViewById(R.id.ud_name_container);
        name_container.setTypeface(font);
        //Address
        TextView address_txt = (TextView) findViewById(R.id.ud_address_txt);
        address_txt.setTypeface(font2);
        address = (TextInputEditText) findViewById(R.id.ud_address);
        address.setTypeface(font);
        address.setText(sharedPref.getString("tokenAddress", "Không có"));
        address.addTextChangedListener(this);
//        TextView change_address = (TextView) findViewById(R.id.ud_change_address);
//        change_address.setTypeface(font);
//        change_address.setOnClickListener(this);
        address_container = (TextInputLayout) findViewById(R.id.ud_address_container);
        address_container.setTypeface(font);
        //Gender
        TextView gender_txt = (TextView) findViewById(R.id.ud_gender_txt);
        gender_txt.setTypeface(font2);
        gender = (TextInputEditText) findViewById(R.id.ud_gender);
        gender.setTypeface(font);
        gender.setText(sharedPref.getString("tokenGender", "Không có"));
        gender.setOnClickListener(this);
//        TextView change_gender = (TextView) findViewById(R.id.ud_change_gender);
//        change_gender.setTypeface(font);
//        change_gender.setOnClickListener(this);
        //Dob
        TextView dob_txt = (TextView) findViewById(R.id.ud_dob_txt);
        dob_txt.setTypeface(font2);
        dob = (TextInputEditText) findViewById(R.id.ud_dob);
        dob.setTypeface(font);
        dob.setText(sharedPref.getString("tokenDob", "Không có"));
        dob.setOnClickListener(this);
//        TextView change_dob = (TextView) findViewById(R.id.ud_change_dob);
//        change_dob.setTypeface(font);
//        change_dob.setOnClickListener(this);
        //Email
        TextView email_txt = (TextView) findViewById(R.id.ud_email_txt);
        email_txt.setTypeface(font2);
        email = (TextInputEditText) findViewById(R.id.ud_email);
        email.setTypeface(font);
        email.setText(sharedPref.getString("tokenEmail", "Không có"));
        email.addTextChangedListener(this);
//        TextView change_email = (TextView) findViewById(R.id.ud_change_email);
//        change_email.setTypeface(font);
//        change_email.setOnClickListener(this);
        email_container = (TextInputLayout) findViewById(R.id.ud_email_container);
        email_container.setTypeface(font);
        //Phone
        TextView phone_txt = (TextView) findViewById(R.id.ud_phone_txt);
        phone_txt.setTypeface(font2);
        phone = (TextInputEditText) findViewById(R.id.ud_phone);
        phone.setTypeface(font);
        phone.setText(sharedPref.getString("tokenPhone", "Không có"));
        phone.addTextChangedListener(this);
//        TextView change_phone = (TextView) findViewById(R.id.ud_change_phone);
//        change_phone.setTypeface(font);
//        change_phone.setOnClickListener(this);
        phone_container = (TextInputLayout) findViewById(R.id.ud_phone_container);
        phone_container.setTypeface(font);
        //Button
        abort = (AppCompatButton) findViewById(R.id.ud_abort_button);
        abort.setTypeface(font);
        abort.setOnClickListener(this);
        finish = (AppCompatButton) findViewById(R.id.ud_finish_button);
        finish.setTypeface(font);
        finish.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkController.registerNetworkReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkController.unregisterNetworkReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_userdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent toMain = new Intent(this, MainActivity.class);
                toMain.putExtra("fromUserTab", true);
                startActivity(toMain);
                finish();
                return true;
            case R.id.edit:
                name.setEnabled(true);
                address.setEnabled(true);
                gender.setEnabled(true);
                dob.setEnabled(true);
                email.setEnabled(true);
                phone.setEnabled(true);
                abort.setVisibility(View.VISIBLE);
                finish.setVisibility(View.VISIBLE);
                scrollToBottom();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra("fromUserTab", true);
        startActivity(toMain);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.ud_change_name:
//                name.setEnabled(true);
//                validName = false;
//                abort.setVisibility(View.VISIBLE);
//                finish.setVisibility(View.VISIBLE);
//                break;
//            case R.id.ud_change_address:
//                address.setEnabled(true);
//                validAddress = false;
//                abort.setVisibility(View.VISIBLE);
//                finish.setVisibility(View.VISIBLE);
//                break;
//            case R.id.ud_change_gender:
//                gender.setEnabled(true);
//                abort.setVisibility(View.VISIBLE);
//                finish.setVisibility(View.VISIBLE);
//                break;
//            case R.id.ud_change_dob:
//                dob.setEnabled(true);
//                abort.setVisibility(View.VISIBLE);
//                finish.setVisibility(View.VISIBLE);
//                break;
//            case R.id.ud_change_email:
//                email.setEnabled(true);
//                validEmail = false;
//                abort.setVisibility(View.VISIBLE);
//                finish.setVisibility(View.VISIBLE);
//                break;
//            case R.id.ud_change_phone:
//                phone.setEnabled(true);
//                validPhone = false;
//                abort.setVisibility(View.VISIBLE);
//                finish.setVisibility(View.VISIBLE);
//                break;
            case R.id.ud_dob:
                DobFragment dobFragment = new DobFragment();
                dobFragment.show(fm, dobFragment.getClass().getName());
                break;
            case R.id.ud_gender:
                GenderFragment genderFragment = new GenderFragment();
                genderFragment.show(fm, genderFragment.getClass().getName());
                break;
            case R.id.ud_abort_button:
                name.setEnabled(false);
                address.setEnabled(false);
                gender.setEnabled(false);
                dob.setEnabled(false);
                email.setEnabled(false);
                phone.setEnabled(false);
                name.setText(sharedPref.getString("tokenName", getString(R.string.user_name)));
                address.setText(sharedPref.getString("tokenAddress", "Không có"));
                gender.setText(sharedPref.getString("tokenGender", "Không có"));
                dob.setText(sharedPref.getString("tokenDob", "Không có"));
                email.setText(sharedPref.getString("tokenEmail", "Không có"));
                phone.setText(sharedPref.getString("tokenPhone", "Không có"));
                abort.setVisibility(View.INVISIBLE);
                finish.setVisibility(View.INVISIBLE);
                name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                address.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                phone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                validName = true; validAddress = true; validEmail = true; validPhone = true;
                scrollToTop();
                break;
            case R.id.ud_finish_button:
                if (validName && validAddress && validEmail && validPhone) {
                    aController.getUserInfo().addInfo("cus_id", sharedPref.getString("tokenID", ""));
                    aController.getUserInfo().addInfo("name", name.getText().toString().trim());
                    aController.getUserInfo().addInfo("address", address.getText().toString().trim());
                    aController.getUserInfo().addInfo("email", email.getText().toString().trim());
                    aController.getUserInfo().addInfo("phone", phone.getText().toString().trim());
                    if (gender.getText().toString().equals("Nam")){
                        aController.getUserInfo().addInfo("gender", "Male");
                    }else{
                        aController.getUserInfo().addInfo("gender", "Female");
                    }
                    aController.getUserInfo().addInfo("dob", formatDateForDatabase(dob.getText().toString().trim()));
                    aController.getUserInfo().addInfo("update_date", getCurrentDate());
                    aController.setRequestData(this, this, ModelURL.UPDATE_CUSTOMERINFO.getUrl(MainActivity.isUAT), aController.getUserInfo().getPostData());
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

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        try {
            if (status.has("Update_CustomerInfo")){
                String result = status.getString("Update_CustomerInfo");
                if (result.equals("Updated")){
                    name.setEnabled(false);
                    address.setEnabled(false);
                    gender.setEnabled(false);
                    dob.setEnabled(false);
                    email.setEnabled(false);
                    phone.setEnabled(false);
                    abort.setVisibility(View.INVISIBLE);
                    finish.setVisibility(View.INVISIBLE);
                    name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    address.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    phone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    validName = true; validAddress = true; validEmail = true; validPhone = true;
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("tokenName", name.getText().toString().trim());
                    editor.putString("tokenAddress", address.getText().toString().trim());
                    editor.putString("tokenDob", dob.getText().toString().trim());
                    editor.putString("tokenGender", gender.getText().toString());
                    editor.putString("tokenEmail", email.getText().toString().trim());
                    editor.putString("tokenPhone", phone.getText().toString().trim());
                    editor.apply();
                    editor.commit();
                    scrollToTop();
                }else{
                    System.out.println("Update fail");
                }
            }
        } catch (JSONException je){
            System.out.println("Problem with JSON API");
        }
    }

    public String getCurrentDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(Calendar.getInstance().getTime());
    }

    public String formatDateForDatabase(String s){
        StringBuilder builder = new StringBuilder();
        builder.append(s.substring(s.lastIndexOf("-")+1, s.length()));
        builder.append("-");
        builder.append(s.substring(s.indexOf("-")+1, s.lastIndexOf("-")));
        builder.append("-");
        builder.append(s.substring(0, s.indexOf("-")));
        return builder.toString();
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
