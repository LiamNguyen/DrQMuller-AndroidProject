package com.lanthanh.admin.icareapp.presentation.signupinfopage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.model.InputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 08-Nov-16.
 */

public class ChangeEmailFragment extends BaseFragment<UserInfoActivityPresenter>{
    @BindView(R.id.ui_email_input) TextInputEditText editEmail;
    @BindView(R.id.ui_email_container) TextInputLayout editEmailContainer;
    @BindView(R.id.ui_change_button) AppCompatButton changeButton;

    private Unbinder unbinder;
    private boolean validEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_changeemail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        validEmail = false;

        return view;
    }

    @Override
    public void initViews() {
        ((UserInfoActivity) getActivity()).showToolbar(true);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        editEmail.setTypeface(font);
        changeButton.setTypeface(font);
        editEmailContainer.setTypeface(font);

        changeButton.setOnClickListener(view -> ((UserInfoActivity) getActivity()).hideSoftKeyboard());//TODO check update email
        changeButton.setEnabled(false);

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = editEmail.getText().toString().trim();
                if (!email.equals("")){
                    if (email.matches(InputRequirement.EMAIL)){
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
                toggleChangeButton();
            }
        });
    }

    @Override
    public void refreshViews() {
        editEmail.setText("");
    }

    @Override
    public UserInfoActivityPresenter getMainPresenter() {
        return ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            ((UserInfoActivity) getActivity()).showToolbar(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void toggleChangeButton() {
        if (validEmail)
            changeButton.setEnabled(true);
        else
            changeButton.setEnabled(false);
    }
}
