package com.lanthanh.admin.icareapp.presentation.base;

/**
 * Created by ADMIN on 02-Jan-17.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
