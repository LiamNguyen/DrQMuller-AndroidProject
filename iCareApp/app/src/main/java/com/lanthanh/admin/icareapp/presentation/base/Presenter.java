package com.lanthanh.admin.icareapp.presentation.base;

/**
 * Created by ADMIN on 31-Dec-16.
 */

public interface Presenter {
    void resume();
    void pause();
    void stop();
    void destroy();
    void onError(String message);
}
