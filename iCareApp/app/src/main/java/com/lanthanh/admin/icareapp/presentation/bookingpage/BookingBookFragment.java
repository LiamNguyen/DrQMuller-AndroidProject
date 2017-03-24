package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.MachineManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.TimeManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.WeekDayManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.presentation.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.converter.TimeComparator;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingBookPresenter;
import com.lanthanh.admin.icareapp.presentation.adapter.CustomSpinnerAdapter;
import com.lanthanh.admin.icareapp.presentation.adapter.ExpandableListViewAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingBookFragment extends BaseFragment<BookingActivityPresenterImpl> implements BookingBookPresenter.View,
        ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener, AdapterView.OnItemSelectedListener{
    @BindView(R.id.booking_finish_button) AppCompatButton finishButton;
    @BindView(R.id.spinner_machine) Spinner machineSpinner;
    @BindView(R.id.expListView) ExpandableListView expandableListView;

    private ExpandableListViewAdapter adapter;
    private TimeComparator timeComparator;
    private CustomSpinnerAdapter machineAdapter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_book, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();

        timeComparator = new TimeComparator();
        getMainPresenter().getMachinesByLocationId();
        getMainPresenter().getWeekDays();
        getMainPresenter().getAllTime();

        return view;
    }

    @Override
    public void initViews() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        finishButton.setTypeface(font);

        finishButton.setOnClickListener(
            view ->{
                if (bookingBookPresenter.getNumberOfCartItems() <= 0){
                    showError(getString(R.string.min_item));
                }else {
                    bookingActivityPresenter.validateAppointment();
                    bookingActivityPresenter.insertAppointment();
                }
        });

        /*========================= MACHINE SPINNER =========================*/
        machineAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, machinesName);
        machineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineSpinner.setAdapter(machineAdapter);
        machineSpinner.setSelection(0, false);
        machineSpinner.setOnItemSelectedListener(this);

        /*========================= EXPANDABLE LIST =========================*/
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;
            //This method will close opening group to expand another group (only one group expand at a time)
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expandableListView.collapseGroup(previousItem );
                previousItem = groupPosition;
                expandableListView.setSelectedGroup(groupPosition);
            }
        });
        //Initialize list adapter
        adapter = new ExpandableListViewAdapter(getActivity(), getProvider().getWeekDays(), getProvider().getAllTime());
        expandableListView.setAdapter(adapter);

    }

    @Override
    public void resetViews() {

    }

    @Override
    public BookingActivityPresenterImpl getMainPresenter() {
        return ((BookingActivity) getActivity()).getMainPresenter();
    }

    @Override
    public ApplicationProvider getProvider() {
        return ((BookingActivity) getActivity()).getProvider();
    }

    @Override
    public String getResourceString(int id) {
        return getString(id);
    }

    @Override
    public void setAvailableDay(List<String> list) {
        adapter.updateGroupList(list);
    }

    @Override
    public void setAvailableTime(List<String> list) {
        Collections.sort(list, timeComparator);
        adapter.updateChildList(list);
    }

    @Override
    public void showProgress() {
        ((BookingActivity) getActivity()).showProgress();
    }

    @Override
    public void hideProgress() {
        ((BookingActivity) getActivity()).hideProgress();
    }

    @Override
    public void showError(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 110);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()) {
            case R.id.spinner_machine:
                bookingBookPresenter.onMachineSelected(machineSp.getSelectedItem().toString());
                collapseAllGroups();
                autoExpandGroup(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void updateMachineList(List<String> list) {
        machinesName.clear();
        machinesName.add(getString(R.string.booking_machine_hint));
        machinesName.addAll(list);
    }

    @Override
    public void autoExpandGroup(int groupPosition) {
        bookingBookPresenter.getSelectedTime(adapter.getGroup(groupPosition));
        expandableListView.expandGroup(groupPosition);
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long groupId) {
        //Check whether machine has been selected. If not selected
        if (!bookingBookPresenter.isMachineFilled()){
            showError(getResourceString(R.string.machine_alert));
            return true;
        }
        //If selected
        if (!list.isGroupExpanded(groupPosition)) {
            bookingBookPresenter.getSelectedTime(adapter.getGroup(groupPosition));
        }

        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view,  int groupPosition, int childPosition, long childId) {
        if (bookingBookPresenter.getNumberOfCartItems() == 3) {
            showError(getString(R.string.max_item));
            return false;
        }

        bookingBookPresenter.onTimeSelected(adapter.getGroup(groupPosition), adapter.getChild(groupPosition, childPosition));

        list.collapseGroup(groupPosition);

        return true;
    }

    @Override
    public void updateCart() {
        bookingActivityPresenter.addCartItem();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
           bookingBookPresenter.resume();
        }
        else
            collapseAllGroups();
    }

    public void collapseAllGroups(){
        int count =  adapter.getGroupCount();
        for (int i = 0; i <count ; i++)
            list.collapseGroup(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
