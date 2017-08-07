package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar

import com.lanthanh.admin.icareapp.R
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by ADMIN on 17-Oct-16.
 */

class WelcomeActivity : BaseActivity(), WelcomeNavigator {

    var mainPresenter: WelcomeActivityPresenter? = null
        private set

    @BindView(R.id.toolbar) internal var toolBar: Toolbar? = null
    @BindView(R.id.progressbar) internal var progressBar: ProgressBar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        ButterKnife.bind(this)

        init()

        //Set up for Toolbar
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            //ChooseFragment as default -> hide ToolBar
            mainPresenter!!.navigateFragment(ChooseFragment::class.java)
            CURRENT_FRAGMENT = CHOOSE_FRAGMENT
        } else {
            CURRENT_FRAGMENT = savedInstanceState.getString(CURRENT_FRAGMENT_KEY, "")
            if (CURRENT_FRAGMENT == LOGIN_FRAGMENT) {
                mainPresenter!!.navigateFragment(LogInFragment::class.java)
            } else if (CURRENT_FRAGMENT == SIGNUP_FRAGMENT) {
                mainPresenter!!.navigateFragment(SignUpFragment::class.java)
            } else {
                mainPresenter!!.navigateFragment(ChooseFragment::class.java)
            }
        }

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

    override fun loadLoginScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSignupScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadWelcomeScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadHomePage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSignupInfoPage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

