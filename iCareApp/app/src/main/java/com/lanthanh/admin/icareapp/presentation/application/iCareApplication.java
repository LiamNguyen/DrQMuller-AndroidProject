package com.lanthanh.admin.icareapp.presentation.application;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
