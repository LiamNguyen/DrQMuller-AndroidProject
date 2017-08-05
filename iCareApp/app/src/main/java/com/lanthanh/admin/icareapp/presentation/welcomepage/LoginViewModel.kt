package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import com.lanthanh.admin.icareapp.core.extension.toRxObservable
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus
import com.lanthanh.admin.icareapp.presentation.base.BaseViewModel
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
    val username : ObservableField<String> =  ObservableField() // Observable value for username input.
    val password : ObservableField<String> = ObservableField() // Observable value for password input.
    val enableLogin : ObservableBoolean = ObservableBoolean(false) // Observable value for login button.

    override fun resume () {
        // Only when username and password are valid that button is enabled
        Observable.combineLatest(
            username.toRxObservable().map { username -> username.isNotEmpty() },
            password.toRxObservable().map { password -> password.isNotEmpty() },
            BiFunction<Boolean, Boolean, Boolean> { validUsername, validPassword -> validUsername && validPassword })
        .distinctUntilChanged()
        .subscribe(enableLogin::set)
        .addTo(disposables)
    }

    fun login () {
        welcomeRepository.login(username.get(), password.get())
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
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