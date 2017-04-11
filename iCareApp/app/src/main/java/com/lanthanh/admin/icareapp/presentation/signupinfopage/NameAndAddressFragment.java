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

public class NameAndAddressFragment extends BaseFragment<UserInfoActivityPresenter>{
    @BindView(R.id.ui_name_input) TextInputEditText editName;
    @BindView(R.id.ui_address_input) TextInputEditText editAddress;
    @BindView(R.id.ui_name_container) TextInputLayout editNameContainer;
    @BindView(R.id.ui_address_container) TextInputLayout editAddressContainer;
    @BindView(R.id.ui_next_button_p1) AppCompatButton nextButton;

    private Disposable editTextDisposable;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_namevsaddress, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        //Apply custom font for UI elements
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        editName.setTypeface(font);
        editAddress.setTypeface(font);
        editNameContainer.setTypeface(font);
        editAddressContainer.setTypeface(font);
        nextButton.setTypeface(font);

        //Set up listener for button
        nextButton.setOnClickListener(
            view -> {
                ((UserInfoActivity) getActivity()).hideSoftKeyboard();
                getMainPresenter().updateBasicInfo(editName.getText().toString().trim(), editAddress.getText().toString().trim());
        });

        //Observe edit texts' value
        Observable<Boolean> nameObservable = RxTextView.textChanges(editName)
            .map(name -> {
                if (StringUtils.isNotEmpty(name)){
                    if (StringUtils.validatePattern(name, InputRequirement.NAME)){
                        editName.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                        return true;
                    }else{
                        editName.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                        return false;
                    }
                }else {
                    editName.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, 0, 0);
                    return false;
                }
            });
        Observable<Boolean> addressObservable = RxTextView.textChanges(editAddress)
                .map(address -> {
                    if (StringUtils.isNotEmpty(address)){
                        if (StringUtils.validatePattern(address, InputRequirement.ADDRESS)){
                            editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, R.drawable.ic_check_circle_white_24dp, 0);
                            return true;
                        }else{
                            editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, R.drawable.ic_error_white_24dp, 0);
                            return false;
                        }
                    }else {
                        editAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, 0, 0);
                        return false;
                    }
                });
        editTextDisposable = Observable.combineLatest(nameObservable, addressObservable, (validName, validAddress) -> validName && validAddress)
                                        .subscribe(nextButton::setEnabled);
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
        editTextDisposable.dispose();
    }
}
