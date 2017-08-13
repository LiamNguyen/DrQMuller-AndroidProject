package com.lanthanh.admin.icareapp.di

import com.lanthanh.admin.icareapp.presentation.welcomepage.ChooseFragment
import com.lanthanh.admin.icareapp.presentation.welcomepage.LoginFragment
import com.lanthanh.admin.icareapp.presentation.welcomepage.SignUpFragment
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author longv
 * Created on 13-Aug-17.
 */
@Module
abstract class ViewModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeWelcomeActivityInjector() : WelcomeActivity

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeChooseFragmentInjector() : ChooseFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeLoginFragmentInjector() : LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSignupFragmentInjector() : SignUpFragment


}