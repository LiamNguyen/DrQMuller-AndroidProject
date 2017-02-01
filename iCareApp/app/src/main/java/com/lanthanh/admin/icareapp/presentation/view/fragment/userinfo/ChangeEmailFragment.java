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
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

/**
 * Created by ADMIN on 08-Nov-16.
 */

public class ChangeEmailFragment extends Fragment implements View.OnClickListener, TextWatcher{
    private UserInfoActivityPresenter userInfoActivityPresenter;
    private TextInputEditText email;
    private TextInputLayout email_container;
    private boolean validEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_changeemail, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font

        AppCompatButton change = (AppCompatButton) view.findViewById(R.id.ui_change_button);
        change.setOnClickListener(this);
        change.setTypeface(font);
        email = (TextInputEditText) view.findViewById(R.id.ui_email_input);
        email.addTextChangedListener(this);
        email.setTypeface(font);
        email_container = (TextInputLayout) view.findViewById(R.id.ui_email_container);
        email_container.setTypeface(font);

        validEmail = false;

        return view;
    }

    public void init(){
        userInfoActivityPresenter = ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_change_button:
                if (validEmail) {
                    userInfoActivityPresenter.setEmail(email.getText().toString());
                    userInfoActivityPresenter.updateCustomer();
                    userInfoActivityPresenter.onEmailChange(R.string.send_mail_again);
                }else {
                    if (email.getText().toString().equals("")) {
                        email_container.setError(getString(R.string.email_null));
                    } else {
                        email_container.setError(getString(R.string.email_requirement));
                    }
                    email_container.setErrorEnabled(true);
                }
                break;
            default:
                break;
        }
        ((UserInfoActivity) getActivity()).hideSoftKeyboard();
    }

    /*
     *Check user input by input to see whether input is valid
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (email.getText().hashCode() == s.hashCode()){
            String get_email = email.getText().toString();
            get_email.trim();
            if (!get_email.equals("")){
                if (get_email.matches(ModelInputRequirement.EMAIL)){
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    email_container.setErrorEnabled(false);
                    validEmail = true;
                }else{
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validEmail = false;
                }
            }else {
                email.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_local_post_office_white_36dp, 0, 0, 0);
                validEmail = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}
