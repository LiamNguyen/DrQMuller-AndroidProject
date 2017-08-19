package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.lanthanh.admin.icareapp.utils.GraphicUtils

import com.lanthanh.admin.icareapp.core.mvvm.MVVMFragment
import com.lanthanh.admin.icareapp.databinding.FragmentWelcomeWelcomeBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by ADMIN on 18-Oct-16.
 */

class WelcomeFragment : MVVMFragment<WelcomeActivity, WelcomeViewModel>() {

    private lateinit var binding: FragmentWelcomeWelcomeBinding

    override lateinit var viewModel: WelcomeViewModel
        @Inject set

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeWelcomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.viewModel.navigator = hostActivity
        return binding.root
    }

    override fun initView() {
        hostActivity.supportActionBar?.setHomeButtonEnabled(false)
        hostActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val font = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_WELCOME)
        val font_light = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_LIGHT)
        binding.welcomeText.typeface = font
        listOfNotNull<TextView>(binding.loginButton, binding.signupButton).forEach { it.typeface = font_light }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        hostActivity.supportActionBar?.setHomeButtonEnabled(false)
        hostActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}

