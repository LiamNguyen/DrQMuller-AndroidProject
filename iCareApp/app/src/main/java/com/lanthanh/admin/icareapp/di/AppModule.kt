package com.lanthanh.admin.icareapp.di

import android.app.Application
import android.content.Context
import com.lanthanh.admin.icareapp.di.data.RepositoryComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author longv
 * Created on 11-Aug-17.
 */
@Module
class AppModule (val application : Application) {

    @Singleton
    @Provides
    fun provideContext () : Context = application.applicationContext
}