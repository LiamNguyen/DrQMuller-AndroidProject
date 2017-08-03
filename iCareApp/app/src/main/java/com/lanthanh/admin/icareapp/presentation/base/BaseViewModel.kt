package com.lanthanh.admin.icareapp.presentation.base

import io.reactivex.disposables.CompositeDisposable

/**
 * @author longv
 * Created on 03-Aug-17.
 */
abstract class BaseViewModel : ViewModel {
    override val disposables: CompositeDisposable = CompositeDisposable()

    override abstract fun resume()

    override fun pause() {
        if (!disposables.isDisposed) disposables.dispose()
    }
}