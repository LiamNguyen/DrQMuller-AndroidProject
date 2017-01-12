package com.lanthanh.admin.icareapp.presentation.view.activity;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.R;
import com.google.gson.Gson;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.BookingItem;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingDetailsActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.BookingDetailsActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.adapter.BookingCVAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class BookingDetailsActivity extends AppCompatActivity implements BookingDetailsActivityPresenter.View{
    private BookingCVAdapter adapter;
    private NetworkController networkController;
    private BookingDetailsActivityPresenter bookingDetailsActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        init();

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setVisibility(View.GONE);

        RecyclerView rv = (RecyclerView)findViewById(R.id.bookingdet_recycler_view);
        adapter = new BookingCVAdapter(this, new ArrayList<DTOAppointment>());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //Init list
        bookingDetailsActivityPresenter.updateList();
    }

    public void init(){
        bookingDetailsActivityPresenter = new BookingDetailsActivityPresenterImpl(getSharedPreferences("content", MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, new AppointmentManagerImpl(iCareApiImpl.getAPI()));
        //Init controllers
        networkController = new NetworkController(this);
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
            if (adapter.getListSize() == 0){
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToMainActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToMainActivity() {
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra("fromUserTab", true);
        startActivity(toMain);
        finish();
    }

    @Override
    public void updateList(List<DTOAppointment> list) {
        adapter.updateList(list);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onBackPressed() {
        navigateToMainActivity();
    }

    @Override
    public BookingDetailsActivityPresenter getMainPresenter() {
        return bookingDetailsActivityPresenter;
    }
}
