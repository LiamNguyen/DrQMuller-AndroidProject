package com.lanthanh.admin.icareapp.core.app

import io.reactivex.disposables.CompositeDisposable

/**
 * @author longv
 * Created on 03-Aug-17.
 */
abstract class BaseViewModel : ViewModel {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    override abstract fun resume ()

    override fun pause () {
        if (!disposables.isDisposed) disposables.dispose()
    }

    override fun backPressed() {}

    override abstract fun setupView ()
}