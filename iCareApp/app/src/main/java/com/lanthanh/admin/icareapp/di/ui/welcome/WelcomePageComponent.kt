package com.lanthanh.admin.icareapp.di.ui.welcome

import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivity
import dagger.Component

/**
 * @author longv
 * Created on 08-Aug-17.
 */
@Component
interface WelcomePageComponent {
    fun inject (activity : WelcomeActivity)
}