package com.lanthanh.admin.icareapp.utils.scheduler;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by long.vu on 4/21/2017.
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
