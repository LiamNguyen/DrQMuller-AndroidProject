package com.lanthanh.admin.icareapp.BookingTab;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.booking_finish_button);
        button.setOnClickListener(this);
        button.setTypeface(font);
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
        if (booking.getType().equals("1"))
            aController.setRequestData(getActivity(), this, ModelURL.SELECT_DAYSOFWEEK.getUrl(MainActivity.isUAT), "");
        else{
            daysList.clear();
            adapter.notifyDataSetInvalidated();
            switch (getDateOfWeek(booking.getFormattedExpireDate())){
                case 1:
                    daysList.add("Chủ nhật\n- " + booking.getFormattedExpireDate());
                    break;
                case 2:
                    daysList.add("Thứ hai\n- " + booking.getFormattedExpireDate());
                    break;
                case 3:
                    daysList.add("Thứ ba\n- " + booking.getFormattedExpireDate());
                    break;
                case 4:
                    daysList.add("Thứ tư\n- " + booking.getFormattedExpireDate());
                    break;
                case 5:
                    daysList.add("Thứ năm\n- " + booking.getFormattedExpireDate());
                    break;
                case 6:
                    daysList.add("Thứ sáu\n- " + booking.getFormattedExpireDate());
                    break;
                case 7:
                    daysList.add("Thứ bảy\n- " + booking.getFormattedExpireDate());
                    break;
                default:
                    break;
            }
        }
//        if (booking.getVoucherID().equals("1"))
        aController.setRequestData(getActivity(), this, ModelURL.SELECT_ALLTIMEINADAY.getUrl(MainActivity.isUAT), "");
//        else
//            aController.setRequestData(getActivity(), this, ModelURL.SELECT_ECOTIME.getUrl(MainActivity.isUAT), "");

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
            }else if (status.has("Select_AllTime") || status.has("Select_EcoTime")) {
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
                if (booking.getVoucherID().equals("1")){
                    for (int i = 0; i < timeList.size(); i++) {
                        if (i <= 5 || (i<=27 && i>=23) || i>=45) {
                            if (availableTime.contains(timeList.get(i)))
                                availableTime.remove(timeList.get(i));
                        }
                    }
                }
                adapter.updateChildList(availableTime);
            }else if (status.has("BookingTransaction")){
                if (status.getString("BookingTransaction").equals("Exist")){
                    Toast toast = Toast.makeText(getActivity(), "Giờ đã bị đặt", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else if (status.getString("BookingTransaction").equals("NonExist")){
                    System.out.println("??????? double");
                    ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(day_id - 1) + " - " + timeList.get(time_id - 1));
                    availableTime.remove(timeList.get(time_id - 1));
                    adapter.notifyDataSetChanged();
                    booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
                }else if (status.getString("BookingTransaction").equals("QueryFailed")){
                    System.out.println("Query Failed");
                }
            }else if (status.has("Insert_NewAppointment")){
                if (status.getString("Insert_NewAppointment").equals("Inserted")){
                    System.out.println("Insert success double");
                }else if (status.getString("Insert_NewAppointment").equals("Failed")){
                    System.out.println("Insert fail");
                }
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view,  int groupPosition, int childPosition, long childId) {
        if (((MainActivity) getActivity()).numberOfCartItems() == 3) {
            Toast toast = Toast.makeText(getActivity(), getString(R.string.max_item), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        TextView tv = (TextView) view.findViewById(R.id.time_of_day);
        time_id = timeList.indexOf(tv.getText().toString()) + 1;
        switch (groupPosition){
            case 0://Monday
                if (!booking.checkDay("1")) {
                    aController.setRequestData(getActivity(), this, ModelURL.BOOKING.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                    //aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMECONCURRENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                }else{
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return false;
                }
                break;
            case 1://Tuesday
                if (!booking.checkDay("2")) {
                    aController.setRequestData(getActivity(), this, ModelURL.BOOKING.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                    //aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMECONCURRENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                }else{
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return false;
                }
                break;
            case 2://Wednesday
                if (!booking.checkDay("3")) {
                    aController.setRequestData(getActivity(), this, ModelURL.BOOKING.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                    //aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMECONCURRENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                }else{
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return false;
                }
                break;
            case 3://Thursday
                if (!booking.checkDay("4")) {
                    aController.setRequestData(getActivity(), this, ModelURL.BOOKING.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                    //aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMECONCURRENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                }else{
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return false;
                }
                break;
            case 4://Friday
                if (!booking.checkDay("5")) {
                    aController.setRequestData(getActivity(), this, ModelURL.BOOKING.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                    //aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMECONCURRENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                }else{
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return false;
                }
                break;
            case 5://Saturday
                if (!booking.checkDay("6")) {
                    aController.setRequestData(getActivity(), this, ModelURL.BOOKING.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                    //aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMECONCURRENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                }else{
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return false;
                }
                break;
            case 6://Sunday
                if (!booking.checkDay("7")) {
                    aController.setRequestData(getActivity(), this, ModelURL.BOOKING.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                    //aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMECONCURRENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
                }else{
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.selected_day), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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
        if (!list.isGroupExpanded(groupPosition)) {
            if (booking.getType().equals("2")){
                switch (getDateOfWeek(booking.getFormattedExpireDate())){
                    case 1:
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=7");
                        day_id = 7;
                        break;
                    case 2:
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=1");
                        day_id = 1;
                        break;
                    case 3:
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=2");
                        day_id = 2;
                        break;
                    case 4:
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=3");
                        day_id = 3;
                        break;
                    case 5:
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=4");
                        day_id = 4;
                        break;
                    case 6:
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=5");
                        day_id = 5;
                        break;
                    case 7:
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=6");
                        day_id = 6;
                        break;
                    default:
                        break;
                }
            }else {
                switch (groupPosition) {
                    case 0://Monday
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=1");
                        day_id = 1;
                        break;
                    case 1://Tuesday
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=2");
                        day_id = 2;
                        break;
                    case 2://Wednesday
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=3");
                        day_id = 3;
                        break;
                    case 3://Thursday
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=4");
                        day_id = 4;
                        break;
                    case 4://Friday
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=5");
                        day_id = 5;
                        break;
                    case 5://Saturday
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=6");
                        day_id = 6;
                        break;
                    case 6://Sunday
                        aController.setRequestData(getActivity(), this, ModelURL.SELECT_SELECTEDTIME.getUrl(MainActivity.isUAT), "day_id=7");
                        day_id = 7;
                        break;
                    default:
                        break;
                }
            }
        }

        return false;
    }

//    public void addSelectedItemToCart(TextView tv, int groupPosition, int childPosition){
//        ((MainActivity) getActivity()).addSelectedItemToCart(daysList.get(groupPosition) + " - " + tv.getText().toString());
//        availableTime.remove(childPosition);
//        adapter.notifyDataSetChanged();
////        time_id = timeList.indexOf(tv.getText().toString()) + 1;
//        aController.setRequestData(getActivity(), this, ModelURL.SELECT_CHECKTIMEEXISTENCE.getUrl(MainActivity.isUAT), "day_id=" + day_id + "&time_id=" + time_id);
//        booking.saveBooking(Integer.toString(day_id), Integer.toString(time_id));
//    }

    public void refreshTimeList(String day, String time){
        if (adapter.getGroup(day_id-1).equals(day)) {
            availableTime.add(time);
            Collections.sort(availableTime, timeComparator);
            adapter.notifyDataSetChanged();
        }
        aController.setRequestData(getActivity(), this, ModelURL.UPDATE_UNCHOSENTIME.getUrl(MainActivity.isUAT), "day_id=" + (daysList.indexOf(day) + 1) + "&time_id=" + (timeList.indexOf(time) + 1));
        booking.deleteBooking(Integer.toString(daysList.indexOf(day) + 1));
    }

    @Override
    public void onClick(View view) {
        if (((MainActivity) getActivity()).numberOfCartItems() <= 0){
            Toast toast = Toast.makeText(getActivity(), getString(R.string.min_item), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else {
            SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);
            booking.setCustomerID(sharedPref.getString("tokenID", ""));
            booking.generateCode();
            aController.setRequestData(getActivity(), this, ModelURL.INSERT_NEWAPPOINTMENT.getUrl(MainActivity.isUAT), booking.getPostData());
            System.out.println(booking.getPostData());
            for (String s: booking.getBookingDays()){
                aController.setRequestData(getActivity(), this, ModelURL.INSERT_NEWBOOKING.getUrl(MainActivity.isUAT), "day_id=" + s + "&time_id=" + booking.getBookingTime(s) + "&code=" + booking.getCode());
            }
            booking.saveBookingInfo(((MainActivity) getActivity()).getCartList());
            System.out.println(booking.getEmailPostData() + "&cus_name=" + sharedPref.getString("tokenName", "Tên Khách Hàng"));
            aController.setRequestData(getActivity(), this, ModelURL.SENDEMAIL_NOTIFYBOOKING.getUrl(MainActivity.isUAT), booking.getEmailPostData() + "&cus_name=" + sharedPref.getString("tokenName", "Tên Khách Hàng") + "&created_day=" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
            ((MainActivity) getActivity()).emptyCart();
            Intent toConfirm = new Intent(getActivity(), ConfirmBookingActivity.class);
            startActivity(toConfirm);
            putBookingToPref();
            booking.emptyDay();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            if (booking.getType().equals("1"))
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_DAYSOFWEEK.getUrl(MainActivity.isUAT), "");
            else{
                daysList.clear();
                adapter.notifyDataSetInvalidated();
                switch (getDateOfWeek(booking.getFormattedExpireDate())){
                    case 1:
                        daysList.add("Chủ nhật\n- " + booking.getFormattedExpireDate());
                        break;
                    case 2:
                        daysList.add("Thứ hai\n- " + booking.getFormattedExpireDate());
                        break;
                    case 3:
                        daysList.add("Thứ ba\n- " + booking.getFormattedExpireDate());
                        break;
                    case 4:
                        daysList.add("Thứ tư\n- " + booking.getFormattedExpireDate());
                        break;
                    case 5:
                        daysList.add("Thứ năm\n- " + booking.getFormattedExpireDate());
                        break;
                    case 6:
                        daysList.add("Thứ sáu\n- " + booking.getFormattedExpireDate());
                        break;
                    case 7:
                        daysList.add("Thứ bảy\n- " + booking.getFormattedExpireDate());
                        break;
                    default:
                        break;
                }
            }
        }
        else
            collapseAllGroups();
    }

    public void collapseAllGroups(){
        int count =  adapter.getGroupCount();
        for (int i = 0; i <count ; i++)
            list.collapseGroup(i);
    }

    public int getDateOfWeek(String s){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = df.parse(s);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

//    public String formatDate(String s){
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
//        Date date;
//        try {
//            date = df.parse(s);
//            System.out.println("check again " + df.format(date));
//            return df.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public void putBookingToPref(){
        int count = 0;
        Gson gson = new Gson();

        JSONArray data = new JSONArray();
        JSONArray bookingCode = new JSONArray();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);

        data.put(sharedPref.getString("tokenName", "Tên Khách Hàng"));
        data.put(booking.getVoucher());
        data.put(booking.getLocation());
        data.put(booking.getStartDate());
        data.put(booking.getExpireDate());
        data.put(getActivity().getString(R.string.not_confirm_yet));
        data.put(booking.getCode());
        for (String s: booking.getBookingDays()){
            int day = Integer.parseInt(s);
            int time = Integer.parseInt(booking.getBookingTime(s));
            data.put(daysList.get(day - 1) + " - " + timeList.get(time - 1));
            count++;
        }
        if (count == 2){
            data.put("");
        }else if (count == 1){
            data.put("");
            data.put("");
        }
        data.put(booking.getType());

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
