package com.lanthanh.admin.icareapp.presentation.welcomepage

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


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_register_choose, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    //    @Override
    //    public void initViews(){
    //        ((WelcomeActivity) getActivity()).showToolbar(false);
    //        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_WELCOME);
    //        Typeface font_light = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
    //        welcomeText.setTypeface(font);
    //        signUpButton.setTypeface(font_light);
    //        logInButton.setTypeface(font_light);
    //        logInButton.setOnClickListener(view ->  getMainPresenter().navigateFragment(LoginFragment.class));
    //        signUpButton.setOnClickListener(view -> getMainPresenter().navigateFragment(SignUpFragment.class));
    //    }

    override fun setupView() {
        (activity as WelcomeActivity).showToolbar(false)
        val font = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_WELCOME)
        val font_light = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_LIGHT)
        welcomeText!!.typeface = font
        signUpButton!!.typeface = font_light
        logInButton!!.typeface = font_light
        logInButton!!.setOnClickListener { view -> hostActivity.showFragment(LoginFragment::class) }
        //signUpButton!!.setOnClickListener { view -> hostActivity.showFragment(SignUpFragment::class) }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden && isVisible)
            (activity as WelcomeActivity).showToolbar(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }
}

