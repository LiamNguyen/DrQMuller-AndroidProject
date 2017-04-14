package com.lanthanh.admin.icareapp.presentation.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.application.iCareApplication;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Toast toast;

    public abstract void refreshAfterLosingNetwork();

    public ApplicationProvider getProvider() {
        return ((iCareApplication) getApplication()).getProvider();
    }

    /**
     * This method is used for hiding soft keyboard if it is visible
     */
    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * This method is used for showing soft keyboard when needed
     */
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void showToast(String msg){
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
