package com.lanthanh.admin.icareapp.presentation.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;

/**
 * Created by ADMIN on 18-Dec-16.
 */

public class NetworkBroadcastReceiver {
    private BaseActivity activity;
    private BroadcastReceiver networkReceiver;
    private IntentFilter intentFilter;
    private AlertDialog alertDialog;
    private boolean isConnected;
    private final String CONNECTIVITY_CHANGE_FILTER = "android.net.conn.CONNECTIVITY_CHANGE";

    public NetworkBroadcastReceiver(final BaseActivity activity){
        this.activity = activity;
        this.isConnected = true;
        //Init an alert dialog to show whenever there is no network connection
        alertDialog = new AlertDialog.Builder(activity)
                .setMessage(activity.getString(R.string.no_network_connection))
                .setPositiveButton(activity.getString(R.string.try_connect_again), (DialogInterface dialog, int which) -> dialog.dismiss())
                .setCancelable(false)
                .setOnDismissListener((DialogInterface dialogInterface) -> checkNetworkConnection(activity))
                .create();

        //Init the broadcast receiver to listen to network's changes
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkNetworkConnection(context);
            }
        };
        //Specify CONNECTION_CHANGES intent to call the receiver
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_CHANGE_FILTER);
    }

    /*=================================== CHECK FOR NETWORK ======================================*/
    private boolean haveNetworkConnection(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //Check when broadcast receiver receives CONNECTIVITY_CHANGE event
    private void checkNetworkConnection(Context ctx){
        if (!haveNetworkConnection(ctx)) {
            alertDialog.show();
            isConnected = false;
        } else {
            if (!isConnected) {
                this.activity.refreshAfterLosingNetwork();
            }
        }
    }

    /*=============================== REGISTER NETWORK RECEIVER ================+===============*/
    public void registerNetworkReceiver(){
        activity.registerReceiver(networkReceiver, intentFilter);
    }

    /*============================== UNREGISTER NETWORK RECEIVER ===============================*/
    public void unregisterNetworkReceiver(){
        activity.unregisterReceiver(networkReceiver);
    }
}

