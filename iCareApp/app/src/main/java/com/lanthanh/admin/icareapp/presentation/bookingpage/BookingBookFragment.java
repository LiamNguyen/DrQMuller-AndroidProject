package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.adapter.CustomSpinnerAdapter;
import com.lanthanh.admin.icareapp.presentation.adapter.ExpandableListViewAdapter;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingBookFragment extends BaseFragment<BookingActivityPresenter> implements AdapterView.OnItemSelectedListener{
    @BindView(R.id.booking_finish_button) AppCompatButton finishButton;
    @BindView(R.id.spinner_machine) Spinner machineSpinner;
    @BindView(R.id.expListView) ExpandableListView expandableListView;

    private ExpandableListViewAdapter listAdapter;
    private CustomSpinnerAdapter machineAdapter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_book, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        finishButton.setTypeface(font);

        finishButton.setOnClickListener(view -> getMainPresenter().createAppointment());
        finishButton.setEnabled(false);

        /*========================= MACHINE SPINNER =========================*/
        machineAdapter = new CustomSpinnerAdapter<DTOMachine>(getActivity(), R.layout.bookingselect_spinner_item, new ArrayList<>(), getString(R.string.booking_machine_hint));
        machineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineSpinner.setAdapter(machineAdapter);
        machineSpinner.setSelection(0, false);
        machineSpinner.setOnItemSelectedListener(this);

        /*========================= EXPANDABLE LIST =========================*/
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnChildClickListener(
                (ExpandableListView expandableListView, View view,  int groupPosition, int childPosition, long childId) -> {
                getMainPresenter().onTimeSelected(listAdapter.getGroup(groupPosition), listAdapter.getChild(groupPosition, childPosition));
                expandableListView.collapseGroup(groupPosition);
                return true;
            }
        );
        expandableListView.setOnGroupClickListener(
            (ExpandableListView expandableListView, View view, int groupPosition, long groupId) ->
                getMainPresenter().onDaySelected(() -> {
                    //If selected (currently the selected item of group is closing)
                    if (!expandableListView.isGroupExpanded(groupPosition)) {
                        expandGroup(groupPosition, false);
                    }
                })
        );
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
        listAdapter = new ExpandableListViewAdapter(getActivity(), new ArrayList<>(), new ArrayList<>());
        expandableListView.setAdapter(listAdapter);

        refreshViews();
    }

    @Override
    public void refreshViews() {
        getMainPresenter().getMachines(machineAdapter::update);
        getMainPresenter().getWeekDays(listAdapter::updateGroupList);
        getMainPresenter().getTime(listAdapter::updateChildList);
        getMainPresenter().resetMachine(() -> machineSpinner.setSelection(0));
    }

    @Override
    public BookingActivityPresenter getMainPresenter() {
        return ((BookingActivity) getActivity()).getMainPresenter();
    }

    public void enableFinishButton(boolean shouldEnable) {
        finishButton.setEnabled(shouldEnable);
    }

    public void enableListView(boolean shouldEnable) {
        expandableListView.setEnabled(shouldEnable);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()) {
            case R.id.spinner_machine:
                if (position != 0) {
                    getMainPresenter().onMachineSelected(
                        () -> {
                        collapseAllGroups();
                        expandGroup(0, true);
                        },
                        (DTOMachine) machineSpinner.getSelectedItem()
                    );
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void expandGroup(int groupPosition, boolean isAuto) {
        getMainPresenter().getAvailableTime(
            listAdapter::updateChildList,
            listAdapter.getGroup(groupPosition).getDayId());
        if (isAuto) {
            expandableListView.expandGroup(groupPosition);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            refreshViews();
        }
        else {
            collapseAllGroups();
        }
    }

    public void collapseAllGroups(){
        int count =  listAdapter.getGroupCount();
        for (int i = 0; i <count ; i++) {
            expandableListView.collapseGroup(i);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
