package com.lanthanh.admin.icareapp.core.app

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.lanthanh.admin.icareapp.R
import dagger.android.AndroidInjection

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * @author longv
 * Created on 05-Aug-17.
 */
typealias GeneralBaseFragment = BaseFragment<*, *>

abstract class BaseActivity : AppCompatActivity() {

    private var fragmentCount = 0
    private val topFragment: Fragment?
        get() = supportFragmentManager.findFragmentByTag(fragmentTag(fragmentCount))

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState, persistentState)
    }

    fun <F : Fragment> showFragment (fragmentClass : KClass<F>, @LayoutRes containerId : Int = R.id.fragmentContainer) {
        // Check whether requested fragment needed to be shown is already in stack
        if (fragmentExists(fragmentClass)) {
            throw IllegalStateException("Try to show fragment that is already in stack")
        } else {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            // Set custom animation for fragment transaction
            // We will want different animations between the first added fragment and the later ones
            if (topFragment == null) fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            else fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)

            // Show requested fragment
            try {
                if (topFragment != null) fragmentTransaction.hide(topFragment)
                val fragment = fragmentClass.createInstance()
                fragmentCount++
                fragmentTransaction.add(R.id.fragmentContainer, fragment, fragmentTag(fragmentCount))
            } catch (e : Resources.NotFoundException) {
                throw RuntimeException("No container found for fragment. Please specified a correct ID for the fragment container or else make sure your layout contains a fragment container having ID: fragmentContainer")
            }

            fragmentTransaction.commit()
        }
    }

    fun closeTopFragment() {
        if (fragmentCount == 1) {
            // End this activity if there is only one fragment left
            finish()
        } else {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            // Set custom animation for fragment transaction
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)

            // Remove top fragment
            fragmentTransaction.remove(topFragment)
            fragmentCount--
            // Since previous top fragment is hidden when showing a new one, show it again
            fragmentTransaction.show(topFragment)

            fragmentTransaction.commit()
        }
    }

    private fun fragmentTag (position : Int) = "fragment#{$position}"

    fun <F : Fragment> fragmentExists(fragmentClass : KClass<F>) : Boolean {
        for (i in 0..fragmentCount) {
            val fragment = supportFragmentManager.findFragmentByTag(fragmentTag(i))
            if (fragment != null && fragmentClass.isInstance(fragment)) return true
        }
        return false
    }

    fun showActivity (activityClass : Class<AppCompatActivity>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val fragmentToBeClosed = topFragment
        if (fragmentToBeClosed is GeneralBaseFragment) {
            if (fragmentToBeClosed.onBackPressed()) return
        }

        closeTopFragment()
    }
}