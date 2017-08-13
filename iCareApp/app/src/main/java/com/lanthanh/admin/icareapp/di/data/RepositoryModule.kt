package com.lanthanh.admin.icareapp.di.data

import android.content.Context
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @author longv
 * Created on 11-Aug-17.
 */
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideWelcomeRepository (impl : WelcomeRepositoryImpl) : WelcomeRepository
}