package com.lanthanh.admin.icareapp.presentation.base;

import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.threading.MainThread;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public abstract class BasePresenter implements Presenter {
    protected Executor mExecutor;
    protected MainThread mMainThread;

    public BasePresenter(){
    }

    public abstract void resume();

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }
}
