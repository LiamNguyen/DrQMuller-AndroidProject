package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.databinding.ObservableField
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import com.lanthanh.admin.icareapp.utils.asObservable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo

/**
 * Created by long.vu on 8/3/2017.
 */
class LoginViewModel (val welcomeRepository: WelcomeRepository) {
    val username : ObservableField<String> =  ObservableField()
    val password : ObservableField<String> = ObservableField()
    val enableLogin : ObservableField<Boolean> = ObservableField()
    val disposables : CompositeDisposable = CompositeDisposable()

    fun resume () {
        bindRx()
    }

    fun bindRx () {
        Observable.combineLatest(
            username.asObservable().map { username -> username.isNotEmpty() },
            username.asObservable().map { password -> password.isNotEmpty() },
            BiFunction<Boolean, Boolean, Boolean> { validUsername, validPassword -> validUsername && validPassword })
        .subscribe(enableLogin::set)
        .addTo(disposables)
    }
}