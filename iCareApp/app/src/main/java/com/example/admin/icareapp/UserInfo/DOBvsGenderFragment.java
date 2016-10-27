package com.example.admin.icareapp.UserInfo;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.example.admin.icareapp.Controller.Controller;
import com.example.admin.icareapp.R;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class DOBvsGenderFragment extends Fragment implements DatePicker.OnDateChangedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener{
    private Controller aController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_dobvsgender, container, false);

        DatePicker dPicker = (DatePicker) view.findViewById(R.id.datePicker);
        dPicker.init(1998, 0, 1, this);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.ui_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p2) ;
        button.setOnClickListener(this);

        aController = Controller.getInstance();

        return view;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        aController.getUserInfo().addInfo("dob", Integer.toString(year) + "-" + Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.ui_male:
                aController.getUserInfo().addInfo("gender", "Male");
                break;
            case R.id.ui_female:
                aController.getUserInfo().addInfo("gender", "Female");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_next_button_p2:
                if (aController.getUserInfo().isInfoExist("dob") && aController.getUserInfo().isInfoExist("gender")){
                    ((UserInfoActivity) getActivity()).navigateToContact();
                }
                break;
            case R.id.back_button:
                ((UserInfoActivity) getActivity()).navigateBack();
                break;
            default:
                break;
        }
    }
}
