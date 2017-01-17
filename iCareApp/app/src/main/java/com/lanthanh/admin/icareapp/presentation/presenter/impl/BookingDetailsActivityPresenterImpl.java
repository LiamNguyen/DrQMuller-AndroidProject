package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.lanthanh.admin.icareapp.data.converter.ConverterJson;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingDetailsActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12-Jan-17.
 */

public class BookingDetailsActivityPresenterImpl extends AbstractPresenter implements BookingDetailsActivityPresenter {
    private BookingDetailsActivityPresenter.View mView;
    private SharedPreferences sharedPreferences;
    private List<DTOAppointment> dtoAppointmentsList;
    private AppointmentManager appointmentManager;

    public BookingDetailsActivityPresenterImpl(SharedPreferences sharedPreferences, Executor executor, MainThread mainThread, View view, AppointmentManager appointmentManager){
        super(executor, mainThread);
        mView = view;
        this.sharedPreferences = sharedPreferences;
        this.appointmentManager = appointmentManager;
        init();
    }

    public void init(){
        dtoAppointmentsList = new ArrayList<>();
    }

    @Override
    public void resume() {

    }

    @Override
    public void updateList() {
        //Get local appointment list
        if ( appointmentManager.getLocalAppointmentsFromPref(sharedPreferences) != null)
            dtoAppointmentsList = appointmentManager.getLocalAppointmentsFromPref(sharedPreferences);

        mView.updateList(dtoAppointmentsList);
    }

    @Override
    public int getNumberOfAppointments(){
        return dtoAppointmentsList.size();
    }
}
