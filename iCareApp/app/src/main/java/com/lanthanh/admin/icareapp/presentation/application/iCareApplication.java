package com.lanthanh.admin.icareapp.presentation.application;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;

/**
 * @author longv
 *         Created on 23-Mar-17.
 */

public class iCareApplication extends MultiDexApplication {
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
}
