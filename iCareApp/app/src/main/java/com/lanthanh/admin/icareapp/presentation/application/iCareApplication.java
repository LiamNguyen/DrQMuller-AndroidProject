package com.lanthanh.admin.icareapp.presentation.application;

import android.app.Application;

/**
 * @author longv
 *         Created on 23-Mar-17.
 */

public class iCareApplication extends Application {
    private ApplicationProvider provider;

    @Override
    public void onCreate() {
        super.onCreate();

        provider = new ApplicationProvider();
    }

    public ApplicationProvider getProvider() {
        return provider;
    }
}
