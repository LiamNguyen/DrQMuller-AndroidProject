package com.lanthanh.admin.icareapp.BookingTab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Confirm.ConfirmBookingActivity;
import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelBookingDetail;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingBookFragment extends Fragment implements DatabaseObserver, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener, View.OnClickListener{
    private Controller aController;
    private List<String> timeList, daysList, availableTime;
    private ExpandableListView list;
    private ExpandableListViewAdapter adapter;
    private TimeComparator timeComparator;
    private int day_id, time_id;
    private ModelBookingDetail booking;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_book, container, false);

        //Get controller
        aController = Controller.getInstance();
        //Get ModelBookingDetails
        booking = ((MainActivity) getActivity()).getModelBooking();
        //Get finish button
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.booking_finish_button);
        button.setOnClickListener(this);
        //Create time comparator
        timeComparator = new TimeComparator();
        //Get expandable list
        list = (ExpandableListView) view.findViewById(R.id.expListView);
        //Initialize list
        timeList = new ArrayList<>();
        availableTime = new ArrayList<>();
        daysList = new ArrayList<>();
        list.setOnChildClickListener(this);
        list.setOnGroupClickListener(this);
        list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    list.collapseGroup(previousItem );
                previousItem = groupPosition;
                list.setSelectedGroup(groupPosition);
            }
        });

        //Initialize list adapter
        adapter = new ExpandableListViewAdapter(getActivity(), daysList, availableTime);
        list.setAdapter(adapter);

        //Get days of week and all time in day
        aController.setRequestData(getActivity(), this, ModelURL.SELECT_DAYSOFWEEK.getUrl(), "");
        aController.setRequestData(getActivity(), this, ModelURL.SELECT_ALLTIMEINADAY.getUrl(), "");

        return view;
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        if (status == null) {
            System.out.println("ERROR IN PHP FILE");
            return;
        }

        try {
            if (status.has("Select_DaysOfWeek")) {
                //Receive response from Select_DaysOfWeek.php
                daysList.clear();
                JSONArray days = status.getJSONArray("Select_DaysOfWeek");//Get the array of days' JSONObject
                int j = days.length();
                if (booking.getVoucherID().equals("1"))
                    j -= 2;
                for (int i = 0; i < j; i++) {
                    JSONObject jOb = (JSONObject) days.get(i);
                    daysList.add(jOb.getString("DAY"));
                }
                adapter.updateGroupList(daysList);
            }else if (status.has("Select_AllTime")) {
                //Receive response from Select_AllTime.php
                JSONArray time = status.getJSONArray("Select_AllTime");//Get the array of time
                for (int i = 0; i < time.length(); i++) {
                    JSONObject jOb = (JSONObject) time.get(i);
                    timeList.add(jOb.getString("TIME"));
                }
            }else if (status.has("Select_SelectedTime")){
                availableTime.clear();
                availableTime.addAll(timeList);
                JSONArray selected = status.getJSONArray("Select_SelectedTime");//Get the array of time
                for (int i = 0; i < selected.length(); i++) {
                    JSONObject jOb = (JSONObject) selected.get(i);
                    if (availableTime.contains(jOb.getString("TIME")))
                        availableTime.remove(jOb.getString("TIME"));
                }
                adapter.updateChildList(availableTime);
            }else if (status.has("Select_CheckTimeExistence")){
                if (status.getString("Select_CheckTimeExistence").equals("Exist"))
                    aController.setRequestData(getActivity(), this, ModelURL.UPDATE_CHOSENTIME.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                else
                    aController.setRequestData(getActivity(), this, ModelURL.INSERT_NEWTEMPTIME.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view,  int groupPosition, int childPosition, long childId) {
        if (((MainActivity) getActivity()).numberOfCartItems() == 3) {
            Toast.makeText(getActivity(), getString(R.string.max_item), Toast.LENGTH_LONG).show();
            return false;
        }

        TextView tv = (TextView) view.findViewById(R.id.time_of_day);
        switch (groupPosition){
            case 0://Monday
                if (!booking.checkDay("1")) {
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(0) + " - " + tv.getText().toString());
                    availableTime.remove(childPosition);
                    adapter.notifyDataSetChanged();
                    //day_id = 1;
                    time_id = timeList.indexOf(tv.getText().toString()) + 1;
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else{
                    Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            case 1://Tuesday
                if (!booking.checkDay("2")) {
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(1) + " - " + tv.getText().toString());
                    availableTime.remove(childPosition);
                    adapter.notifyDataSetChanged();
                    //day_id = 2;
                    time_id = timeList.indexOf(tv.getText().toString()) + 1;
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else{
                    Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            case 2://Wednesday
                if (!booking.checkDay("3")) {
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(2) + " - " + tv.getText().toString());
                    availableTime.remove(childPosition);
                    adapter.notifyDataSetChanged();
                    //day_id = 3;
                    time_id = timeList.indexOf(tv.getText().toString()) + 1;
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else{
                    Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            case 3://Thursday
                if (!booking.checkDay("4")) {
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(3) + " - " + tv.getText().toString());
                    availableTime.remove(childPosition);
                    adapter.notifyDataSetChanged();
                    //day_id = 4;
                    time_id = timeList.indexOf(tv.getText().toString()) + 1;
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else{
                    Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            case 4://Friday
                if (!booking.checkDay("5")) {
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(4) + " - " + tv.getText().toString());
                    availableTime.remove(childPosition);
                    adapter.notifyDataSetChanged();
                    //day_id = 5;
                    time_id = timeList.indexOf(tv.getText().toString()) + 1;
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else{
                    Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            case 5://Saturday
                if (!booking.checkDay("6")) {
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(5) + " - " + tv.getText().toString());
                    availableTime.remove(childPosition);
                    adapter.notifyDataSetChanged();
                    //day_id = 6;
                    time_id = timeList.indexOf(tv.getText().toString()) + 1;
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else{
                    Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            case 6://Sunday
                if (!booking.checkDay("7")) {
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(6) + " - " + tv.getText().toString());
                    availableTime.remove(childPosition);
                    adapter.notifyDataSetChanged();
                    //day_id = 7;
                    time_id = timeList.indexOf(tv.getText().toString()) + 1;
                    aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(), "day_id=" + day_id + "&time_id=" + time_id);
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else{
                    Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_LONG).show();
                    return false;
                }
                break;
            default:
                break;
        }

        list.collapseGroup(groupPosition);

        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long groupId) {
         switch (groupPosition){
            case 0://Monday
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(), "day_id=1");
                day_id = 1;
                break;
            case 1://Tuesday
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(), "day_id=2");
                day_id = 2;
                break;
            case 2://Wednesday
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(), "day_id=3");
                day_id = 3;
                break;
            case 3://Thursday
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(), "day_id=4");
                day_id = 4;
                break;
            case 4://Friday
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(), "day_id=5");
                day_id = 5;
                break;
            case 5://Saturday
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(), "day_id=6");
                day_id = 6;
                break;
            case 6://Sunday
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(), "day_id=7");
                day_id = 7;
                break;
            default:
                break;
        }

        return false;
    }

    public void refreshTimeList(String day, String time){
        if (adapter.getGroup(day_id-1).equals(day)) {
            availableTime.add(time);
            Collections.sort(availableTime, timeComparator);
            adapter.notifyDataSetChanged();
        }
        aController.setRequestData(getActivity(), this, ModelURL.UPDATE_UNCHOSENTIME.getUrl(), "day_id=" + (daysList.indexOf(day) + 1) + "&time_id=" + (timeList.indexOf(time) + 1));
        booking.deleteBooking(Integer.toString(daysList.indexOf(day) + 1));
    }

    @Override
    public void onClick(View view) {
        if (((MainActivity) getActivity()).numberOfCartItems() <= 0){
            Toast.makeText(getActivity(), getString(R.string.min_item), Toast.LENGTH_LONG).show();
        }else {
            SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);
            booking.setCustomerID(sharedPref.getString("tokenID", ""));
            booking.generateCode();
            aController.setRequestData(getActivity(), this, ModelURL.INSERT_NEWAPPOINTMENT.getUrl(), booking.getPostData());
            for (String s: booking.getBookingDays()){
                aController.setRequestData(getActivity(), this, ModelURL.INSERT_NEWBOOKING.getUrl(), "day_id=" + s + "&time_id=" + booking.getBookingTime(s) + "&code=" + booking.getCode());
            }
            ((MainActivity) getActivity()).emptyCart();
            Intent toConfirm = new Intent(getActivity(), ConfirmBookingActivity.class);
            startActivity(toConfirm);
            putBookingToPref();
            booking.emptyDay();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            aController.setRequestData(getActivity(), this, ModelURL.SELECT_DAYSOFWEEK.getUrl(), "");
    }

    public void putBookingToPref(){
        Gson gson = new Gson();

        JSONArray data = new JSONArray();
        JSONArray bookingCode = new JSONArray();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);

        data.put(sharedPref.getString("tokenName", "Tên Khách Hàng"));
        data.put(booking.getVoucher());
        data.put(booking.getLocation());
        data.put(booking.getStartDate());
        data.put(booking.getExpireDate());
        data.put("Chưa Xác Nhận");
        data.put(booking.getCode());

        if (!sharedPref.getString("bookingCode", "").isEmpty())
            bookingCode = gson.fromJson(sharedPref.getString("bookingCode", ""), JSONArray.class);

        SharedPreferences.Editor editor = sharedPref.edit();
        bookingCode.put("booking"+data.hashCode());
        editor.putString("bookingCode", gson.toJson(bookingCode));
        editor.putString("booking"+data.hashCode(), gson.toJson(data));

        editor.apply();
        editor.commit();
    }
}
