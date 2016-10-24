package com.example.admin.icareapp.UserInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.admin.icareapp.R;
import com.example.admin.icareapp.SignUpFragment;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class NameAndLocationFragment extends Fragment implements View.OnClickListener, TextWatcher{
    private final String name_requirement = "[a-zA-Z]+";
    private final String address_requirement = "\\w+";
    private EditText name;
    private EditText address;
    private UserInfoListener uiListener;
    private boolean validName, validAddress;
    private FragmentTransaction frag_transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_name, container, false);

        name = (EditText) view.findViewById(R.id.ui_name_input);
        address = (EditText) view.findViewById(R.id.ui_address_input);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p1);

        name.addTextChangedListener(this);
        address.addTextChangedListener(this);
        button.setOnClickListener(this);

        validName = false;
        validAddress = false;

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a = (Activity) context;
        try {
            uiListener = (UserInfoListener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UserInfoListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_next_button_p1:
                if (validName && validAddress){
                    uiListener.addUserInfo("name", name.getText().toString());
                    uiListener.addUserInfo("address", address.getText().toString());
                    frag_transaction = getFragmentManager().beginTransaction();
                    frag_transaction.replace(R.id.ui_fragment_container, new DOBvsGenderFragment());
                    frag_transaction.addToBackStack(null);
                    frag_transaction.commit();
                }
                break;
            case R.id.back_button:

                break;
            default:
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (name.getText().hashCode() == s.hashCode()){
            String get_name = name.getText().toString();
            get_name.trim();
            if (!get_name.equals("")){
                if (get_name.matches(name_requirement)){
                    name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_input, 0, R.drawable.ic_valid_input, 0);
                    //login_id_container.setErrorEnabled(false);
                    validName = true;
                }else{
                    name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_input, 0, R.drawable.ic_invalid_input, 0);
                    validName = false;
                }
            }else {
                name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_input, 0, 0, 0);
                validName = false;
            }
        }else if (address.getText().hashCode() == s.hashCode()){
            String get_address = address.getText().toString();
            get_address.toString();
            if (!get_address.equals("")){
                if (get_address.matches(address_requirement)){
                    address.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location, 0, R.drawable.ic_valid_input, 0);
                    //login_id_container.setErrorEnabled(false);
                    validAddress = true;
                }else{
                    address.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location, 0, R.drawable.ic_invalid_input, 0);
                    validAddress = false;
                }
            }else {
                address.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location, 0, 0, 0);
                validAddress = false;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
