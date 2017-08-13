package com.lanthanh.admin.icareapp.di

import android.content.Context
import com.lanthanh.admin.icareapp.di.data.RepositoryComponent
import com.lanthanh.admin.icareapp.di.data.RepositoryModule
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import dagger.Component
import javax.inject.Singleton

/**
 * @author longv
 * Created on 11-Aug-17.
 */
@Singleton
@Component(
    modules = arrayOf(
        AppModule::class,
        ViewModule::class,
        RepositoryModule::class
    )
)
interface AppComponent {
    fun welcomeRepository () : WelcomeRepository
}