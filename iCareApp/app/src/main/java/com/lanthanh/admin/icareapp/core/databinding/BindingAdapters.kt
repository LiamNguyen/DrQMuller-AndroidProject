package com.lanthanh.admin.icareapp.core.databinding

import android.databinding.BindingAdapter
import android.widget.EditText
import com.lanthanh.admin.icareapp.core.extension.hideKeyboard
import com.lanthanh.admin.icareapp.core.extension.showKeyboard

/**
 * Created by long.vu on 8/17/2017.
 */
class BindingAdapters {
    @BindingAdapter("showKeyboard")
    fun showKeyboard(editText: EditText, show: Boolean) {
        if (show) editText.showKeyboard()
        else editText.hideKeyboard()
    }
}