package com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.presentation.model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.UserInfoActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class NameAndAddressFragment extends Fragment implements View.OnClickListener, TextWatcher{
    private TextInputEditText name;
    private TextInputEditText address;
    private TextInputLayout name_container;
    private TextInputLayout address_container;
    private UserInfoActivityPresenter userInfoActivityPresenter;
    private boolean validName, validAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_namevsaddress, container, false);

        init();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font

        name = (TextInputEditText) view.findViewById(R.id.ui_name_input);
        name.addTextChangedListener(this);
        name.setTypeface(font);
        address = (TextInputEditText) view.findViewById(R.id.ui_address_input);
        address.addTextChangedListener(this);
        address.setTypeface(font);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p1);
        button.setOnClickListener(this);
        button.setTypeface(font);
        name_container = (TextInputLayout) view.findViewById(R.id.ui_name_container);
        name_container.setTypeface(font);
        address_container = (TextInputLayout) view.findViewById(R.id.ui_address_container);
        address_container.setTypeface(font);


        return view;
    }

    public void init(){
        userInfoActivityPresenter = ((UserInfoActivity) getActivity()).getMainPresenter();
        validName = false;
        validAddress = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_next_button_p1:
                if (validName && validAddress){
                    userInfoActivityPresenter.setName(name.getText().toString().trim());
                    userInfoActivityPresenter.setAddress(address.getText().toString().trim());
                    userInfoActivityPresenter.navigateFragment(UserInfoActivity.DOB_GENDER);
                }
                else{
                    if (!validName){
                        if (name.getText().toString().equals("")){
                            name_container.setError(getString(R.string.name_null));
                        }else {
                            name_container.setError(getString(R.string.name_requirement));
                        }
                        name_container.setErrorEnabled(true);
                    }
                    if (!validAddress){
                        if (address.getText().toString().equals("")){
                            address_container.setError(getString(R.string.address_null));
                        }else{
                            address_container.setError(getString(R.string.address_requirement));
                        }
                        address_container.setErrorEnabled(true);
                    }
                }
                break;
            default:
                break;
        }
    }

    //nothing
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /*
     *Check user input by input to see whether input is valid
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (name.getText().hashCode() == s.hashCode()){//Edit name
            String get_name = name.getText().toString();
            get_name.trim();
            if (!get_name.equals("")){
                if (get_name.matches(ModelInputRequirement.NAME)){
                    name.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    name_container.setErrorEnabled(false);
                    validName = true;
                }else{
                    name.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validName = false;
                }
            }else {
                name.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_pin_white_36dp, 0, 0, 0);
                validName = false;
            }
        }else if (address.getText().hashCode() == s.hashCode()){//Edit Address
            String get_address = address.getText().toString();
            get_address.toString();
            if (!get_address.equals("")){
                if (get_address.matches(ModelInputRequirement.ADDRESS)){
                    address.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    address_container.setErrorEnabled(false);
                    validAddress = true;
                }else{
                    address.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validAddress = false;
                }
            }else {
                address.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_pin_drop_white_36dp, 0, 0, 0);
                validAddress = false;
            }
        }
    }

    //nothing
    @Override
    public void afterTextChanged(Editable s) {

    }
}
