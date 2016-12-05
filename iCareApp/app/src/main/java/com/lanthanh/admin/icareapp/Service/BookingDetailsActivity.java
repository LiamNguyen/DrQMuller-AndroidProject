package com.lanthanh.admin.icareapp.Service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class BookingDetailsActivity extends AppCompatActivity{
    List<BookingItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        list = new ArrayList<>();
        listInit(list);
        RecyclerView rv = (RecyclerView)findViewById(R.id.bookingdet_recycler_view);
    
        BookingCVAdapter adapter = new BookingCVAdapter(this, list);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null){
            if (b.containsKey("isBookingSuccess")) {
                int m = b.getInt("isBookingSuccess", 0);
                if (m == 1) {
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.booking_success))
                            .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setCancelable(false).show();
                }else{
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.booking_fail))
                            .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setCancelable(false).show();
                }
            }
        }else{
            if (list.isEmpty()){
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.empty_appointment))
                        .setPositiveButton(getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                onBackPressed();
                            }
                        }).setCancelable(false).show();
            }
        }
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

    public void listInit(List<BookingItem> list){
        Gson gson = new Gson();
        JSONArray bookingCode;
        SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
        bookingCode = gson.fromJson(sharedPref.getString("bookingCode", ""), JSONArray.class);
        if (bookingCode != null && bookingCode.length() != 0){
            for (int i = 0; i < bookingCode.length(); i ++) {
                try {
                    JSONArray data = gson.fromJson(sharedPref.getString(bookingCode.getString(i), ""), JSONArray.class);
                    System.out.println(data);
                    list.add(new BookingItem(bookingCode.toString(i), data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getString(6), data.getString(7), data.getString(8), data.getString(9), data.getString(10)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
