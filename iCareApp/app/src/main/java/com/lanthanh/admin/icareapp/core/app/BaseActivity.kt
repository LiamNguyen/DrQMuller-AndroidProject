package com.lanthanh.admin.icareapp.core.app

import android.content.Intent
import android.content.res.Resources
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.lanthanh.admin.icareapp.R

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * @author longv
 * Created on 05-Aug-17.
 */
typealias GeneralBaseFragment = BaseFragment<*, *>

abstract class BaseActivity : AppCompatActivity() {

    private var fragmentCount = 0
    private var topFragment = supportFragmentManager.findFragmentByTag(fragmentTag(fragmentCount))

    fun <F : GeneralBaseFragment> showFragment (fragmentClass : KClass<F>, @LayoutRes containerId : Int = R.id.fragmentContainer) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_right, R.anim.slide_out_left,
            R.anim.slide_in_left, R.anim.slide_out_right
        )

        // Check whether fragment needed to be shown is already in stack
        if (findFragmentByClass(fragmentClass) != null) {
            throw IllegalStateException("Try to show fragment that already in stack")
        } else {
            try {
                fragmentCount++
                val fragment = fragmentClass.createInstance()
                fragmentTransaction.add(containerId, fragment, fragmentTag(fragmentCount))
            } catch (e : Resources.NotFoundException) {
                throw RuntimeException("No container found for fragment. Please specified a correct ID for the fragment container or else make sure your layout contains a fragment container having ID: fragmentContainer")
            }
        }
        fragmentTransaction.commit()
    }

    fun closeTopmostFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right
        )

        fragmentTransaction.remove(topFragment)
        fragmentCount--

        fragmentTransaction.commit()
    }

    private fun fragmentTag (position : Int) = "fragment#{$position}"

    fun <F : GeneralBaseFragment> findFragmentByClass (fragmentClass : KClass<F>) : Fragment? {
        for (i in 0..fragmentCount) {
            val fragment = supportFragmentManager.findFragmentByTag(fragmentTag(i))
            if (fragment != null && fragmentClass.isInstance(fragment)) return fragment
        }
        return null
    }

    fun showActivity (activityClass : Class<AppCompatActivity>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (fragmentCount > 1) {
            val topFragment = topFragment
            if (topFragment is GeneralBaseFragment) {
                if (topFragment.onBackPressed()) return
            }

            closeTopmostFragment()
        } else {
            super.onBackPressed()
        }
    }
}