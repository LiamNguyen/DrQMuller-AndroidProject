package com.lanthanh.admin.icareapp.core.extension

import android.databinding.ObservableField
import com.lanthanh.admin.icareapp.core.rx.ObservableFieldObservable
import io.reactivex.Observable

/**
 * Created by long.vu on 8/3/2017.
 */

fun <T> ObservableField<T>.toRxObservable() : Observable<T> {
    return ObservableFieldObservable(this)
}

