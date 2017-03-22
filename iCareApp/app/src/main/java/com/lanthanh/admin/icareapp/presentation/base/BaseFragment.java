package com.lanthanh.admin.icareapp.presentation.base;

import android.support.v4.app.Fragment;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseFragment<T extends Presenter> extends Fragment{
    public abstract void initViews();
    public abstract void resetViews();
    public abstract T getMainPresenter();
}
