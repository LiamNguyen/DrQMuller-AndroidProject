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
 * Created by ADMIN on 22-Oct-16.
 */

public class NameAndAddressFragment extends BaseFragment<UserInfoActivityPresenter>{
    @BindView(R.id.ui_name_input) TextInputEditText editName;
    @BindView(R.id.ui_address_input) TextInputEditText editAddress;
    @BindView(R.id.ui_name_container) TextInputLayout editNameContainer;
    @BindView(R.id.ui_address_container) TextInputLayout editAddressContainer;
    @BindView(R.id.ui_next_button_p1) AppCompatButton nextButton;

    private Unbinder unbinder;
    private boolean validName, validAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_namevsaddress, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        validName = false; validAddress = false;

        return view;
    }

    @Override
    public void initViews() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        editName.setTypeface(font);
        editAddress.setTypeface(font);
        editNameContainer.setTypeface(font);
        editAddressContainer.setTypeface(font);
        nextButton.setTypeface(font);

        nextButton.setOnClickListener(
            view -> {
                ((UserInfoActivity) getActivity()).hideSoftKeyboard();
                getMainPresenter().updateBasicInfo(editName.getText().toString().trim(), editAddress.getText().toString().trim());
            });
        nextButton.setEnabled(false);

        editName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = editName.getText().toString().trim();
                if (!name.equals("")){
                    if (name.matches(InputRequirement.NAME)){
                        editName.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editNameContainer.setErrorEnabled(false);
                        validName = true;
                    }else{
                        editName.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        validName = false;
                    }
                }else {
                    editName.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, 0, 0);
                    validName = false;
                }
                toggleNextButton();
            }
        });
        editAddress.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String address = editAddress.getText().toString().trim();
                if (!address.equals("")){
                    if (address.matches(InputRequirement.ADDRESS)){
                        editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        editAddressContainer.setErrorEnabled(false);
                        validAddress = true;
                    }else{
                        editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        validAddress = false;
                    }
                }else {
                    editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, 0, 0);
                    validAddress = false;
                }
                toggleNextButton();
            }
        });
    }

    @Override
    public void refreshViews() {
        editName.setText("");
        editAddress.setText("");
    }

    @Override
    public UserInfoActivityPresenter getMainPresenter() {
        return ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((UserInfoActivity) getActivity()).showSoftKeyboard(editName);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            ((UserInfoActivity) getActivity()).showSoftKeyboard(editName);
        }
        else
            refreshViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public ApplicationProvider getProvider() {
        return null;
    }

    //                if (validName && validAddress){
//
//                }
//                else{
//                    if (!validName){
//                        if (editName.getText().toString().equals("")){
//                            editNameContainer.setError(getString(R.string.name_null));
//                        }else {
//                            editNameContainer.setError(getString(R.string.name_requirement));
//                        }
//                        editNameContainer.setErrorEnabled(true);
//                    }
//                    if (!validAddress){
//                        if (editAddress.getText().toString().equals("")){
//                            editAddressContainer.setError(getString(R.string.address_null));
//                        }else{
//                            editAddressContainer.setError(getString(R.string.address_requirement));
//                        }
//                        editAddressContainer.setErrorEnabled(true);
//                    }
//                }

    private void toggleNextButton() {
        if (validName && validAddress)
            nextButton.setEnabled(true);
        else
            nextButton.setEnabled(false);
    }
}
