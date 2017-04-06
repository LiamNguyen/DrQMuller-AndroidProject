package com.lanthanh.admin.icareapp.presentation.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.application.iCareApplication;
import com.lanthanh.admin.icareapp.presentation.broadcastreceivers.NetworkBroadcastReceiver;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public abstract class BasePresenter implements Presenter {
    private Activity activity;
    private NetworkBroadcastReceiver networkBroadcastReceiver;

    public BasePresenter(Activity activity){
        this.activity = activity;
        this.networkBroadcastReceiver = new NetworkBroadcastReceiver(this.activity);
    }

    @Override
    public void resume() {
        this.networkBroadcastReceiver.registerNetworkReceiver();
    }

    @Override
    public void pause() {
        this.networkBroadcastReceiver.unregisterNetworkReceiver();
    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    public ApplicationProvider getProvider() {
        return ((iCareApplication) this.activity.getApplication()).getProvider();
    }

    public void navigateActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this.activity, activityClass);
        this.activity.startActivity(intent);
        if (!(this.activity instanceof MainActivity))
            this.activity.finish();
    }

    public void navigateActivity(Class<? extends Activity> activityClass, Bundle b) {
        Intent intent = new Intent(this.activity, activityClass);
        intent.putExtra(this.getClass().getName(), b); //TODO check this put extra
        this.activity.startActivity(intent);
        if (!(this.activity instanceof MainActivity))
            this.activity.finish();
    }
}
