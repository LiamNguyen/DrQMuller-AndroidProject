package com.lanthanh.admin.icareapp.presentation.view.fragment.bookingtab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.AppointmentManager;
import com.lanthanh.admin.icareapp.data.manager.TimeManager;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.TimeManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.converter.TimeComparator;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingBookPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.BookingBookPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.adapter.ExpandableListViewAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingBookFragment extends Fragment implements BookingBookPresenter.View,
        ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener, View.OnClickListener{
    private BookingBookPresenter bookingBookPresenter;
    private MainActivityPresenter mainActivityPresenter;
    private ExpandableListView list;
    private ExpandableListViewAdapter adapter;
    private TimeComparator timeComparator;
    private TimeManager timeManager;
    private AppointmentManager appointmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_book, container, false);

        init();

        //Get finish button
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
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

        return view;
    }

//    @Override
//    public void update(Object o) {
//        JSONObject status = (JSONObject) o;
//
//        if (status == null) {
//            System.out.println("ERROR IN PHP FILE");
//            return;
//        }
//
//        try {
//            if (status.has("Select_DaysOfWeek")) {
//                //Receive response from Select_DaysOfWeek.php
//                daysList.clear();
//                JSONArray days = status.getJSONArray("Select_DaysOfWeek");//Get the array of days' JSONObject
//                int j = days.length();
//                if (booking.getVoucherID().equals("1"))
//                    j -= 2;
//                for (int i = 0; i < j; i++) {
//                    JSONObject jOb = (JSONObject) days.get(i);
//                    daysList.add(jOb.getString("DAY"));
//                }
//                adapter.updateGroupList(daysList);
//            }else if (status.has("Select_AllTime") || status.has("Select_EcoTime")) {
//                //Receive response from Select_AllTime.php
//                JSONArray time = status.getJSONArray("Select_AllTime");//Get the array of time
//                for (int i = 0; i < time.length(); i++) {
//                    JSONObject jOb = (JSONObject) time.get(i);
//                    timeList.add(jOb.getString("TIME"));
//                }
//            }else if (status.has("Select_SelectedTime")){
//                availableTime.clear();
//                availableTime.addAll(timeList);
//                JSONArray selected = status.getJSONArray("Select_SelectedTime");//Get the array of time
//                for (int i = 0; i < selected.length(); i++) {
//                    JSONObject jOb = (JSONObject) selected.get(i);
//                    if (availableTime.contains(jOb.getString("TIME")))
//                        availableTime.remove(jOb.getString("TIME"));
//                }
//                if (booking.getVoucherID().equals("1")){
//                    for (int i = 0; i < timeList.size(); i++) {
//                        if (i <= 5 || (i<=27 && i>=23) || i>=45) {
//                            if (availableTime.contains(timeList.get(i)))
//                                availableTime.remove(timeList.get(i));
//                        }
//                    }
//                }
//                adapter.updateChildList(availableTime);
//            }else if (status.has("BookingTransaction")){
//                if (status.getString("BookingTransaction").equals("Exist")){
//                    Toast toast = Toast.makeText(getActivity(), "Giờ đã bị đặt", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }else if (status.getString("BookingTransaction").equals("NonExist")){
//                    System.out.println("??????? double");
//                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(day_id - 1) + " - " + timeList.get(time_id - 1));
//                    availableTime.remove(timeList.get(time_id - 1));
//                    adapter.notifyDataSetChanged();
//                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
//                }else if (status.getString("BookingTransaction").equals("QueryFailed")){
//                    System.out.println("Query Failed");
//                }
//            }else if (status.has("Insert_NewAppointment")){
//                if (status.getString("Insert_NewAppointment").equals("Inserted")){
//                    System.out.println("Insert success double");
//                }else if (status.getString("Insert_NewAppointment").equals("Failed")){
//                    System.out.println("Insert fail");
//                }
//            }
//        }catch (JSONException je){
//            je.printStackTrace();
//        }
//    }

    public void init(){
        //Init presenter
        mainActivityPresenter = ((MainActivity)getActivity()).getMainPresenter();
        bookingBookPresenter = new BookingBookPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, mainActivityPresenter.getDTOAppointment());
        //Create time comparator
        timeComparator = new TimeComparator();
        timeManager = new TimeManagerImpl(iCareApiImpl.getAPI());
        appointmentManager = new AppointmentManagerImpl(iCareApiImpl.getAPI());
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

    }

    @Override
    public void hideProgress() {

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
            bookingBookPresenter.getSelectedTime(adapter.getGroup(groupPosition), timeManager);
        }

        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view,  int groupPosition, int childPosition, long childId) {
        if (bookingBookPresenter.getNumberOfCartItems() == 3) {
            showError(getString(R.string.max_item));
            return false;
        }

        bookingBookPresenter.onTimeSelected(adapter.getGroup(groupPosition), adapter.getChild(groupPosition, childPosition), appointmentManager);

        list.collapseGroup(groupPosition);

        return true;
    }

    @Override
    public void updateCart() {
        mainActivityPresenter.addCartItem();
    }

    @Override
    public void onClick(View view) {
        if (bookingBookPresenter.getNumberOfCartItems() <= 0){
            showError(getString(R.string.min_item));
        }else {
            mainActivityPresenter.validateAppointment();
            mainActivityPresenter.insertAppointment();
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
