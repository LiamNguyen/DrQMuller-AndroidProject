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

import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.model.InputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private Unbinder unbinder;
    private boolean validEmail, validPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_contacts, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        validEmail = false; validPhone = false;

        return view;
    }

    @Override
    public void initViews() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        contactMessage.setTypeface(font);
        nextButton.setTypeface(font);
        editEmail.setTypeface(font);
        editPhone.setTypeface(font);
        editEmailContainer.setTypeface(font);
        editPhoneContainer.setTypeface(font);

        nextButton.setOnClickListener(
            view -> {
                ((UserInfoActivity) getActivity()).hideSoftKeyboard();
                getMainPresenter().updateImportantInfo(editEmail.getText().toString().trim(), editPhone.getText().toString().trim());
            });
        nextButton.setEnabled(false);

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String get_email = editEmail.getText().toString();
                get_email.trim();
                if (!get_email.equals("")){
                    if (get_email.matches(InputRequirement.EMAIL)){
                        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editEmailContainer.setErrorEnabled(false);
                        validEmail = true;
                    }else{
                        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        validEmail = false;
                    }
                }else {
                    editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, 0, 0);
                    validEmail = false;
                }
                toggleNextButton();
            }
        });
        editPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String get_phone = editPhone.getText().toString();
                get_phone.trim();
                if (!get_phone.equals("")){
                    if (get_phone.matches(InputRequirement.PHONE)){
                        editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editPhoneContainer.setErrorEnabled(false);
                        validPhone = true;
                    }else{
                        editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        validPhone = false;
                    }
                }else {
                    editPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_phone_white_36dp, 0, 0, 0);
                    validPhone = false;
                }
                toggleNextButton();
            }
        });
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
    }

    private void toggleNextButton() {
        if (validEmail && validPhone)
            nextButton.setEnabled(true);
        else
            nextButton.setEnabled(false);
    }

    @Override
    public ApplicationProvider getProvider() {
        return null;
    }
}
