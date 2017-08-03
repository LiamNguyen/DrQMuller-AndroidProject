package com.lanthanh.admin.icareapp.presentation.welcomepage

import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.jakewharton.rxbinding2.widget.RxTextView
import com.lanthanh.admin.icareapp.R
import com.lanthanh.admin.icareapp.databinding.FragmentWelcomeLoginBinding
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment
import com.lanthanh.admin.icareapp.utils.GraphicUtils
import com.lanthanh.admin.icareapp.utils.StringUtils

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by ADMIN on 17-Oct-16.
 */

class LogInFragment : BaseFragment<WelcomeActivityPresenter>() {

    private val editTextDisposable: Disposable? = null
    private var binding: FragmentWelcomeLoginBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeLoginBinding.inflate(inflater, container, false)

        initViews()


        return binding!!.root
    }

    override fun initViews() {
        (activity as WelcomeActivity).showToolbar(true)

        //Apply custom font for UI elements
        val font = Typeface.createFromAsset(activity.assets, GraphicUtils.FONT_LIGHT)
        //        logInButton.setTypeface(font);
        //        editUsername.setTypeface(font);
        //        editPassword.setTypeface(font);
        //        editUsernameContainer.setTypeface(font);
        //        editPasswordContainer.setTypeface(font);
        //
        //        //Set up listener for button
        //        logInButton.setOnClickListener(
        //                view ->  {
        //                    ((WelcomeActivity) getActivity()).hideSoftKeyboard();
        //                    getMainPresenter().login(editUsername.getText().toString().trim(), editPassword.getText().toString());
        //                }
        //        );
        //
        //        //Observe edit texts' value
        //        Observable<Boolean> usernameObservable = RxTextView.textChanges(editUsername).map(StringUtils::isNotEmpty);
        //        Observable<Boolean> passwordObservable = RxTextView.textChanges(editPassword).map(StringUtils::isNotEmpty);
        //        editTextDisposable =  Observable.combineLatest(usernameObservable, passwordObservable, (validUsername, validPassword) -> validUsername && validPassword)
        //                                        .subscribe(logInButton::setEnabled);
    }

    override fun refreshViews() {
        //        editUsername.setText("");
        //        editPassword.setText("");
    }

    override fun getMainPresenter(): WelcomeActivityPresenter {
        return (activity as WelcomeActivity).mainPresenter
    }

    override fun onStart() {
        super.onStart()
        //((WelcomeActivity) getActivity()).showSoftKeyboard(editUsername);
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden && isVisible) {
            (activity as WelcomeActivity).showToolbar(true)
            //((WelcomeActivity) getActivity()).showSoftKeyboard(editUsername);
        } else
            refreshViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        editTextDisposable!!.dispose()
    }
}

