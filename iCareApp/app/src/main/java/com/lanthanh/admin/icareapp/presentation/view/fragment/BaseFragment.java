package com.lanthanh.admin.icareapp.presentation.view.fragment;

import android.support.v4.app.Fragment;

import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseFragment<T extends Presenter> extends Fragment{
    public abstract void initViews();
    public abstract void resetViews();
    public abstract T getMainPresenter();
}
