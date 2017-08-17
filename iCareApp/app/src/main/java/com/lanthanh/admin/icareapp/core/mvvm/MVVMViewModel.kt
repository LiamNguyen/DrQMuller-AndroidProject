package com.lanthanh.admin.icareapp.core.mvvm

import io.reactivex.disposables.CompositeDisposable

/**
 * @author longv
 * Created on 03-Aug-17.
 */
interface MVVMViewModel {
    fun resume () // Called in view's OnResume()
    fun pause () // Called in view's OnPause()
    fun setupView ()
    fun backPressed () : Boolean = false
    fun hiddenChanged (hidden : Boolean) = {} // Called if view is a fragment
}