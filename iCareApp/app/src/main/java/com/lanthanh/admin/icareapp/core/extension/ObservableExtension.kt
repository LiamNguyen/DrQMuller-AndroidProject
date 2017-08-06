package com.lanthanh.admin.icareapp.core.extension

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.lanthanh.admin.icareapp.core.rx.ObservableBooleanRx
import com.lanthanh.admin.icareapp.core.rx.ObservableFieldRx
import io.reactivex.Observable

/**
 * Created by long.vu on 8/3/2017.
 */

fun <T> ObservableField<T>.toRxObservable () : Observable<T> {
    return ObservableFieldRx(this)
}

fun ObservableBoolean.toRxObservable () : Observable<Boolean> {
    return ObservableBooleanRx(this)
}

