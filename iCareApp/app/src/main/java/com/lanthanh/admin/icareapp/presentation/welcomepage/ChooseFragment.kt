package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.lanthanh.admin.icareapp.R

import com.lanthanh.admin.icareapp.core.app.BaseFragment
import com.lanthanh.admin.icareapp.utils.GraphicUtils

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.lanthanh.admin.icareapp.core.mvvm.MVVMFragment
import com.lanthanh.admin.icareapp.core.mvvm.MVVMViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by ADMIN on 18-Oct-16.
 */

class ChooseFragment : MVVMFragment<WelcomeActivity, LoginViewModel>() {
    @BindView(R.id.wel_log_in_button) lateinit var logInButton: AppCompatButton
    @BindView(R.id.wel_sign_up_button) lateinit var signUpButton: AppCompatButton
    @BindView(R.id.wel_text) lateinit var welcomeText: TextView
    private var unbinder: Unbinder? = null

    override lateinit var viewModel: LoginViewModel
    @Inject set

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_register_choose, container, false)
        unbinder = ButterKnife.bind(this, view)
        val font = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_WELCOME)
        val font_light = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_LIGHT)
        welcomeText!!.typeface = font
        signUpButton!!.typeface = font_light
        logInButton!!.typeface = font_light
        logInButton!!.setOnClickListener { view -> hostActivity.showFragment(LoginFragment::class) }
        return view
    }

    override fun setupView() {
        hostActivity.supportActionBar?.setHomeButtonEnabled(false)
        hostActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }
}

