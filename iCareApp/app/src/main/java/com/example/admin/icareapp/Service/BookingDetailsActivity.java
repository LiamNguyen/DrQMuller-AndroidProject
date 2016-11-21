package com.example.admin.icareapp.Service;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.admin.icareapp.BookingTab.BookingBookFragment;
import com.example.admin.icareapp.BookingTab.BookingSelectFragment;
import com.example.admin.icareapp.Model.ModelBookingDetail;
import com.example.admin.icareapp.R;
import com.example.admin.icareapp.UserTab.UserFragment;

import java.util.ArrayList;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class BookingDetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        RecyclerView rv = (RecyclerView)findViewById(R.id.bookingdet_recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

    }
}
