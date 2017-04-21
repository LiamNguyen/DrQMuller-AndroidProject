package com.lanthanh.admin.icareapp.presentation.base;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by long.vu on 4/21/2017.
 */

public class AbstractPresenter2 implements BasePresenter {
    @NonNull protected CompositeDisposable mDisposables;

    protected AbstractPresenter2() {
        mDisposables = new CompositeDisposable();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
        if (!mDisposables.isDisposed())
            mDisposables.dispose();
    }
}
