package com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.presentation.bookingpage.BookingActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class DefaultAppointmentFragment extends BaseFragment<MainActivityPresenter>{
    @BindView(R.id.button) AppCompatButton bookButton;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_defaultappointment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();

        return view;
    }

    @Override
    public void initViews() {
        //Get finish button
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        bookButton.setTypeface(font);
        bookButton.setOnClickListener(view -> getMainPresenter().navigateActivity(BookingActivity.class));
    }

    @Override
    public void refreshViews() {}

    @Override
    public MainActivityPresenter getMainPresenter() {
        return ((MainActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public ApplicationProvider getProvider() {
        return null;
    }
}
