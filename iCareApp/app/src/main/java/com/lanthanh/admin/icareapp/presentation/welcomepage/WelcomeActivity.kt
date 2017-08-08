package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar

import com.lanthanh.admin.icareapp.R
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity

import butterknife.BindView
import butterknife.ButterKnife
import com.lanthanh.admin.icareapp.di.ui.welcome.DaggerWelcomePageComponent
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by ADMIN on 17-Oct-16.
 */

class WelcomeActivity : BaseActivity(), WelcomeNavigator {

    var mainPresenter: WelcomeActivityPresenter? = null
        private set

    @BindView(R.id.toolbar) lateinit var toolBar: Toolbar
    @BindView(R.id.progressbar) lateinit var progressBar: ProgressBar

    @Inject lateinit var chooseFragment : ChooseFragment
    @Inject lateinit var logInFragment : LogInFragment
    @Inject lateinit var signUpFragment : SignUpFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        DaggerWelcomePageComponent.builder().build().inject(this)

        ButterKnife.bind(this)

        init()

        //Set up for Toolbar
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        loadWelcomeScreen()
    }

    fun init() {
        mainPresenter = WelcomeActivityPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        mainPresenter!!.resume()
    }

    override fun onPause() {
        super.onPause()
        mainPresenter!!.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter!!.destroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mainPresenter!!.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_KEY, CURRENT_FRAGMENT)
    }

    fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar!!.visibility = View.GONE
    }

    //Hide/show tool bar
    fun showToolbar(shouldShow: Boolean) {
        if (shouldShow)
            toolBar!!.visibility = View.VISIBLE
        else
            toolBar!!.visibility = View.GONE
    }

    fun backToHomeScreen() {
        hideProgress()
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(homeIntent)
    }

    override fun onBackPressed() {
        mainPresenter!!.onBackPressed()
    }

    override fun refreshAfterLosingNetwork() {}

    companion object {
        //public final static String TAG = RegisterActivity.class.getSimpleName();
        //TODO check used fields
        val CHOOSE_FRAGMENT = ChooseFragment::class.java.name
        val LOGIN_FRAGMENT = LogInFragment::class.java.name
        val SIGNUP_FRAGMENT = SignUpFragment::class.java.name
        var CURRENT_FRAGMENT = ""
        val CURRENT_FRAGMENT_KEY = "CurrentFragment"
    }


    fun getVisibleFragments(): List<Fragment> {
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        val result = ArrayList<Fragment>(3)

        // Add each visible fragment to the result
        if (chooseFragment.isVisible()) {
            result.add(chooseFragment)
        }
        if (logInFragment.isVisible()) {
            result.add(logInFragment)
        }
        if (signUpFragment.isVisible()) {
            result.add(signUpFragment)
        }

        return result
    }

    fun hideFragments(ft: FragmentTransaction, visibleFrags: List<Fragment>) {
        visibleFrags.forEach { ft.hide(it) }
    }

    fun showFragment(f: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        //Hide all current visible fragment
        hideFragments(fragmentTransaction, getVisibleFragments())

        if (!f.isAdded) {
            fragmentTransaction.add(R.id.wel_fragment_container, f, f.javaClass.name)
        } else {
            fragmentTransaction.show(f)
        }

        fragmentTransaction.addToBackStack(null).commit()
    }


    override fun loadLoginScreen() {
        showFragment(logInFragment)
    }

    override fun loadSignupScreen() {
        showFragment(signUpFragment)
    }

    override fun loadWelcomeScreen() {
        showFragment(chooseFragment)
    }

    override fun loadHomePage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSignupInfoPage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

