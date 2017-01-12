package com.lanthanh.admin.icareapp.presentation.view.fragment.bookingtab;

import android.app.DatePickerDialog;

import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.data.manager.CountryManager;
import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.TypeManager;
import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.data.manager.impl.CityManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CountryManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.DistrictManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.LocationManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.TypeManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.VoucherManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingSelectPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.BookingSelectPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.presentation.view.adapter.CustomSpinnerAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingSelectFragment extends Fragment implements BookingSelectPresenter.View, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, View.OnClickListener{
    private BookingSelectPresenter bookingSelectPresenter;
    private MainActivityPresenter mainActivityPresenter;
    private List<String> countriesName, citiesName, districtsName, locationsName, vouchersName, typesName;
    private CustomSpinnerAdapter countryAdapter, cityAdapter, districtAdapter, locationAdapter, voucherAdapter, typeAdapter;
    private Spinner countrySp, citySp, districtSp, locationSp, voucherSp, typeSp;
    private Calendar calendar;
    private TextInputEditText startDate, expireDate;
    private DatePickerDialog startDatePickerDialog, expireDatePickerDialog;
    private CountryManager countryManager;
    private CityManager cityManager;
    private DistrictManager districtManager;
    private LocationManager locationManager;
    private VoucherManager voucherManager;
    private TypeManager typeManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_select, container, false);

        init();

        /* =============================== SPINNER =============================== */
//        typesName.add(getString(R.string.booking_type_free));
//        typesName.add(getString(R.string.booking_type_fixed));

        //Set up Spinners
        countrySp = (Spinner) view.findViewById(R.id.spinner_countries);
        countrySp.setEnabled(true);
        countrySp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showError(getString(R.string.temp_inform));
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
                    showError(getString(R.string.temp_inform));
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
                    showError(
                            getString(R.string.temp_inform));
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

        //Set up adapter for spinner
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
        expireDate = (TextInputEditText) view.findViewById(R.id.booking_expiredate);
        expireDate.setOnClickListener(this);
        expireDate.setTypeface(font);
        //Initialize DatePickerDialog
        startDatePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        expireDatePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        /* =============================== LAST STEP =============================== */
        //Initialize data for Country Spinner first
        bookingSelectPresenter.getAllCountries(countryManager);

        AppCompatButton nextBut = (AppCompatButton) view.findViewById(R.id.booking_next_button);
        nextBut.setOnClickListener(this);
        nextBut.setTypeface(font);

        return view;
    }

    public void init(){
        //Init presenter
        mainActivityPresenter = ((MainActivity)getActivity()).getMainPresenter();
        bookingSelectPresenter = new BookingSelectPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, mainActivityPresenter.getDTOAppointment());
        //Init calendar and picker dialog
        calendar = Calendar.getInstance();
        //Init managers
        countryManager = new CountryManagerImpl(iCareApiImpl.getAPI());
        cityManager = new CityManagerImpl(iCareApiImpl.getAPI());
        districtManager = new DistrictManagerImpl(iCareApiImpl.getAPI());
        locationManager = new LocationManagerImpl(iCareApiImpl.getAPI());
        voucherManager = new VoucherManagerImpl(iCareApiImpl.getAPI());
        typeManager = new TypeManagerImpl(iCareApiImpl.getAPI());
        //Init data and the first string is hint
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
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
//        if (!hidden && isVisible())
//            aController.setRequestData(getActivity(), this, ModelURL.SELECT_COUNTRIES.getUrl(MainActivity.isUAT), "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.booking_next_button:
                break;
            case R.id.booking_startdate:
                bookingSelectPresenter.onStartDatePickerClick();
                break;
            case R.id.booking_expiredate:
                bookingSelectPresenter.onExpireDatePickerClick();
                break;
            default:
                break;
        }
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
//            if (status.has("Select_Countries")) {
//                //Receive response from Select_Countries.php
//                JSONArray countries = status.getJSONArray("Select_Countries");//Get the array of countries' JSONObject
//                populateData(countriesName, mapToCountryID, countries, "COUNTRY_ID", "COUNTRY");//Populate countriesName list if data exists
//                countrySp.setSelection(235);
//            }else if (status.has("Select_Cities")) {
//                //Receive response from Select_Cities.php
//                JSONArray cities = status.getJSONArray("Select_Cities");//Get the array of cities' JSONObject
//                populateData(citiesName, mapToCityID, cities, "CITY_ID", "CITY");//Populate citiesName list if data exists
//                citySp.setSelection(58);
//            }else if (status.has("Select_Districts")) {
//                //Receive response from Select_Districts.php
//                JSONArray districts = status.getJSONArray("Select_Districts");//Get the array of districts' JSONObject
//                populateData(districtsName, mapToDistrictID, districts, "DISTRICT_ID", "DISTRICT");//Populate districtsName list if data exists
//                districtSp.setSelection(8);
//            }else if (status.has("Select_Locations")) {
//                //Receive response from Select_Districts.php
//                JSONArray locations = status.getJSONArray("Select_Locations");//Get the array of districts' JSONObject
//                populateData(locationsName, mapToLocationID, locations, "LOCATION_ID", "ADDRESS");//Populate districtsName list if data exists
//                locationSp.setEnabled(true);//Set Spinner clickable to true
//            }else if (status.has("Select_Vouchers")){
//                //Receive response from Select_Vouchers.php
//                JSONArray vouchers = status.getJSONArray("Select_Vouchers");//Get the array of vouchers' JSONObject
//                populateData(vouchersName, mapToVoucherID, vouchers, "VOUCHER_ID", "VOUCHER", "PRICE");//Populate districtsName list if data exists
//                voucherSp.setEnabled(true);//Set Spinner clickable to true
//            }
//        }catch (JSONException je){
//            je.printStackTrace();
//        }
//    }

//    public void populateData(List<String> populateList, Map<String,String> mapToID, JSONArray data, String... params){
//        try {
//            for (int i = 0; i < data.length(); i++) {
//                JSONObject jOb = (JSONObject) data.get(i);
//                if (params.length == 3)
//                    populateList.add(jOb.getString(params[1]) + " - " + jOb.getString(params[2]) + " VND");
//                else
//                    populateList.add(jOb.getString(params[1]));
//                mapToID.put(jOb.getString(params[1]), jOb.getString(params[0]));
//            }
//        }catch (JSONException je){
//            je.printStackTrace();
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinner_countries:
                bookingSelectPresenter.getAllCitiesByCountry(countrySp.getSelectedItem().toString(), cityManager);
                bookingSelectPresenter.onCountrySelect(countrySp.getSelectedItem().toString());
                break;
            case R.id.spinner_cities:
                bookingSelectPresenter.getAllDistrictsByCity(citySp.getSelectedItem().toString(), districtManager);
                bookingSelectPresenter.onCitySelect(citySp.getSelectedItem().toString());
                break;
            case R.id.spinner_districts:
                bookingSelectPresenter.getAllLocationsByDistrict(districtSp.getSelectedItem().toString(), locationManager);
                bookingSelectPresenter.onDistrictSelect(districtSp.getSelectedItem().toString());
                break;
            case R.id.spinner_locations:
                bookingSelectPresenter.getAllVouchers(voucherManager);
                bookingSelectPresenter.onLocationSelect(locationSp.getSelectedItem().toString());
                break;
            case R.id.spinner_vouchers:
                bookingSelectPresenter.getAllTypes(typeManager);
                bookingSelectPresenter.onVoucherSelect(voucherSp.getSelectedItem().toString());
                break;
            case R.id.spinner_type:
                bookingSelectPresenter.onTypeSelect(typeSp.getSelectedItem().toString());
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void updateCountryList(List<String> list) {
        countriesName.clear();
        countriesName.add(getString(R.string.booking_country_hint));
        countriesName.addAll(list);
    }

    @Override
    public void updateCityList(List<String> list) {
        citiesName.clear();
        citiesName.add(getString(R.string.booking_city_hint));
        citiesName.addAll(list);
    }

    @Override
    public void updateDistrictList(List<String> list) {
        districtsName.clear();
        districtsName.add(getString(R.string.booking_district_hint));
        districtsName.addAll(list);
    }

    @Override
    public void updateLocationList(List<String> list) {
        locationsName.clear();
        locationsName.add(getString(R.string.booking_location_hint));
        locationsName.addAll(list);
    }

    @Override
    public void updateVoucherList(List<String> list) {
        vouchersName.clear();
        vouchersName.add(getString(R.string.booking_voucher_hint));
        vouchersName.addAll(list);
    }

    @Override
    public void updateTypeList(List<String> list) {
        typesName.clear();
        typesName.add(getString(R.string.booking_type_hint));
        typesName.addAll(list);
    }

    @Override
    public void updateStartDate(String startDate) {
        if (startDate == null)
            this.startDate.setText(getActivity().getString(R.string.booking_start_date));
        else
            this.startDate.setText(startDate);
    }

    @Override
    public void updateExpireDate(String expireDate) {
        if (expireDate == null)
            this.expireDate.setText(getActivity().getString(R.string.booking_expire_date));
        else
            this.expireDate.setText(expireDate);
    }

    @Override
    public void showStartDatePicker(Calendar calendar) {
        startDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        startDatePickerDialog.show();
    }

    @Override
    public void showExpireDatePicker(Calendar calendar) {
        expireDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        expireDatePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        calendar.set(year, month, day);
        if (startDatePickerDialog.hashCode() == datePicker.hashCode())
            bookingSelectPresenter.onStartDateSet(calendar);
        else
            bookingSelectPresenter.onExpireDateSet(calendar);
    }

    @Override
    public void onTypeChange() {
        mainActivityPresenter.emptyCart();
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
}
