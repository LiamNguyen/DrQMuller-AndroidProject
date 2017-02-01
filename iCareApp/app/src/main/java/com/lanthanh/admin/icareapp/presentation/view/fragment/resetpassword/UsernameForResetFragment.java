package com.lanthanh.admin.icareapp.presentation.view.fragment.resetpassword;

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
import android.widget.TextView;

import com.lanthanh.admin.icareapp.presentation.presenter.ResetPasswordActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.ResetPasswordActivity;
import com.lanthanh.admin.icareapp.presentation.model.ModelInputRequirement;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

/**
 * Created by ADMIN on 22-Nov-16.
 */

public class UsernameForResetFragment extends Fragment implements TextWatcher, View.OnClickListener {
    private ResetPasswordActivityPresenter resetPasswordActivityPresenter;
    private TextInputEditText username;
    private TextInputLayout username_container;
    private boolean validUN;
    private TextView noti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resetpw_username, container, false);

        init();

        //Custom font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);

        AppCompatButton send = (AppCompatButton) view.findViewById(R.id.resetpw_send_button);
        send.setOnClickListener(this);
        send.setTypeface(font);

        username = (TextInputEditText) view.findViewById(R.id.resetpw_username_input);
        username.addTextChangedListener(this);
        username.setTypeface(font);
        username_container = (TextInputLayout) view.findViewById(R.id.resetpw_username_container);
        username_container.setTypeface(font);
        noti = (TextView) view.findViewById(R.id.resetpw_email_noti);
        noti.setTypeface(font);

        return view;
    }

    public void init(){
        resetPasswordActivityPresenter = ((ResetPasswordActivity) getActivity()).getMainPresenter();
        validUN = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resetpw_send_button:
                if (validUN) {
                    resetPasswordActivityPresenter.sendEmailToResetPassword(username.getText().toString().trim());
                }else {
                    if (!validUN){
                        if (username.getText().toString().equals(""))
                            username_container.setError(getString(R.string.username_null));
                        else
                            username_container.setError(getString(R.string.username_requirement));
                        username_container.setErrorEnabled(true);
                    }
                }
                break;
            default:
                break;
        }
        ((ResetPasswordActivity) getActivity()).hideSoftKeyboard();
    }

    /*
     *Check user input by input to see whether input is valid
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (username.getText().hashCode() == s.hashCode()) { //Check username
            String get_username = s.toString();
            get_username.trim();
            if (!get_username.equals("")) {
                if (get_username.matches(ModelInputRequirement.USERNAME)) {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_valid_input, 0);
                    username_container.setErrorEnabled(false);
                    validUN = true;
                } else {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, R.drawable.ic_invalid_input, 0);
                    validUN = false;
                }
            } else {
                username.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_white_36dp, 0, 0, 0);
                validUN = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void showEmailResult(int string){
        noti.setText(string);
        noti.setVisibility(View.VISIBLE);
    }
}
