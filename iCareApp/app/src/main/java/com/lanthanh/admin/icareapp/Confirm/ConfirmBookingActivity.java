package com.lanthanh.admin.icareapp.Confirm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;
import com.google.gson.Gson;
import com.lanthanh.admin.icareapp.Service.BookingDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class ConfirmBookingActivity extends AppCompatActivity implements View.OnClickListener, DatabaseObserver {
    private TextInputEditText edttxt;
    //Controller
    private Controller aController;
    private NetworkController networkController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //Init controllers
        networkController = new NetworkController(this);
        aController = Controller.getInstance();

        //Toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
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
    public void onClick(View view) {
        if (edttxt.getText().toString().isEmpty()){
            Toast.makeText(this, getString(R.string.empty_code), Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
            String cus_id = sharedPref.getString("tokenID", "");
            aController.setRequestData(this, this, ModelURL.UPDATE_APPOINTMENT.getUrl(MainActivity.isUAT), "cus_id=" + cus_id + "&code=" + edttxt.getText().toString().trim());
        }
        hideSoftKeyboard();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent toDetails = new Intent(this, BookingDetailsActivity.class);
                toDetails.putExtra("isBookingSuccess", 0);
                startActivity(toDetails);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent toDetails = new Intent(this, BookingDetailsActivity.class);
        toDetails.putExtra("isBookingSuccess", 0);
        startActivity(toDetails);
        finish();
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        if (status == null) {
            System.out.println("ERROR IN PHP FILE");
            return;
        }

        try{
            if (status.has("Update_Appointment")){
                //String result = status.getString("Select_ToAuthenticate");
                String response = status.getString("Update_Appointment");//Get the array of days' JSONObject
                if (!response.isEmpty()){
                    if (response.equals("Failed")){
                        Toast.makeText(this, getString(R.string.wrong_code), Toast.LENGTH_SHORT).show();
                    }else if(response.equals("Updated")){
                        updateBookingStatus(edttxt.getText().toString().trim());
                        Intent toDetails = new Intent(this, BookingDetailsActivity.class);
                        toDetails.putExtra("isBookingSuccess", 1);
                        startActivity(toDetails);
                        finish();
                    }else if (response.equals("QueryFailed")){
                        System.out.println("Query Failed");
                    }
                }
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    public void updateBookingStatus(String s){
        Gson gson = new Gson();
        JSONArray bookingCode;
        SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        bookingCode = gson.fromJson(sharedPref.getString("bookingCode", ""), JSONArray.class);

        if (bookingCode != null && bookingCode.length() != 0){
            for (int i = 0; i < bookingCode.length(); i ++) {
                try {
                    JSONArray data = gson.fromJson(sharedPref.getString(bookingCode.getString(i), ""), JSONArray.class);
                    if (data.getString(6).equals(s)) {
                        data.put(5, getString(R.string.confirmed));
                        editor.putString(bookingCode.getString(i), gson.toJson(data));
                        editor.apply();
                        editor.commit();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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