package com.lanthanh.admin.icareapp.Confirm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class ConfirmBookingActivity extends AppCompatActivity implements View.OnClickListener, DatabaseObserver {
    private TextInputEditText edttxt;
    private Controller aController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        aController = Controller.getInstance();

        AppCompatButton button = (AppCompatButton) findViewById(R.id.confirm_button);
        edttxt = (TextInputEditText) findViewById(R.id.booking_confirm_input);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (edttxt.getText().toString().isEmpty()){
            Toast.makeText(this, "Mã xác nhận không hợp lệ", Toast.LENGTH_LONG).show();
        }else {
            SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
            String cus_id = sharedPref.getString("tokenID", "");
            aController.setRequestData(this, this, ModelURL.UPDATE_APPOINTMENT.getUrl(MainActivity.isUAT), "cus_id=" + cus_id + "&code=" + edttxt.getText().toString().trim());
        }
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
                        Toast.makeText(this, "Mã xác nhận không hợp lệ", Toast.LENGTH_LONG).show();
                    }else{
                        updateBookingStatus(edttxt.getText().toString().trim());
                        Intent toMain = new Intent(this, MainActivity.class);
                        toMain.putExtra("isBookingSuccess", 1);
                        startActivity(toMain);
                        finish();
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
                    System.out.println(data);
                    if (data.getString(6).equals(s)) {
                        data.put(5, "Đã Xác Nhận");
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
}