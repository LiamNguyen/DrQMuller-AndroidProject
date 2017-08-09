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


import butterknife.BindView
import butterknife.ButterKnife
import com.lanthanh.admin.icareapp.core.app.BaseActivity
import com.lanthanh.admin.icareapp.di.ui.welcome.DaggerWelcomePageComponent
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by ADMIN on 17-Oct-16.
 */

class WelcomeActivity : BaseActivity(), WelcomeNavigator {

    @BindView(R.id.toolbar) lateinit var toolBar: Toolbar
    @BindView(R.id.progressbar) lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        ButterKnife.bind(this)

        setupToolbar()

        loadWelcomeScreen()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun setupToolbar () {
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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

    }

    override fun loadLoginScreen() {
        showFragment(LoginFragment::class)
    }

    override fun loadSignupScreen() {
        showFragment(SignUpFragment::class)
    }

    override fun loadWelcomeScreen() {
        showFragment(ChooseFragment::class)
    }

    override fun loadHomePage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSignupInfoPage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

