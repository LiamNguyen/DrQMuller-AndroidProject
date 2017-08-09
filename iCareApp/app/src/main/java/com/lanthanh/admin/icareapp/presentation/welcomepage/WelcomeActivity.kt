package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

import com.lanthanh.admin.icareapp.R

import butterknife.BindView
import butterknife.ButterKnife
import com.lanthanh.admin.icareapp.core.app.BaseActivity

/**
 * Created by ADMIN on 17-Oct-16.
 */

class WelcomeActivity : BaseActivity(), WelcomeNavigator {

    @BindView(R.id.toolbar) lateinit var toolBar: Toolbar

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
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //Hide/show tool bar
    fun showToolbar(shouldShow: Boolean = true) {
        toolBar.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    fun backToHomeScreen() {
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
        //showFragment(SignUpFragment::class)
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

