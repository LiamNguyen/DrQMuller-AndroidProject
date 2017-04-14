package com.lanthanh.admin.icareapp.presentation.application;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * @author longv
 *         Created on 23-Mar-17.
 */

public class iCareApplication extends Application{
    private ApplicationProvider provider;

    @Override
    public void onCreate() {
        super.onCreate();
        provider = new ApplicationProvider();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override public void onActivityStarted(Activity activity) {}
            @Override public void onActivityResumed(Activity activity) {}
            @Override public void onActivityPaused(Activity activity) {}
            @Override public void onActivityStopped(Activity activity) {}
            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override public void onActivityDestroyed(Activity activity) {}
        });
    }

    public ApplicationProvider getProvider() {
        return provider;
    }

    public PackageInfo getPackageInfo() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVersionName() {
        if (getPackageInfo() != null) {
            return "v" + getPackageInfo().versionName;
        }
        return "";
    }

    public int getVersionCode() {
        if (getPackageInfo() != null) {
            return getPackageInfo().versionCode;
        }
        return -1;
    }
}
