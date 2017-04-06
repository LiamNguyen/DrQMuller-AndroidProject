package com.lanthanh.admin.icareapp.presentation.broadcastreceivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;

/**
 * Created by ADMIN on 18-Dec-16.
 */

public class NetworkBroadcastReceiver {
    private Activity activity;
    private BroadcastReceiver networkReceiver;
    private IntentFilter intentFilter;
    private AlertDialog alertDialog;
    private boolean isConnected;
    private final String CONNECTIVITY_CHANGE_FILTER = "android.net.conn.CONNECTIVITY_CHANGE";

    public NetworkBroadcastReceiver(final Activity activity){
        //Assign current activity
        this.activity = activity;
        //Init check connection variable
        isConnected = false;
        //Init an alert dialog to show whenever there is no network connection
        alertDialog = new AlertDialog.Builder(activity)
                .setMessage(activity.getString(R.string.no_network_connection))
                .setPositiveButton(activity.getString(R.string.try_connect_again), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        checkNetworkConnection(activity);
                    }
                }).create();

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

//    //Check when activity starts
//    public void checkNetworkConnection(){
//        if (!haveNetworkConnection(activity)){


//            if (!alertDialog.isShowing())
//                noNetworkConnectionAlertShow();
//        }
//    }

    //Check when broadcast receiver receives CONNECTIVITY_CHANGE event
    private void checkNetworkConnection(Context ctx){
        if (!haveNetworkConnection(ctx))
            alertDialog.show();
        else{
            if (activity instanceof MainActivity)
                ((MainActivity) activity).refreshAfterNetworkConnected();
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

