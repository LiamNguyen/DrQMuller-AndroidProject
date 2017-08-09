package com.lanthanh.admin.icareapp.core.app

import android.content.Context
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
abstract class BaseActivity : AppCompatActivity() {
    private val fragments = ArrayList<Fragment>()

    fun <F : Fragment> showFragment (fragmentClass: KClass<F>) {
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
            fragmentTransaction.add(R.id.wel_fragment_container, fragment, fragmentClass.qualifiedName)
        } else {
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.addToBackStack(null).commit()
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