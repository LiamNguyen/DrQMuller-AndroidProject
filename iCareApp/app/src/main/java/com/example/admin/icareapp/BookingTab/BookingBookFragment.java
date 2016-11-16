package com.example.admin.icareapp.BookingTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.admin.icareapp.ExpandableListViewAdapter;
import com.example.admin.icareapp.Model.DatabaseObserver;
import com.example.admin.icareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingBookFragment extends Fragment implements DatabaseObserver{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_book, container, false);

        ExpandableListView list = (ExpandableListView) view.findViewById(R.id.expListView);
        List<String> daysList = new ArrayList<>();
        daysList.add(getString(R.string.monday));
        daysList.add(getString(R.string.tuesday));
        daysList.add(getString(R.string.wednesday));
        daysList.add(getString(R.string.thursday));
        daysList.add(getString(R.string.friday));





        return view;
    }



    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;
        try {
            if (status.has("Select_DaysOfWeek")) {
                //Receive response from Select_DaysOfWeek.php
                //Get the array of days' JSONObject
                JSONArray days = status.getJSONArray("Select_DaysOfWeek");
                for (int i = 0; i < days.length(); i++) {
                    JSONObject jOb = (JSONObject) days.get(i);
                    //daysList.add(jOb.getString("DAY"));;
                }
            }else{
                System.out.println("ERROR IN PHP FILE");
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }
}
