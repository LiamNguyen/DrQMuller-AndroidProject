package com.lanthanh.admin.icareapp.presentation.signupinfopage;

import android.graphics.Typeface;
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
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class DOBvsGenderFragment extends BaseFragment<UserInfoActivityPresenterImpl>{
    @BindView(R.id.ui_dob_txt) TextView dobStatus;
    @BindView(R.id.ui_gender_txt) TextView genderStatus;
    @BindView(R.id.datePicker) DatePicker datePicker;
    @BindView(R.id.ui_radio_group) RadioGroup radioGroup;
    @BindView(R.id.ui_male) RadioButton maleButton;
    @BindView(R.id.ui_female) RadioButton femaleButton;
    @BindView(R.id.ui_next_button_p2) AppCompatButton nextButton;

    private String dob, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_dobvsgender, container, false);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        dobStatus.setTypeface(font);
        genderStatus.setTypeface(font);
        maleButton.setTypeface(font);
        femaleButton.setTypeface(font);
        nextButton.setTypeface(font);
        dobStatus.setTypeface(font);
        genderStatus.setTypeface(font);

        //Date picker
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            dobStatus.setVisibility(View.INVISIBLE);
                            dob = Integer.toString(year) + "-" +
                                    ((Integer.toString(monthOfYear + 1).length() == 1)?("0" + Integer.toString(monthOfYear + 1)) : Integer.toString(monthOfYear + 1)) + "-" +
                                    ((Integer.toString(dayOfMonth).length() == 1)?("0" + Integer.toString(dayOfMonth)) : Integer.toString(dayOfMonth));
                            toggleNextButton();
                        });

        //Gender group
        radioGroup.setOnCheckedChangeListener(
            (group, checkedId) -> {
                genderStatus.setVisibility(View.INVISIBLE);
                switch (checkedId){
                    case R.id.ui_male:
                        gender = "Male";
                        break;
                    case R.id.ui_female:
                        gender = "Female";
                        break;
                    default:
                        break;
                }
                toggleNextButton();
        });
        nextButton.setOnClickListener(view -> getMainPresenter().updateNecessaryInfo(dob, gender));
        nextButton.setEnabled(false);
    }

    @Override
    public void resetViews() {
        radioGroup.clearCheck();
        dob = null; gender = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            updateDatePicker();
        else
            resetViews();
    }

    @Override
    public UserInfoActivityPresenterImpl getMainPresenter() {
        return ((UserInfoActivity) getActivity()).getMainPresenter();
    }

    private void updateDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void toggleNextButton() {
        if (dob != null && gender != null)
            nextButton.setEnabled(true);
        else
            nextButton.setEnabled(false);
    }
}
