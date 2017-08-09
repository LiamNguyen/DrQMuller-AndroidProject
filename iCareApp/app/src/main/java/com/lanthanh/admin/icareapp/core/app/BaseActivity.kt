package com.lanthanh.admin.icareapp.core.app

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.lanthanh.admin.icareapp.R
import java.util.ArrayList
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * @author longv
 * Created on 05-Aug-17.
 */
typealias GeneralBaseFragment = BaseFragment<*, *>

abstract class BaseActivity : AppCompatActivity() {
    private val fragments = ArrayList<GeneralBaseFragment>()

    fun <F : GeneralBaseFragment> showFragment (fragmentClass : KClass<F>, @LayoutRes containerId : Int = R.id.fragmentContainer) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/

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
        fragmentTransaction.addToBackStack(null).commit()
    }

    fun showActivity (activityClass : KClass<AppCompatActivity>) {
        val intent = Intent(this, activityClass.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        fragments.forEach {
            // If onBackPressed event has been handle (fragment onBackPressed return true)
            if (it.onBackPressed()) return
        }
        // Else finish current activity
        finish()
    }

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