package com.lanthanh.admin.icareapp.presentation.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment{
    public abstract void initViews();
    public abstract void refreshViews();
    public abstract T getMainPresenter();
    public void showToast(String msg){
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
