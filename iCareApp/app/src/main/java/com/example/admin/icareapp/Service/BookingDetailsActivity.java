package com.example.admin.icareapp.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.example.admin.icareapp.BookingTab.BookingBookFragment;
import com.example.admin.icareapp.BookingTab.BookingSelectFragment;
import com.example.admin.icareapp.MainActivity;
import com.example.admin.icareapp.Model.ModelBookingDetail;
import com.example.admin.icareapp.R;
import com.example.admin.icareapp.UserTab.UserFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class BookingDetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        List<BookingItem> list = new ArrayList<>();
        listInit(list);
        RecyclerView rv = (RecyclerView)findViewById(R.id.bookingdet_recycler_view);

        BookingCVAdapter adapter = new BookingCVAdapter(this, list);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent toMain = new Intent(this, MainActivity.class);
            toMain.putExtra("fromUserTab", true);
            startActivity(toMain);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void listInit(List<BookingItem> list){
        Gson gson = new Gson();
        JSONArray bookingCode;
        SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
        bookingCode = gson.fromJson(sharedPref.getString("bookingCode", ""), JSONArray.class);
        System.out.println(bookingCode);
        if (bookingCode != null && bookingCode.length() != 0){
            for (int i = 0; i < bookingCode.length(); i ++) {
                try {
                    JSONArray data = gson.fromJson(sharedPref.getString(bookingCode.getString(i), ""), JSONArray.class);
                    list.add(new BookingItem(bookingCode.toString(i), data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getString(6)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
