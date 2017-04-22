package com.lanthanh.admin.icareapp.presentation.base;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by long.vu on 4/21/2017.
 */

public abstract class AbstractPresenter2 implements BasePresenter2 {
    @NonNull protected CompositeDisposable mDisposables;

    protected AbstractPresenter2() {
        mDisposables = new CompositeDisposable();
    }

    public abstract void subscribe();

    @Override
    public void unsubscribe() {
        if (!mDisposables.isDisposed()) mDisposables.dispose();
    }
}
