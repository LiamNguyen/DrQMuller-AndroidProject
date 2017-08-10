package com.lanthanh.admin.icareapp.core.app

import io.reactivex.disposables.CompositeDisposable

/**
 * @author longv
 * Created on 03-Aug-17.
 */
interface ViewModel {
    fun resume () // Called in view's OnResume()
    fun pause () // Called in view's OnPause()
    fun setupView ()
    fun backPressed () : Boolean
    fun hiddenChanged (hidden : Boolean) // Called if view is a fragment
}