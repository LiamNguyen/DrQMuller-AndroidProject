package com.lanthanh.admin.icareapp.presentation.signupinfopage;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.model.InputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;
import com.lanthanh.admin.icareapp.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class ContactFragment extends BaseFragment<UserInfoActivityPresenter>{
    @BindView(R.id.ui_email_input) TextInputEditText editEmail;
    @BindView(R.id.ui_phone_input) TextInputEditText editPhone;
    @BindView(R.id.ui_email_container) TextInputLayout editEmailContainer;
    @BindView(R.id.ui_phone_container) TextInputLayout editPhoneContainer;
    @BindView(R.id.ui_next_button_p3) AppCompatButton nextButton;
    @BindView(R.id.ui_contacts_noti) TextView contactMessage;

    private Disposable editTextDisposable;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_contacts, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        //Apply custom font for UI elements
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        contactMessage.setTypeface(font);
        nextButton.setTypeface(font);
        editEmail.setTypeface(font);
        editPhone.setTypeface(font);
        editEmailContainer.setTypeface(font);
        editPhoneContainer.setTypeface(font);

        //Set up listener for button
        nextButton.setOnClickListener(
            view -> {
                ((UserInfoActivity) getActivity()).hideSoftKeyboard();
                getMainPresenter().updateImportantInfo(editEmail.getText().toString().trim(), editPhone.getText().toString().trim());
            });

        //Observe edit texts' value
        Observable<Boolean> emailObservable = RxTextView.textChanges(editEmail)
            .map(email -> {
                if (StringUtils.isNotEmpty(email)) {
                    if (StringUtils.validatePattern(email, InputRequirement.EMAIL)) {
                        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        return true;
                    } else {
                        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        return false;
                    }
                } else {
                    editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, 0, 0);
                    return false;
                }
            });
        Observable<Boolean> phoneObservable = RxTextView.textChanges(editPhone)
            .map(phone -> {
                if (StringUtils.isNotEmpty(phone)) {
                    if (StringUtils.validatePattern(phone, InputRequirement.PHONE)) {
                        editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        return true;
                    } else {
                        editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        return false;
                    }
                } else {
                    editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, 0, 0);
                    return false;
                }
            });
        editTextDisposable = Observable.combineLatest(emailObservable, phoneObservable, (validEmail, validPhone) -> validEmail && validPhone)
                                        .subscribe(valid -> nextButton.setEnabled(valid));
    }

    @Override
    public void onStart() {
        super.onStart();
        ((UserInfoActivity) getActivity()).showSoftKeyboard(editEmail);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            ((UserInfoActivity) getActivity()).showSoftKeyboard(editEmail);
        }
        else
            refreshViews();
    }

    @Override
    public void refreshViews() {
        editEmail.setText("");
        editPhone.setText("");
    }

    @Override
    public UserInfoActivityPresenter getMainPresenter() {
        return ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        editTextDisposable.dispose();
    }
}
