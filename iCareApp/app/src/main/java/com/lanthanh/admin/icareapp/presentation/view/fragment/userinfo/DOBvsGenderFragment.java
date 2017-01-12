package com.lanthanh.admin.icareapp.presentation.view.fragment.userinfo;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.UserInfoActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.UserInfoActivity;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class DOBvsGenderFragment extends Fragment implements DatePicker.OnDateChangedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener{
    private UserInfoActivityPresenter userInfoActivityPresenter;
    private TextView dob_noti;
    private TextView gender_noti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_dobvsgender, container, false);

        init();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font

        //Header
        TextView dob_txt = (TextView) view.findViewById(R.id.ui_dob_txt);
        dob_txt.setTypeface(font);
        TextView gender_txt = (TextView) view.findViewById(R.id.ui_gender_txt);
        gender_txt.setTypeface(font);
        //Date picker
        DatePicker dPicker = (DatePicker) view.findViewById(R.id.datePicker);
        dPicker.init(1998, 0, 1, this);
        //Gender group
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.ui_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.ui_next_button_p2);
        RadioButton male = (RadioButton) view.findViewById(R.id.ui_male);
        male.setTypeface(font);
        RadioButton female = (RadioButton) view.findViewById(R.id.ui_female);
        female.setTypeface(font);
        button.setOnClickListener(this);
        button.setTypeface(font);
        dob_noti = (TextView) view.findViewById(R.id.ui_dob_noti);
        gender_noti = (TextView) view.findViewById(R.id.ui_gender_noti);
        dob_noti.setTypeface(font);
        gender_noti.setTypeface(font);

        return view;
    }

    public void init(){
        userInfoActivityPresenter = ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dob_noti.setVisibility(View.INVISIBLE);
        userInfoActivityPresenter.setDob(Integer.toString(year) + "-" + Integer.toString(monthOfYear+1) + "-" + Integer.toString(dayOfMonth));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        gender_noti.setVisibility(View.INVISIBLE);
        switch (checkedId){
            case R.id.ui_male:
                userInfoActivityPresenter.setGender("Male");
                break;
            case R.id.ui_female:
                userInfoActivityPresenter.setGender("Female");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_next_button_p2:
                if (userInfoActivityPresenter.isDobSet() && userInfoActivityPresenter.isGenderSet()){
                    userInfoActivityPresenter.navigateFragment(UserInfoActivity.CONTACT);
                }else {
                    if (!userInfoActivityPresenter.isDobSet()){
                        dob_noti.setVisibility(View.VISIBLE);
                    }
                    if (!userInfoActivityPresenter.isGenderSet()){
                        gender_noti.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                break;
        }
    }
}
