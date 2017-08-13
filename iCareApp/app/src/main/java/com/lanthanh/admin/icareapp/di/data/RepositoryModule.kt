package com.lanthanh.admin.icareapp.di.data

import android.content.Context
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author longv
 * Created on 11-Aug-17.
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWelcomeRepository (context : Context) : WelcomeRepository = WelcomeRepositoryImpl(context)
}