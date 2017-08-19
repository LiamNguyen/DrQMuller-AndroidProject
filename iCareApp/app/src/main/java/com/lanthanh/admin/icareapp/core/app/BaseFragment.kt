package com.lanthanh.admin.icareapp.core.app

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lanthanh.admin.icareapp.core.mvvm.MVVMView
import com.lanthanh.admin.icareapp.core.mvvm.MVVMViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable

/**
 * @author longv
 * *         Created on 05-Aug-17.
 */

abstract class BaseFragment<out A : BaseActivity> : Fragment() {

    @Suppress("UNCHECKED_CAST")
    val hostActivity by lazy {
        activity as A
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun onBackPressed() : Boolean = false

    /**
     * This method is only be called once to initialize view after it is created.
     * Setup UI events, adapter, font, etc. should be placed here.
     */
    abstract fun initView()
}
