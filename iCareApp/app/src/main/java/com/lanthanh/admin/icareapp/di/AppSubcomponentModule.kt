package com.lanthanh.admin.icareapp.di

import com.lanthanh.admin.icareapp.di.data.RepositoryComponent
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

/**
 * @author longv
 * Created on 13-Aug-17.
 */
@Module(
    subcomponents = arrayOf(RepositoryComponent::class)
)
class AppSubcomponentModule {

    @Provides
    @IntoMap
    @ClassKey(RepositoryComponent::class)
    fun provideRepositoryComponent (builder : RepositoryComponent.Builder) : RepositoryComponent
    = builder.build()
}