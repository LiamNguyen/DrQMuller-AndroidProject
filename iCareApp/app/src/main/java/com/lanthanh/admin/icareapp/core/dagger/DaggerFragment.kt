package com.lanthanh.admin.icareapp.core.dagger

import android.content.Context
import android.support.v4.app.Fragment
import com.lanthanh.admin.icareapp.core.app.BaseFragment
import com.lanthanh.admin.icareapp.core.mvvm.MVVMViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author longv
 * Created on 13-Aug-17.
 */
open class DaggerFragment : Fragment(), HasSupportFragmentInjector {
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}