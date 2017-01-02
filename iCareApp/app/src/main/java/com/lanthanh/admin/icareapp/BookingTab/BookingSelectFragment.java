package com.lanthanh.admin.icareapp.BookingTab;

import android.app.DatePickerDialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelBookingDetail;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private Map<String, String> mapToCountryID, mapToCityID, mapToDistrictID, mapToLocationID, mapToVoucherID;
    private CustomSpinnerAdapter countryAdapter, cityAdapter, districtAdapter, locationAdapter, voucherAdapter, typeAdapter;
    private Spinner countrySp, citySp, districtSp, locationSp, voucherSp, typeSp;
    private Calendar startCalendar, endCalendar;
    private TextInputEditText startDate, endDate;
    private ModelBookingDetail booking;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_select, container, false);

        aController = Controller.getInstance();

        //BookingDetails object
        booking = ((MainActivity) getActivity()).getModelBooking();

        /* =============================== SPINNER =============================== */
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
        typesName.add(getString(R.string.booking_type_free));
        typesName.add(getString(R.string.booking_type_fixed));

        //Initialize mapping to ID in database
        mapToCountryID = new HashMap<>();
        mapToCityID = new HashMap<>();
        mapToDistrictID = new HashMap<>();
        mapToLocationID = new HashMap<>();
        mapToVoucherID = new HashMap<>();

        //Set up Spinners
        countrySp = (Spinner) view.findViewById(R.id.spinner_countries);
        countrySp.setEnabled(true);
        countrySp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    alert(getString(R.string.temp_inform));
                    return true;
                }
                return false;
            }
        });
        citySp = (Spinner) view.findViewById(R.id.spinner_cities);
        citySp.setEnabled(true);
        citySp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    alert(getString(R.string.temp_inform));
                    return true;
                }
                return false;
            }
        });
        districtSp = (Spinner) view.findViewById(R.id.spinner_districts);
        districtSp.setEnabled(true);
        districtSp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    alert(getString(R.string.temp_inform));
                    return true;
                }
                return false;
            }
        });
        locationSp = (Spinner) view.findViewById(R.id.spinner_locations);
        locationSp.setEnabled(false);
        voucherSp = (Spinner) view.findViewById(R.id.spinner_vouchers);
        voucherSp.setEnabled(false);
        typeSp = (Spinner) view.findViewById(R.id.spinner_type);
        typeSp.setEnabled(false);

        countryAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.booking_spinner_item, countriesName);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySp.setAdapter(countryAdapter);
        countrySp.setSelection(0, false);

        cityAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.booking_spinner_item, citiesName);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySp.setAdapter(cityAdapter);
        citySp.setSelection(0, false);

        districtAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.booking_spinner_item, districtsName);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSp.setAdapter(districtAdapter);
        districtSp.setSelection(0, false);

        locationAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.booking_spinner_item, locationsName);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSp.setAdapter(locationAdapter);
        locationSp.setSelection(0, false);

        voucherAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.booking_spinner_item, vouchersName);
        voucherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voucherSp.setAdapter(voucherAdapter);
        voucherSp.setSelection(0, false);

        typeAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.booking_spinner_item, typesName);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(typeAdapter);
        typeSp.setSelection(0, false);

        //Set Listeners for Spinners
        countrySp.setOnItemSelectedListener(this);
        citySp.setOnItemSelectedListener(this);
        districtSp.setOnItemSelectedListener(this);
        locationSp.setOnItemSelectedListener(this);
        voucherSp.setOnItemSelectedListener(this);
        typeSp.setOnItemSelectedListener(this);

        /* =============================== PICK DATE =============================== */
        //Initialize TextInputEditText
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font
        startDate = (TextInputEditText) view.findViewById(R.id.booking_startdate);
        startDate.setOnClickListener(this);
        startDate.setTypeface(font);
        endDate = (TextInputEditText) view.findViewById(R.id.booking_enddate);
        endDate.setOnClickListener(this);
        endDate.setTypeface(font);

        /* =============================== LAST STEP =============================== */
        //Initialize data for Country Spinner first
        aController.setRequestData(getActivity(), this, ModelURL.SELECT_COUNTRIES.getUrl(MainActivity.isUAT), "");

        AppCompatButton nextBut = (AppCompatButton) view.findViewById(R.id.booking_next_button);
        nextBut.setOnClickListener(this);
        nextBut.setTypeface(font);

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible())
            aController.setRequestData(getActivity(), this, ModelURL.SELECT_COUNTRIES.getUrl(MainActivity.isUAT), "");
    }

    private void updateDateLabel(TextInputEditText t) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        t.setText(sdf.format(startCalendar.getTime()));

        if (t.getId() == R.id.booking_startdate){
            endDate.setEnabled(true);
            endCalendar = startCalendar;
            endCalendar.add(Calendar.DATE, 1);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.booking_next_button){
            if (booking.isDataEmpty(typeSp.getSelectedItem().toString().equals(getString(R.string.booking_type_fixed)))){
                Toast toast = Toast.makeText(getActivity(), getString(R.string.missing_detail), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else {
                aController.setRequestData(getActivity(), this, ModelURL.UPDATE_VALIDATEAPPOINTMENT.getUrl(MainActivity.isUAT), "");
                ((MainActivity) getActivity()).navigateToBook();
            }
        }else {
            startCalendar = Calendar.getInstance();

            final View v = view;

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    if (v.getId() == R.id.booking_startdate) {
                        if (startCalendar.get(Calendar.YEAR) > year
                                || (startCalendar.get(Calendar.YEAR) == year && startCalendar.get(Calendar.MONTH) > monthOfYear)
                                || (startCalendar.get(Calendar.YEAR) == year && startCalendar.get(Calendar.MONTH) == monthOfYear && startCalendar.get(Calendar.DAY_OF_MONTH) > dayOfMonth)) {
                            Toast toast = Toast.makeText(getActivity(), getString(R.string.booking_error_date), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            startCalendar.set(Calendar.YEAR, year);
                            startCalendar.set(Calendar.MONTH, monthOfYear);
                            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            if (endCalendar != null && (startCalendar.get(Calendar.YEAR) > endCalendar.get(Calendar.YEAR)
                                    || (startCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && startCalendar.get(Calendar.MONTH) > endCalendar.get(Calendar.MONTH))
                                    || (startCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && startCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) && startCalendar.get(Calendar.DAY_OF_MONTH) > endCalendar.get(Calendar.DAY_OF_MONTH)))){
                                endDate.setText("Ngày Kết Thúc");
                                booking.clearExpireDate();
                            }
                            updateDateLabel((TextInputEditText) v);
                            booking.setStartDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    } else {
                        if (endCalendar == null)
                            endCalendar = startCalendar;
                        if ((endCalendar.get(Calendar.YEAR) > year
                                || (endCalendar.get(Calendar.YEAR) == year && endCalendar.get(Calendar.MONTH) > monthOfYear)
                                || (endCalendar.get(Calendar.YEAR) == year && endCalendar.get(Calendar.MONTH) == monthOfYear && endCalendar.get(Calendar.DAY_OF_MONTH) > dayOfMonth))
                                && booking.getType().equals("1")) {
                            Toast toast = Toast.makeText(getActivity(), getString(R.string.booking_error_date), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            startCalendar.set(Calendar.YEAR, year);
                            startCalendar.set(Calendar.MONTH, monthOfYear);
                            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateDateLabel((TextInputEditText) v);
                            booking.setExpireDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }
                }

            };
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), date, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

            if (v.getId() == R.id.booking_startdate)
                datePicker.getDatePicker().setMinDate(startCalendar.getTimeInMillis());
            else {
                if (endCalendar != null) {
                    if (booking.getType().equals("1"))
                        datePicker.getDatePicker().setMinDate(endCalendar.getTimeInMillis());
                    else
                        datePicker.getDatePicker().setMinDate(startCalendar.getTimeInMillis());
                }
                else
                    datePicker.getDatePicker().setMinDate(startCalendar.getTimeInMillis());
            }

            datePicker.show();
        }
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        if (status == null) {
            System.out.println("ERROR IN PHP FILE");
            return;
        }

        try {
            if (status.has("Select_Countries")) {
                //Receive response from Select_Countries.php
                JSONArray countries = status.getJSONArray("Select_Countries");//Get the array of countries' JSONObject
                populateData(countriesName, mapToCountryID, countries, "COUNTRY_ID", "COUNTRY");//Populate countriesName list if data exists
                countrySp.setSelection(235);
            }else if (status.has("Select_Cities")) {
                //Receive response from Select_Cities.php
                JSONArray cities = status.getJSONArray("Select_Cities");//Get the array of cities' JSONObject
                populateData(citiesName, mapToCityID, cities, "CITY_ID", "CITY");//Populate citiesName list if data exists
                citySp.setSelection(58);
            }else if (status.has("Select_Districts")) {
                //Receive response from Select_Districts.php
                JSONArray districts = status.getJSONArray("Select_Districts");//Get the array of districts' JSONObject
                populateData(districtsName, mapToDistrictID, districts, "DISTRICT_ID", "DISTRICT");//Populate districtsName list if data exists
                districtSp.setSelection(8);
            }else if (status.has("Select_Locations")) {
                //Receive response from Select_Districts.php
                JSONArray locations = status.getJSONArray("Select_Locations");//Get the array of districts' JSONObject
                populateData(locationsName, mapToLocationID, locations, "LOCATION_ID", "ADDRESS");//Populate districtsName list if data exists
                locationSp.setEnabled(true);//Set Spinner clickable to true
            }else if (status.has("Select_Vouchers")){
                //Receive response from Select_Vouchers.php
                JSONArray vouchers = status.getJSONArray("Select_Vouchers");//Get the array of vouchers' JSONObject
                populateData(vouchersName, mapToVoucherID, vouchers, "VOUCHER_ID", "VOUCHER", "PRICE");//Populate districtsName list if data exists
                voucherSp.setEnabled(true);//Set Spinner clickable to true
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    public void populateData(List<String> populateList, Map<String,String> mapToID, JSONArray data, String... params){
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject jOb = (JSONObject) data.get(i);
                if (params.length == 3)
                    populateList.add(jOb.getString(params[1]) + " - " + jOb.getString(params[2]) + " VND");
                else
                    populateList.add(jOb.getString(params[1]));
                mapToID.put(jOb.getString(params[1]), jOb.getString(params[0]));
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
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_CITIES.getUrl(MainActivity.isUAT), "country_id=" + mapToCountryID.get(countrySp.getSelectedItem().toString()));
                break;
            case R.id.spinner_cities:
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_DISTRICTS.getUrl(MainActivity.isUAT), "city_id=" + mapToCityID.get(citySp.getSelectedItem().toString()));
                break;
            case R.id.spinner_districts:
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_LOCATIONS.getUrl(MainActivity.isUAT), "district_id=" + mapToDistrictID.get(districtSp.getSelectedItem().toString()));
                break;
            case R.id.spinner_locations:
                aController.setRequestData(getActivity(), this, ModelURL.SELECT_VOUCHERS.getUrl(MainActivity.isUAT), "");
                booking.setLocation(mapToLocationID.get(locationSp.getSelectedItem().toString()), locationSp.getSelectedItem().toString(), districtSp.getSelectedItem().toString(), citySp.getSelectedItem().toString(), countrySp.getSelectedItem().toString());
                break;
            case R.id.spinner_vouchers:
                if (!booking.getVoucher().isEmpty() && !booking.getVoucher().equals(voucherSp.getSelectedItem().toString()))
                    ((MainActivity) getActivity()).releaseCartWhenReselect();
                typeSp.setEnabled(true);
                booking.setVoucher(mapToVoucherID.get(voucherSp.getSelectedItem().toString().substring(0, voucherSp.getSelectedItem().toString().indexOf("-") - 1)), voucherSp.getSelectedItem().toString());
                break;
            case R.id.spinner_type:
                startDate.setEnabled(false);
                startDate.setText(getString(R.string.booking_start_date));
                endDate.setEnabled(false);
                endDate.setText(getString(R.string.booking_end_date));
                endCalendar = null;
                if (typeSp.getSelectedItem().toString().equals(getString(R.string.booking_type_fixed))) {
                    startDate.setVisibility(View.VISIBLE);
                    startDate.setEnabled(true);
                    booking.setType(Integer.toString(1));
                }
                else {
                    startDate.setVisibility(View.INVISIBLE);
                    endDate.setText(getString(R.string.booking_do_date));
                    endDate.setEnabled(true);
                    booking.setType(Integer.toString(2));
                }
            default:
                break;
        }
    }

    private class CustomSpinnerAdapter extends ArrayAdapter{
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");//Custom font

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

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;

            if(position == 0)
                tv.setTextColor(getResources().getColor(R.color.colorLightGray));// Set the hint text color gray
            else
                tv.setTextColor(getResources().getColor(R.color.colorLightBlack));
            tv.setTypeface(font);
            return view;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            ((TextView) view).setTypeface(font);
            TextView tv = (TextView) view;

            if (parent.getId() == R.id.spinner_countries || parent.getId() == R.id.spinner_cities || parent.getId() == R.id.spinner_districts){
                if (!parent.isEnabled())
                    tv.setTextColor(getResources().getColor(R.color.colorLightBlack));
            }

            return view;
        }
    }

    void alert(String msg) {
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 110);
        toast.show();

    }
}
