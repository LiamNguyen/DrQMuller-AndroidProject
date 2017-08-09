package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import com.lanthanh.admin.icareapp.core.extension.toRxObservable
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus
import com.lanthanh.admin.icareapp.core.app.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by long.vu on 8/3/2017.
 */
class LoginViewModel (val welcomeRepository: WelcomeRepository) : BaseViewModel() {

    val username : ObservableField<String> =  ObservableField() // Binding property (two-way) for username input.
    val password : ObservableField<String> = ObservableField() // Binding property (two-way) for password input.
    val enableLogin : ObservableBoolean = ObservableBoolean() // Binding property for login button.

    override fun resume () {
        // Only when username and password are valid that button is enabled
        Observable.combineLatest(
            username.toRxObservable().map { username -> username.isNotEmpty() },
            password.toRxObservable().map { password -> password.isNotEmpty() },
            BiFunction<Boolean, Boolean, Boolean> { validUsername, validPassword -> validUsername && validPassword })
        .distinctUntilChanged()
        .subscribeBy(onNext = enableLogin::set)
        .addTo(disposables)
    }

    override fun setupView () {
        enableLogin.set(false)
    }

    fun login () {
        welcomeRepository.login(username.get(), password.get())
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .subscribeBy(
            onError = { print("error") } ,
            onSuccess = {
                when (it) {
                    RepositorySimpleStatus.SUCCESS -> print("Success")
                    RepositorySimpleStatus.PATTERN_FAIL -> print("Invalid username password")
                    RepositorySimpleStatus.USERNAME_PASSWORD_NOT_MATCH -> print("Username password not match")
                    else -> {
                        print("Unknown status")
                    }
                }
            }
        )
        .addTo(disposables)
    }
}