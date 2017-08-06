package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.databinding.Observable
import android.graphics.Typeface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lanthanh.admin.icareapp.core.app.BaseFragment


import com.lanthanh.admin.icareapp.utils.GraphicUtils

import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl
import com.lanthanh.admin.icareapp.databinding.FragmentWelcomeLoginBinding

/**
 * Created by ADMIN on 17-Oct-16.
 */

class LoginFragment : BaseFragment<WelcomeActivity, LoginViewModel>() {

    private lateinit var binding: FragmentWelcomeLoginBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeLoginBinding.inflate(inflater, container, false)
        binding.viewModel = LoginViewModel(WelcomeRepositoryImpl(activity))
        viewModel = binding.viewModel
        return binding.root
    }

    override fun setupView () {
        applyFont()

        setupSoftKeyboard()

        setupToolbar()
    }

    fun applyFont () {
        //Apply custom font for UI elements
        val font = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_LIGHT)
        listOfNotNull<TextView>(binding.inputUsername, binding.inputPassword, binding.buttonLogin).forEach { it.typeface = font }
    }

    fun setupSoftKeyboard () {
        viewModel?.showKeyboard!!.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (viewModel?.showKeyboard!!.get()) hostActivity.showSoftKeyboard(binding.inputUsername)
                else hostActivity.hideSoftKeyboard()
            }
        })
    }

    fun setupToolbar () {
        viewModel?.showToolbar!!.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                hostActivity.showToolbar(viewModel?.showToolbar!!.get())
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        viewModel?.onHiddenChange(hidden = hidden)
    }
}

