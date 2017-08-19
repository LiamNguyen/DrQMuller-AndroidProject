package com.lanthanh.admin.icareapp.presentation.welcomepage

import com.lanthanh.admin.icareapp.core.app.BaseViewModel
import com.lanthanh.admin.icareapp.di.FragmentScope
import javax.inject.Inject

/**
 * Created by kidsuke on 8/19/17.
 */
@FragmentScope
class WelcomeViewModel @Inject constructor() : BaseViewModel() {

    // Helper navigator for view model
    var navigator : WelcomeNavigator? = null

    override fun setupView() {}

    fun loadLoginScreen() {
        navigator?.loadLoginScreen()
    }

    fun loadSignupScreen() {
        navigator?.loadSignupScreen()
    }
}