package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.databinding.Observable
import android.graphics.Typeface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lanthanh.admin.icareapp.core.app.BaseFragment
import com.lanthanh.admin.icareapp.core.extension.toRxObservable


import com.lanthanh.admin.icareapp.utils.GraphicUtils

import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl
import com.lanthanh.admin.icareapp.databinding.FragmentWelcomeLoginBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by ADMIN on 17-Oct-16.
 */

class LoginFragment : BaseFragment<WelcomeActivity, LoginViewModel>() {

    private lateinit var binding: FragmentWelcomeLoginBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeLoginBinding.inflate(inflater, container, false)
        binding.viewModel = LoginViewModel(WelcomeRepositoryImpl(activity))
        viewModel = binding.viewModel
        viewModel!!.navigator = hostActivity
        return binding.root
    }

    override fun setupView () {
        applyFont()

        hostActivity.showSoftKeyboard(binding.inputUsername)

        hostActivity.showToolbar(true)
    }

    fun applyFont () {
        //Apply custom font for UI elements
        val font = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_LIGHT)
        listOfNotNull<TextView>(binding.inputUsername, binding.inputPassword, binding.buttonLogin).forEach { it.typeface = font }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        val visible = !hidden

        if (visible) hostActivity.showSoftKeyboard(binding.inputUsername)
        else hostActivity.hideSoftKeyboard()

        hostActivity.showToolbar(visible)
    }
}

