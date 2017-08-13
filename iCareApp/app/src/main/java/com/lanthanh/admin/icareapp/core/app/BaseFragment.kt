package com.lanthanh.admin.icareapp.core.app

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author longv
 * *         Created on 05-Aug-17.
 */

abstract class BaseFragment< out A : BaseActivity, VM : ViewModel > : Fragment() {

    protected var viewModel : VM? = null
            @Inject set

    protected val disposables: CompositeDisposable = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    val hostActivity by lazy {
        activity as A
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        viewModel?.setupView()
    }

    override fun onResume () {
        super.onResume()

        viewModel?.resume()
    }

    override fun onPause() {
        super.onPause()

        if (!disposables.isDisposed) disposables.dispose()

        viewModel?.pause()
    }

    fun onBackPressed () : Boolean = viewModel?.backPressed() ?: false

    override fun onHiddenChanged(hidden: Boolean) {
        viewModel?.hiddenChanged(hidden)
    }

    abstract fun setupView ()
}
