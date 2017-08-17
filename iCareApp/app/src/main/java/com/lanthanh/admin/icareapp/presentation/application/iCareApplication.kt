package com.lanthanh.admin.icareapp.presentation.application

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import com.lanthanh.admin.icareapp.di.AppComponent
import com.lanthanh.admin.icareapp.di.AppModule
import com.lanthanh.admin.icareapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author longv
 * *         Created on 23-Mar-17.
 */

class iCareApplication : DaggerApplication() {
    @Inject lateinit var applicationInjector: DispatchingAndroidInjector<iCareApplication>
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    val packageInfo: PackageInfo?
        get() {
            try {
                return packageManager.getPackageInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return null
        }

    val versionName: String
        get() {
            if (packageInfo != null) {
                return "v" + packageInfo!!.versionName
            }
            return ""
        }

    val versionCode: Int
        get() = packageInfo?.versionCode ?: -1


    private var appComponent: AppComponent? = null

    override fun onCreate() {
        setupAppComponent()
        super.onCreate()
//        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
//            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
//                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            }
//
//            override fun onActivityStarted(activity: Activity) {}
//            override fun onActivityResumed(activity: Activity) {}
//            override fun onActivityPaused(activity: Activity) {}
//            override fun onActivityStopped(activity: Activity) {}
//            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
//            override fun onActivityDestroyed(activity: Activity) {}
//        })

    }



    fun setupAppComponent () {
        appComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(this))
                        .build()
        appComponent?.inject(this)
    }
}
