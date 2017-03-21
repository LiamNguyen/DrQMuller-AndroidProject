package com.lanthanh.admin.icareapp.presentation.view.fragment;

import android.support.v4.app.Fragment;

import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseFragment<T extends Presenter> extends Fragment{
    public abstract T getMainPresenter();
    public abstract void initViews();
    public abstract void resetViews();
}
