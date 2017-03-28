package com.lanthanh.admin.icareapp.presentation.base;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public abstract class BasePresenter implements Presenter {

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
