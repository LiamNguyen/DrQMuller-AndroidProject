package com.lanthanh.admin.icareapp.presentation.view.fragment.booking;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.TimeManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.WeekDayManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.converter.TimeComparator;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingBookPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.BookingBookPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.activity.BookingActivity;
import com.lanthanh.admin.icareapp.presentation.view.adapter.ExpandableListViewAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingBookFragment extends Fragment implements BookingBookPresenter.View,
        ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener, View.OnClickListener{
    private BookingBookPresenter bookingBookPresenter;
    private BookingActivityPresenter bookingActivityPresenter;
    private ExpandableListView list;
    private ExpandableListViewAdapter adapter;
    private TimeComparator timeComparator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_book, container, false);

        init();

        //Get finish button
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.booking_finish_button);
        button.setOnClickListener(this);
        button.setTypeface(font);

        /*========================= EXPANDABLE LIST =========================*/
        list = (ExpandableListView) view.findViewById(R.id.expListView);
        list.setOnChildClickListener(this);
        list.setOnGroupClickListener(this);
        list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;
            //This method will close opening group to expand another group (only one group expand at a time)
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    list.collapseGroup(previousItem );
                previousItem = groupPosition;
                list.setSelectedGroup(groupPosition);
            }
        });
        //Initialize list adapter
        adapter = new ExpandableListViewAdapter(getActivity(), new ArrayList<String>(), new ArrayList<String>());
        list.setAdapter(adapter);

        bookingBookPresenter.getAllWeekDays();
        bookingBookPresenter.getAllTime();
        bookingBookPresenter.getAllEcoTime();

        return view;
    }

    public void init(){
        //Init presenter
        bookingActivityPresenter = ((BookingActivity) getActivity()).getMainPresenter();
        bookingBookPresenter = new BookingBookPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, bookingActivityPresenter.getDTOAppointment(),
                new TimeManagerImpl(iCareApiImpl.getAPI()), new AppointmentManagerImpl(iCareApiImpl.getAPI()), new WeekDayManagerImpl(iCareApiImpl.getAPI()));
        //Create time comparator
        timeComparator = new TimeComparator();
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
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long groupId) {
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
    public void onClick(View view) {
        if (bookingBookPresenter.getNumberOfCartItems() <= 0){
            showError(getString(R.string.min_item));
        }else {
            bookingActivityPresenter.validateAppointment();
            bookingActivityPresenter.insertAppointment();
        }
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
}
