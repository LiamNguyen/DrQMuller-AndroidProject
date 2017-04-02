package com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.presentation.bookingpage.BookingActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.adapter.AppointmentCVAdapter;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class AppointmentFragment extends BaseFragment<MainActivityPresenter>{
    @BindView(R.id.recycler_view) RecyclerView appointmentView;
    @BindView(R.id.fab) FloatingActionButton bookButton;
    private Unbinder unbinder;
    private AppointmentCVAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        //Set up adapter and layout manager for recycler view
        adapter = new AppointmentCVAdapter(getActivity(), new ArrayList<>());
        appointmentView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        appointmentView.setLayoutManager(llm);

        //Set up for floating action button
        bookButton.setOnClickListener(view -> getMainPresenter().navigateActivity(BookingActivity.class));

        refreshViews();
    }

    @Override
    public void refreshViews() {
        getMainPresenter().getAppointmentList(this.adapter::updateList);
    }

    @Override
    public MainActivityPresenter getMainPresenter() {
        return ((MainActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            refreshViews();
        }
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


