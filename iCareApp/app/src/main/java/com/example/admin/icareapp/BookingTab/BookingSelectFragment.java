package com.example.admin.icareapp.BookingTab;

import android.app.DatePickerDialog;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.admin.icareapp.Controller.Controller;
import com.example.admin.icareapp.Model.DatabaseObserver;
import com.example.admin.icareapp.Model.ModelURL;
import com.example.admin.icareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingSelectFragment extends Fragment implements DatabaseObserver, AdapterView.OnItemSelectedListener, View.OnClickListener{
    private Controller aController;
    private List<String> countriesName, citiesName, districtsName, locationsName, vouchersName, typesName;
    private Map<String, String> mapToCountryID, mapToCityID, mapToDistrictID, mapToLocationID;
    private CustomSpinnerAdapter countryAdapter, cityAdapter, districtAdapter, locationAdapter, voucherAdapter;
    private Spinner countrySp, citySp, districtSp, locationSp, voucherSp;
    private Calendar myCalendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_select, container, false);

        //Get controller instance
        aController = Controller.getInstance();

        //Initialize data and the first string is hint
        countriesName = new ArrayList<>();
        countriesName.add(getString(R.string.booking_country_hint));
        citiesName = new ArrayList<>();
        citiesName.add(getString(R.string.booking_city_hint));
        districtsName = new ArrayList<>();
        districtsName.add(getString(R.string.booking_district_hint));
        locationsName = new ArrayList<>();
        locationsName.add(getString(R.string.booking_location_hint));
        vouchersName = new ArrayList<>();
        vouchersName.add(getString(R.string.booking_voucher_hint));
        typesName = new ArrayList<>();
        typesName.add(getString(R.string.booking_type_hint));

        //Initialize mapping to ID in database
        mapToCountryID = new HashMap<>();
        mapToCityID = new HashMap<>();
        mapToDistrictID = new HashMap<>();
        mapToLocationID = new HashMap<>();

        //Set up Spinners
        countrySp = (Spinner) view.findViewById(R.id.spinner_countries);
        citySp = (Spinner) view.findViewById(R.id.spinner_cities);
        districtSp = (Spinner) view.findViewById(R.id.spinner_districts);
        locationSp = (Spinner) view.findViewById(R.id.spinner_locations);
        voucherSp = (Spinner) view.findViewById(R.id.spinner_vouchers);

        countryAdapter = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, countriesName);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySp.setAdapter(countryAdapter);
        countrySp.setSelection(0, false);

        cityAdapter = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, citiesName);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySp.setAdapter(cityAdapter);
        citySp.setSelection(0, false);

        districtAdapter = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, districtsName);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSp.setAdapter(districtAdapter);
        districtSp.setSelection(0, false);

        locationAdapter = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, locationsName);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSp.setAdapter(locationAdapter);
        locationSp.setSelection(0, false);

        voucherAdapter = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, vouchersName);
        voucherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voucherSp.setAdapter(voucherAdapter);
        voucherSp.setSelection(0, false);

        //Set Listeners for Spinners
        countrySp.setOnItemSelectedListener(this);
        citySp.setOnItemSelectedListener(this);
        districtSp.setOnItemSelectedListener(this);
        locationSp.setOnItemSelectedListener(this);
        voucherSp.setOnItemSelectedListener(this);

        //Initialize data for Country Spinner first
        aController.setRequestData(getActivity(), this, ModelURL.SELECT_COUNTRIES.getUrl(), "");

        myCalendar = Calendar.getInstance();

        TextInputEditText startDate = (TextInputEditText) view.findViewById(R.id.booking_startdate);
        startDate.setOnClickListener(this);
        TextInputEditText endDate = (TextInputEditText) view.findViewById(R.id.booking_enddate);
        endDate.setOnClickListener(this);

        return view;
    }

    private void updateLabel(TextInputEditText t) {

        String myFormat = "dd-mm-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        t.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        final View v = view;

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel((TextInputEditText) v);
            }

        };
        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;
        try {
            if (status.has("Select_Countries")) {
                //Receive response from Select_Countries.php
                //Get the array of countries' JSONObject
                JSONArray countries = status.getJSONArray("Select_Countries");
                //Populate countriesName list if data exists
                populateData(countriesName, mapToCountryID, countries, "COUNTRY_ID", "COUNTRY");
                countrySp.setSelection(235);
            }else if (status.has("Select_Cities")) {
                //Receive response from Select_Cities.php
                //Get the array of cities' JSONObject
                JSONArray cities = status.getJSONArray("Select_Cities");
                //Populate citiesName list if data exists
                populateData(citiesName, mapToCityID, cities, "CITY_ID", "CITY");
                citySp.setSelection(58);
            }else if (status.has("Select_Districts")) {
                //Receive response from Select_Districts.php
                //Get the array of districts'
                JSONArray districts = status.getJSONArray("Select_Districts");
                //Populate districtsName list if data exists
                populateData(districtsName, mapToDistrictID, districts, "DISTRICT_ID", "DISTRICT");
                districtSp.setSelection(8);
            }else if (status.has("Select_Locations")) {
                //Receive response from Select_Districts.php
                //Get the array of districts' JSONObject
                JSONArray locations = status.getJSONArray("Select_Locations");
                //Populate districtsName list if data exists
                populateData(locationsName, mapToLocationID, locations, "ID", "ADDRESS");
                //Set Spinner clickable to true
                locationSp.setClickable(true);
            } else{
                System.out.println("ERROR IN PHP FILE");
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    public void populateData(List<String> populateList, Map<String,String> mapToID, JSONArray data, String id, String type){
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject jOb = (JSONObject) data.get(i);
                populateList.add(jOb.getString(type));
                mapToID.put(jOb.getString(type), jOb.getString(id));
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinner_countries:
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_CITIES.getUrl(), "country_id=" + mapToCountryID.get(countrySp.getSelectedItem().toString()));
                break;
            case R.id.spinner_cities: 
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_DISTRICTS.getUrl(), "city_id=" + mapToCityID.get(citySp.getSelectedItem().toString()));
                break;
            case R.id.spinner_districts:
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_LOCATIONS.getUrl(), "district_id=" + mapToDistrictID.get(districtSp.getSelectedItem().toString()));
                break;
            case R.id.spinner_locations:
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_LOCATIONS.getUrl(), "location_id=" + mapToLocationID.get(locationSp.getSelectedItem().toString()));
                break;
            case R.id.spinner_vouchers:

                break;
            default:
                break;
        }
    }

    private class CustomSpinnerAdapter extends ArrayAdapter{

        public CustomSpinnerAdapter(Context ctx, int layout, List<String> list){
            super(ctx, layout, list);
        }

        @Override
        public boolean isEnabled(int position){
            if(position == 0)
            {
                // Disable the first item from Spinner
                // First item will be use for hint
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}
