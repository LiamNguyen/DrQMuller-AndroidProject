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

abstract class BaseFragment<out A : BaseActivity, out VM : MVVMViewModel> : Fragment(), MVVMView<VM> {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    val hostActivity by lazy {
        activity as A
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        viewModel.create()
    }

    override fun onResume () {
        super.onResume()

        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()

        if (!disposables.isDisposed) disposables.dispose()

        viewModel.pause()
    }

    fun onBackPressed () : Boolean = viewModel.backPressed()

    override fun onHiddenChanged(hidden: Boolean) {
        viewModel.hiddenChanged(hidden)
    }

    abstract fun setupView ()
}
