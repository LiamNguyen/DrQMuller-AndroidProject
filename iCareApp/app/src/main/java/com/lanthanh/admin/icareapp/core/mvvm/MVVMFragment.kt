package com.lanthanh.admin.icareapp.core.mvvm

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lanthanh.admin.icareapp.core.app.BaseActivity
import com.lanthanh.admin.icareapp.core.app.BaseFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by long.vu on 8/18/2017.
 */
abstract class MVVMFragment<out A : BaseActivity, out VM : MVVMViewModel> : BaseFragment<A>(), MVVMView<VM> {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onBackPressed () : Boolean = viewModel.backPressed()

    override fun onHiddenChanged(hidden: Boolean) {
        viewModel.hiddenChanged(hidden)
    }

    override abstract fun setupView ()
}