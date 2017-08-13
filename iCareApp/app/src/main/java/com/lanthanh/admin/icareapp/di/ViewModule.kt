package com.lanthanh.admin.icareapp.di

import com.lanthanh.admin.icareapp.presentation.welcomepage.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author longv
 * Created on 13-Aug-17.
 */
@Module
abstract class ViewModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeLoginFragmentInjector() : LoginFragment
}