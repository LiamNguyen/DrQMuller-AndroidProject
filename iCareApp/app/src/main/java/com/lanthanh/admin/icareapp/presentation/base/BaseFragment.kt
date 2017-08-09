package com.lanthanh.admin.icareapp.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * @author longv
 * * Created on 19-Mar-17.
 */

abstract class BaseFragment<T : Presenter> : Fragment() {
    abstract fun initViews()
    abstract fun refreshViews()
    abstract fun getMainPresenter() : T

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    fun showToast(msg: String) {
        val toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        toast.show()
    }
}
