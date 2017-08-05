package com.lanthanh.admin.icareapp.core.app

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity

/**
 * @author longv
 * *         Created on 05-Aug-17.
 */

abstract class BaseFragment<out A : BaseActivity, VM : ViewModel> : Fragment() {

    var viewModel : VM? = null

    val hostActivity by lazy {
        activity as A
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume () {
        super.onResume()
        viewModel?.resume()
    }

    override fun onPause() {
        super.onPause()
        viewModel?.pause()
    }

    abstract fun initViews ()
}
