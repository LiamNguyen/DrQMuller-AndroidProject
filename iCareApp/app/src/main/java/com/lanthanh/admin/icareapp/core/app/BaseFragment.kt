package com.lanthanh.admin.icareapp.core.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * @author longv
 * *         Created on 05-Aug-17.
 */

abstract class BaseFragment< out A : BaseActivity, VM : ViewModel > : Fragment() {

    protected open var viewModel : VM? = null

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

        viewModel?.pause()
    }

    fun onBackPressed () : Boolean {
        if (!isVisible) return false

        viewModel?.backPressed()

        return true
    }

    abstract fun setupView ()
}
