package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import com.lanthanh.admin.icareapp.core.extension.toRxObservable
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus
import com.lanthanh.admin.icareapp.core.app.BaseViewModel
import com.lanthanh.admin.icareapp.di.FragmentScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by long.vu on 8/3/2017.
 */
@FragmentScope
class LoginViewModel @Inject constructor (val welcomeRepository: WelcomeRepository) : BaseViewModel() {

    // Binding properties
    val username : ObservableField<String> =  ObservableField() // Binding property (two-way) for username input.
    val password : ObservableField<String> = ObservableField() // Binding property (two-way) for password input.
    val enableLogin : ObservableBoolean = ObservableBoolean() // Binding property for login button.
    val showToolbar : ObservableBoolean = ObservableBoolean() // Binding property for toolbar.
    val showKeyboard : ObservableBoolean = ObservableBoolean() // Binding property for soft keyboard.

    // LoginViewModel's current state
    var validAccount : Boolean = false // Account is not valid by default
    var visible : Boolean = true // When view model is instantiated for the first time, view is visible by default

    // Helper navigator for view model
    var navigator : WelcomeNavigator? = null

    override fun resume () {
        // Only when username and password are valid that button is enabled
        Observable.combineLatest(
            username.toRxObservable().map { username -> username.isNotEmpty() },
            password.toRxObservable().map { password -> password.isNotEmpty() },
            BiFunction<Boolean, Boolean, Boolean> { validUsername, validPassword -> validUsername && validPassword })
        .distinctUntilChanged()
        .subscribeBy(
            onNext = {
                validAccount = it
                setupView()
            }
        )
        .addTo(disposables)
    }

    override fun setupView () {
        showToolbar.set(visible)
        showKeyboard.set(visible)
        enableLogin.set(validAccount)
    }

    override fun backPressed () : Boolean {
        navigator?.loadWelcomeScreen()
        return true
    }

    override fun hiddenChanged(hidden: Boolean) {
        visible = !hidden
        setupView()
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