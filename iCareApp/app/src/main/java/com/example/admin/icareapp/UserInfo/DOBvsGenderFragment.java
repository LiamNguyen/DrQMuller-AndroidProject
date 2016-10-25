package com.example.admin.icareapp.UserInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.icareapp.R;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class DOBvsGenderFragment extends Fragment implements DatePicker.OnDateChangedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener{
    private UserInfoListener uiListener;
    private FragmentTransaction frag_transaction;
    RadioGroup rGroup;
    RadioButton male_button;
    RadioButton female_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_dobvsgender, container, false);

        DatePicker dPicker = (DatePicker) view.findViewById(R.id.datePicker);
        rGroup = (RadioGroup) view.findViewById(R.id.ui_radio_group);
        RadioButton male_button = (RadioButton) view.findViewById(R.id.ui_male);
        RadioButton female_button = (RadioButton) view.findViewById(R.id.ui_female);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p2) ;

        dPicker.init(1998, 0, 1, this);
        rGroup.setOnCheckedChangeListener(this);
        button.setOnClickListener(this);
        //male_button.setOnClickListener(this);
        //female_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        System.out.println("date changed");
        uiListener.addUserInfo("day", Integer.toString(dayOfMonth));
        uiListener.addUserInfo("month", Integer.toString(monthOfYear));
        uiListener.addUserInfo("year", Integer.toString(year));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            uiListener = (UserInfoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UserInfoListener");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.ui_male:
                uiListener.addUserInfo("gender", "Male");
                break;
            case R.id.ui_female:
                uiListener.addUserInfo("gender", "Female");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        rGroup.getCheckedRadioButtonId();
        switch (v.getId()){
            case R.id.ui_next_button_p2:
                System.out.println("pressed");
                if (uiListener.checkUserInfo("day") && uiListener.checkUserInfo("month") && uiListener.checkUserInfo("year") && uiListener.checkUserInfo("gender")){
                    System.out.println("valid");
                    frag_transaction = getFragmentManager().beginTransaction();
                    frag_transaction.replace(R.id.ui_fragment_container, new ContactFragment());
                    frag_transaction.addToBackStack(null);
                    frag_transaction.commit();
                }
                break;
            case R.id.back_button:

                break;
            /*case R.id.ui_radio_group:
                System.out.println("rg pressed");
                if (male_button.isChecked())
                    uiListener.addUserInfo("gender", "Male");
                else if (female_button.isChecked())
                    uiListener.addUserInfo("gender", "Female");
                break;*/
            default:
                break;
        }
    }
}
