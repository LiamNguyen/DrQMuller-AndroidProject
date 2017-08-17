package com.lanthanh.admin.icareapp.core.mvvm

import io.reactivex.disposables.CompositeDisposable

/**
 * @author longv
 * Created on 03-Aug-17.
 */
interface MVVMViewModel {
    fun create() // Called when view model is first created
    fun resume() // Called when view resumes
    fun pause() // Called when view pauses
    fun setupView()
    fun backPressed() : Boolean = false
    fun hiddenChanged(hidden : Boolean) = {} // Called if view is a fragment
}