package com.lanthanh.admin.icareapp.di

import android.app.Application
import android.content.Context
import com.lanthanh.admin.icareapp.di.data.RepositoryComponent
import com.lanthanh.admin.icareapp.di.data.RepositoryModule
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import com.lanthanh.admin.icareapp.presentation.application.iCareApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author longv
 * Created on 11-Aug-17.
 */
@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModule::class,
        RepositoryModule::class
    )
)
interface AppComponent {
    fun inject(iCareApplication: iCareApplication)
}