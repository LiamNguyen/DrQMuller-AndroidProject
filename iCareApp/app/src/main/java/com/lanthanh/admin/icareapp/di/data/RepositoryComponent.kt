package com.lanthanh.admin.icareapp.di.data

import dagger.Subcomponent

/**
 * @author longv
 * Created on 11-Aug-17.
 */

@Subcomponent(
    modules = arrayOf(RepositoryModule::class)
)
interface RepositoryComponent {
    @Subcomponent.Builder
    interface Builder {
        fun requestRepositoryBuilder (repositoryModule: RepositoryModule) : Builder
        fun build () : RepositoryComponent
    }
}