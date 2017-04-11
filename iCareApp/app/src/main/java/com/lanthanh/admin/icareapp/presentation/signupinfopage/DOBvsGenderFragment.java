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

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.view.ViewAttachAttachedEvent;
import com.jakewharton.rxbinding2.widget.RxRadioGroup;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;
import com.lanthanh.admin.icareapp.utils.StringUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public class DOBvsGenderFragment extends BaseFragment<UserInfoActivityPresenter>{
    @BindView(R.id.ui_dob_txt) TextView dobTitle;
    @BindView(R.id.ui_gender_txt) TextView genderTitle;
    @BindView(R.id.datePicker) DatePicker datePicker;
    @BindView(R.id.ui_radio_group) RadioGroup radioGroup;
    @BindView(R.id.ui_male) RadioButton maleButton;
    @BindView(R.id.ui_female) RadioButton femaleButton;
    @BindView(R.id.ui_next_button_p2) AppCompatButton nextButton;

    private Disposable dobGenderDisposable;
    private Unbinder unbinder;
    private String dob, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_dobvsgender, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();

        return view;
    }

    @Override
    public void initViews() {
        //Apply custom font for UI elements
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);
        dobTitle.setTypeface(font);
        genderTitle.setTypeface(font);
        maleButton.setTypeface(font);
        femaleButton.setTypeface(font);
        nextButton.setTypeface(font);

        //Set up listener for button
        nextButton.setOnClickListener(view -> getMainPresenter().updateNecessaryInfo(dob, gender));
        nextButton.setEnabled(false);

        //Observe date picker and radio group button's value
        Observable<Boolean> genderObservable = RxRadioGroup.checkedChanges(radioGroup)
            .map(genderId -> {
               switch (genderId) {
                   case R.id.ui_male:
                       this.gender = "Male";
                       return true;
                   case R.id.ui_female:
                       this.gender = "Female";
                       return true;
                   default:
                       return false;
               }
            });
        Observable<Boolean> datePickerObservable =
                                    Observable.<String>create(emitter -> {
                                        Calendar calendar = Calendar.getInstance();
                                        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                                                (view, year, monthOfYear, dayOfMonth) -> {
                                                    dob = Integer.toString(year) + "-" +
                                                            ((Integer.toString(monthOfYear + 1).length() == 1)?("0" + Integer.toString(monthOfYear + 1)) : Integer.toString(monthOfYear + 1)) + "-" +
                                                            ((Integer.toString(dayOfMonth).length() == 1)?("0" + Integer.toString(dayOfMonth)) : Integer.toString(dayOfMonth));
                                                    emitter.onNext(dob);
                                                });
                                    }).map(StringUtils::isNotNull);
        dobGenderDisposable = Observable.combineLatest(datePickerObservable, genderObservable, (validDob, validGender) -> validDob && validGender)
                                        .distinctUntilChanged()
                                        .subscribe(nextButton::setEnabled);
    }

    @Override
    public void refreshViews() {
        radioGroup.clearCheck();
        dob = null; gender = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            updateDatePicker();
        else
            refreshViews();
    }

    @Override
    public UserInfoActivityPresenter getMainPresenter() {
        return ((UserInfoActivity) getActivity()).getMainPresenter();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        dobGenderDisposable.dispose();
    }

    private void updateDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
}
