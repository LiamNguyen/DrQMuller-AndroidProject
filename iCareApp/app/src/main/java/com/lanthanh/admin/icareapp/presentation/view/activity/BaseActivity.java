package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseActivity extends AppCompatActivity {

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
}
