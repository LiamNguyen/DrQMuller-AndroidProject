package com.lanthanh.admin.icareapp.presentation.homepage.appointmenttab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.adapter.AppointmentCVAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class AppointmentFragment extends Fragment implements MainActivityPresenter.AppointmentChildView, View.OnClickListener{
    private AppointmentCVAdapter adapter;
    private MainActivityPresenter mainActivityPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        init();

        //Init view
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        //Set up adapter and layout manager for recycler view
        adapter = new AppointmentCVAdapter(getActivity(), new ArrayList<DTOAppointment>());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        //Set up for floating action button
        fab.setOnClickListener(this);

        //Init list
        mainActivityPresenter.updateAppointmentList();

        return view;
    }

    public void init(){
        mainActivityPresenter = ((MainActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            mainActivityPresenter.updateAppointmentList();
        }
    }

    @Override
    public void onClick(View view) {
        mainActivityPresenter.navigateToBookingActivity();
    }

    @Override
    public void updateList(List<DTOAppointment> list) {
        adapter.updateList(list);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
