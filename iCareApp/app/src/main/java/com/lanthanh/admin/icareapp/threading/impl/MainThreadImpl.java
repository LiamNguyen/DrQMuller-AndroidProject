package com.lanthanh.admin.icareapp.threading.impl;

import android.os.Handler;
import android.os.Looper;

import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class MainThreadImpl implements MainThread {

    private static MainThread sMainThread;

    private Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static MainThread getInstance() {
        if (sMainThread == null) {
            sMainThread = new MainThreadImpl();
        }

        return sMainThread;
    }
}
