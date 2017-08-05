package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.graphics.Typeface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.lanthanh.admin.icareapp.presentation.base.BaseFragment
import com.lanthanh.admin.icareapp.utils.GraphicUtils

import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl
import com.lanthanh.admin.icareapp.databinding.FragmentWelcomeLoginBinding

/**
 * Created by ADMIN on 17-Oct-16.
 */

class LoginFragment : BaseFragment<WelcomeActivityPresenter>() {

    private lateinit var binding: FragmentWelcomeLoginBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeLoginBinding.inflate(inflater, container, false)
        binding.viewModel = LoginViewModel(WelcomeRepositoryImpl(activity))
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.viewModel.pause()
    }

    override fun initViews() {
        (activity as WelcomeActivity).showToolbar(true)

        //Apply custom font for UI elements
        val font = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_LIGHT)
        listOfNotNull<TextView>(binding.inputUsername, binding.inputPassword, binding.buttonLogin).forEach { it.typeface = font }
    }

    override fun refreshViews() {
        binding.inputUsername.text.clear()
        binding.inputPassword.text.clear()
    }

    override fun getMainPresenter(): WelcomeActivityPresenter {
        return (activity as WelcomeActivity).mainPresenter
    }

    override fun onStart() {
        super.onStart()
        (activity as WelcomeActivity).showSoftKeyboard(binding.inputUsername)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden && isVisible) {
            (activity as WelcomeActivity).showToolbar(true)
            (activity as WelcomeActivity).showSoftKeyboard(binding.inputUsername)
        } else
            refreshViews()
    }
}

