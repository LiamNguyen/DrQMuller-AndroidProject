package com.lanthanh.admin.icareapp.utils.scheduler;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by long.vu on 4/21/2017.
 */

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {
    public ImmediateSchedulerProvider(){}

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
