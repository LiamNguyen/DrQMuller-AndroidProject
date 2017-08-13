package com.lanthanh.admin.icareapp.core.app

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.lanthanh.admin.icareapp.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.ArrayList
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject



/**
 * @author longv
 * Created on 05-Aug-17.
 */
typealias GeneralBaseFragment = BaseFragment<*, *>

abstract class BaseActivity : AppCompatActivity() {


    private val fragments = ArrayList<GeneralBaseFragment>()
    private var fragmentCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
       // AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    fun <F : GeneralBaseFragment> showFragment1 (fragmentClass : KClass<F>, @LayoutRes containerId : Int = R.id.fragmentContainer) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right
        )

        // Hide all current visible fragment
        fragments.forEach { if (it.isVisible) fragmentTransaction.hide(it) }

        // Show the desired fragment
        var fragment = supportFragmentManager.findFragmentByTag(fragmentClass.qualifiedName)
        if (fragment == null) {
            fragment = fragmentClass.createInstance()
            fragments.add(fragment)
            try {
                fragmentTransaction.add(containerId, fragment, fragmentClass.qualifiedName)
            } catch (e : Resources.NotFoundException) {
                throw Exception("No container found for fragment. Please specified a correct ID for the fragment container or else make sure your layout contains a fragment container having ID: fragmentContainer")
            }
        } else {
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.commit()
    }

    fun <F : GeneralBaseFragment> showFragment (fragmentClass : KClass<F>, replace : Boolean = true, @LayoutRes containerId : Int = R.id.fragmentContainer) {
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

    private fun fragmentTag (position : Int) = "fragment#{$position}"

    fun topFragment () = supportFragmentManager.findFragmentByTag(fragmentTag(fragmentCount))

    fun <F : GeneralBaseFragment> findFragmentByClass (fragmentClass : KClass<F>) : Fragment? {
        for (i in 0..fragmentCount) {
            val fragment = supportFragmentManager.findFragmentByTag(fragmentTag(i))
            if (fragment != null && fragmentClass.isInstance(fragment)) return fragment
        }
        return null
    }

    fun <F : GeneralBaseFragment> findFragment (fragmentClass: KClass<F>) : Fragment? {
        for (i in 0..fragmentCount) {
            val fragment = supportFragmentManager.findFragmentByTag(fragmentTag(i))
            if (fragment != null && fragmentClass.isInstance(fragment)) return fragment
        }
        return null
    }

    fun showActivity (activityClass : KClass<AppCompatActivity>) {
        val intent = Intent(this, activityClass.java)
        startActivity(intent)
    }

//    override fun onBackPressed() {
//        // Find the current visible fragment
//        val fragment = fragments.find { it.isVisible }
//        // If onBackPressed event has been handle (fragment onBackPressed return true), do nothing
//        if (fragment?.onBackPressed() ?: false) return
//        // Else
//        super.onBackPressed()
//    }


    /**
     * This method is used for hiding soft keyboard if it is visible
     */
    fun hideSoftKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * This method is used for showing soft keyboard when needed
     */
    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}