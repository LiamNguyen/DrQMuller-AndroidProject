package com.lanthanh.admin.icareapp.presentation.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * @author longv
 *         Created on 22-Apr-17.
 */

public abstract class BaseFragment2<T extends BasePresenter2> extends Fragment {
    private Toast toast;
    public abstract void initViews();
    public abstract void refreshViews();
    public void showMessage(String msg){
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
