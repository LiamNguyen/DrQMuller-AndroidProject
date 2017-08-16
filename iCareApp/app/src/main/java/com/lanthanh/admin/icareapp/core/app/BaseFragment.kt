package com.lanthanh.admin.icareapp.core.app

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.lanthanh.admin.icareapp.core.mvvm.MVVMView
import com.lanthanh.admin.icareapp.core.mvvm.MVVMViewModel
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        viewModel.setupView()
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

    /**
     * This method is used for hiding soft keyboard if it is visible
     */
    fun hideSoftKeyboard() {
        val view = hostActivity.currentFocus
        if (view != null) {
            val imm = hostActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * This method is used for showing soft keyboard when needed
     */
    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = hostActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
